<#import "/spring.ftl" as spring>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <h1>Добро пожаловать, ${name}!</h1>
    </div>
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <h2>Доступные операции</h2>
            <br>
            <div class="d-grid gap-2 col-6 mx-auto">
                <button class="btn btn-primary" type="button" onclick='location.href="/web/accountadd"'>Создать счёт
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/accountdelete"'>Удалить счёт
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/accounts"'>Вывести все счета
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/categoryadd"'>Создать тип
                    транзакции
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/categorydel"'>Удалить тип
                    транзакции
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/categoryupd"'>Переименовать тип
                    транзакции
                </button>
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/categories"'>Вывести все типы
                    транзакции
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/reportform"'>Вывести отчеты
                    по
                    доходам/расходам на счетах за промежуток времени
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/transactions"'>Вывести все
                    транзакции
                </button>
                <button class="btn btn-primary" type="button" onclick='location.href="/web/transactionadd"'>Добавить
                    транзакцию
                </button>
            </div>
            <a href="/logout">Выйти</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>