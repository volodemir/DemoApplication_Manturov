package ru.manturov.terminal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manturov.dao.ReportModel;
import ru.manturov.entity.Account;
import ru.manturov.entity.Category;
import ru.manturov.entity.User;
import ru.manturov.exception.CustomException;
import ru.manturov.service.AccountService;
import ru.manturov.service.CategoryService;
import ru.manturov.service.RegService;
import ru.manturov.service.TransactionService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class Main {
    private final RegService regService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final TransactionService transactionService;
    private User user;

    void start() {
        System.out.println("Добро пожаловать!" + "\n"
                + "Авторизация в системе" + "\n"
                + "1. Авторизация" + "\n"
                + "2. Регистрация" + "\n");

        String userNumOperation = numOfOperation();

        distrOfAuthOperations(userNumOperation);
    }

    private void distrOfAuthOperations(String userNumOperation) throws NumberFormatException {
        int num = 0;
        try {
            num = Integer.parseInt(userNumOperation);
        } catch (NumberFormatException e) {
            System.out.println("Необходимо ввести одну цифру (1 или 2).");
        }
        if (num == AuthMenu.AUTHORIZATION.getNumOfOperation()) {
            auth();
        }
        if (num == AuthMenu.REGISTRATION.getNumOfOperation()) {
            registration();
        }
    }

    private void auth() {
        String email = request("Введите email: ");
        user = regService.getUserByEmail(email);
        if (user != null) {
            System.out.println("\n" + "Hello, " + user.getEmail() + "!");
            mainMenu();
        } else {
            System.out.println("Необходимо зарегистрироваться в системе.");
            start();
        }
    }

    private void registration() {
        String email = request("Введите email: ");
        String password = request("Введите password: ");
        user = regService.registration(email, password);
        if (user != null) {
            System.out.println("\n" + "Hello, " + user.getEmail() + "!");
            mainMenu();
        } else {
            System.out.println("Регистрация не удалась.");
        }
    }

    private void mainMenu() {
        System.out.println("\n" + "Доступные операции:");
        System.out.println("1. Создать счёт");
        System.out.println("2. Удалить счёт");
        System.out.println("3. Вывести все счета");
        System.out.println("4. Создать тип транзакции");
        System.out.println("5. Вывести отчеты по доходам/расходам на счетах за промежуток времени");
        System.out.println("6. Добавить транзакцию");
        String userNumOperation = numOfOperation();

        distrOfOperations(userNumOperation);
    }

    private void distrOfOperations(String userNumOperation) {
        int num = 0;
        try {
            num = Integer.parseInt(userNumOperation);
        } catch (NumberFormatException e) {
            System.out.println("Необходимо ввести одну цифру (1 или 2).");
        }
        if (num == MainMenu.CREATE_ACCOUNT.getNumOfOperation()) {
            createAccount();
        }
        if (num == MainMenu.DELETE_ACCOUNT.getNumOfOperation()) {
            deleteAccount();
        }
        if (num == MainMenu.SHOW_ACCOUNTS.getNumOfOperation()) {
            showAccounts();
            mainMenu();
        }
        if (num == MainMenu.CREATE_CATEGORY.getNumOfOperation()) {
            createTransactionCategory();
        }
        if (num == MainMenu.SHOW_REPORT.getNumOfOperation()) {
            showReportByIncomeExpenseByTime();
        }
        if (num == MainMenu.ADD_TRANSATION.getNumOfOperation()) {
            addTransaction();
        }
    }

    private void createAccount() {
        System.out.println("Создание нового счёта.");
        BigDecimal sumOnAccount = null;
        try {
            sumOnAccount = BigDecimal.valueOf(Long.parseLong(request("Введите сумму денежных средств на счёте (цифрами).")));
        } catch (InputMismatchException e) {
            System.out.println("Необходимо ввести числовое значение.");
        }
        String nameOfAcc = request("Введите название счёта");

        accountService.insert(sumOnAccount, nameOfAcc, user.getId());
        System.out.println("Счёт " + sumOnAccount + " " + nameOfAcc + " успешно создан.");
        mainMenu();
    }

    private void deleteAccount() {
        System.out.println("\nУдаление счёта.");
        showAccounts();
        List<Account> accountList = accountService.getAllByUserId(user.getId());
        if (accountList.isEmpty()) {
            System.out.println("Нет созданных счетов.");
        } else {
            byte numOfAcc = 0;
            try {
                numOfAcc = Byte.parseByte(request("Введите номер счёта, который следует удалить: "));
            } catch (InputMismatchException e) {
                System.out.println("Необходимо ввести числовое значение.");
            }

            if (numOfAcc < 1 || numOfAcc > accountList.size()) {
                System.out.println("Введено некорректное значение/номера счёта не существует.");
            }
            for (int i = 0; i < accountList.size(); i++) {
                if (i + 1 == numOfAcc) {
                    String nameOfAcc = accountList.get(i).getName();
                    BigDecimal balance = accountList.get(i).getBalance();
                    if (!accountService.delete(accountList.get(i).getId(), user.getId())) {
                        System.out.println("Удаление счёта не произошло.");
                    } else {
                        System.out.println("Счёт под номером " + numOfAcc + " (" +
                                balance + " " + nameOfAcc + ") успешно удалён.");
                    }
                }
            }
        }
        mainMenu();
    }

    private void showAccounts() {
        System.out.println("Ваши счета");
        List<Account> accountList = accountService.getAllByUserId(user.getId());
        if (accountList.isEmpty()) {
            System.out.println("В системе нет созданных счетов");
        } else {
            for (int i = 0; i < accountList.size(); i++) {
                System.out.println((i + 1) + ". " + accountList.get(i).getName() + " " + accountList.get(i).getBalance());
            }
        }
    }

    private void createTransactionCategory() {
        System.out.println("Создание типа транзакции.");
        String transactionCategory = request("Введите название типа транзакции:");
        categoryService.insert(transactionCategory, user.getId());
        System.out.println("Тип транзакции " + transactionCategory + " успешно создан.");
        mainMenu();
    }

    private void showReportByIncomeExpenseByTime() {
        System.out.println("Отчеты по доходам/расходам");

        Date dateBegin = parseStrToDate(request("Введите дату начала периода в формате дд.мм.гггг (например, 03.09.2022)"));
        Date dateEnd = parseStrToDate(request("Введите дату конца периода в формате дд.мм.гггг (например, 07.09.2022)"));

        List<ReportModel> valueIncomeByCategoryList = categoryService.getIncomeByPeriod(user.getId(), dateBegin, dateEnd);
        List<ReportModel> valueExpenseByCategoryList = categoryService.getExpenseByPeriod(user.getId(), dateBegin, dateEnd);

        System.out.println("Доход по категориям");
        for (ReportModel rm : valueIncomeByCategoryList) {
            System.out.println(rm);
        }
        System.out.println("Расход по категориям");
        for (ReportModel rm : valueExpenseByCategoryList) {
            System.out.println(rm);
        }
        mainMenu();
    }

    private void addTransaction() {
        System.out.println("Добавление транзакции");
        showAccounts();
        List<Account> accountList = accountService.getAllByUserId(user.getId());
        List<Category> transactionCategoryList = showCategories(user.getId());
        List<Long> categories = new ArrayList<>();
        BigDecimal value = null;
        long fromAccount;
        long toAccount;
        long fromAccountId = 0;
        long toAccountId = 0;
        try {
            value = BigDecimal.valueOf(Long.parseLong(request("Введите сумму транзакции:")));
            fromAccount = Integer.parseInt(request("Введите номер счёта, с которого выполнить транзакию:"));
            toAccount = Integer.parseInt(request("Введите номер счёта, на который выполнить транзакию:"));
            for (int i = 0; i < accountList.size(); i++) {
                if (fromAccount == i + 1) {
                    fromAccountId = accountList.get(i).getId();
                }
                if (toAccount == i + 1) {
                    toAccountId = accountList.get(i).getId();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Необходимо ввести числовое значение.");
        }

        String[] categoryNumbersMassString = null;
        try {
            categoryNumbersMassString = request("Введите номера категорий через пробел:").split(" ");
        } catch (InputMismatchException e) {
            System.out.println("Необходимо ввести числовые значения.");
        }
        try {
            for (int i = 0; i < transactionCategoryList.size(); i++) {
                if (categoryNumbersMassString != null) {
                    for (String s : categoryNumbersMassString) {
                        if (Long.parseLong(s) == i + 1) {
                            System.out.println("s " + s);
                            categories.add(transactionCategoryList.get(i).getId());
                        }
                    }
                }
            }
            transactionService.insert(value, fromAccountId, toAccountId, categories);
            System.out.println("Транзакция добавлена успешно");
        } catch (NullPointerException e) {
            System.out.println("Номера категорий не указаны.");
        }
        mainMenu();
    }

    private static String request(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        return scanner.nextLine();
    }

    private static String numOfOperation() {
        return request("Введите номер желаемой операции");
    }

    private static Date parseStrToDate(String dateStr) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new CustomException("Необходимо ввести дату в формате дд.ММ.гггг!", e);
        }
    }

    private List<Category> showCategories(Long userId) {
        System.out.println("Ваши категории:");
        List<Category> categoryList = categoryService.getAllByUserId(userId);
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println((i + 1) + ". " + categoryList.get(i).getCategory());
        }
        return categoryList;
    }
}



