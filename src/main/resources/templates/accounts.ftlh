<#import "/spring.ftl" as spring>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Счета пользователя</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <h1>Ваши счета</h1>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Name</th>
                    <th scope="col">Balance</th>
                </tr>
                </thead>
                <tbody>
                <#list accounts as a>
                    <tr>
                        <th scope="row">${a.getId()}</th>
                        <th>${a.getName()}</th>
                        <th>${a.getBalance()}</th>
                    </tr>
                </#list>
                </tbody>
            </table>
            <br>
            <h2>Доступные операции cо счетами</h2>
            <br>
            <a href="accountadd">Создать счёт</a><br>
            <a href="accountdelete">Удалить счёт</a><br><br>
            <a href="personal-area">Вернуться в основное меню</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>