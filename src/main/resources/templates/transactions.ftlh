<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Транзакции</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <h1>Ваши транзакции</h1>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Value</th>
                    <th scope="col">Date of creation</th>
                    <th scope="col">From Account Id</th>
                    <th scope="col">To Account Id</th>
                </tr>
                </thead>
                <tbody>
                <#list transactions as t>
                    <tr>
                        <th scope="row">${t.getId()}</th>
                        <th>${t.getValue()}</th>
                        <th>${t.getCreatedDate()?date}</th>
                        <th>${(t.getFromAccount().getId())!"-"}</th>
                        <th>${(t.getToAccount().getId())!"-"}</th>
                    </tr>
                </#list>
                </tbody>
            </table>
            <br>
            <h2>Доступные операции c транзакциями</h2>
            <br>
            <a href="transactionadd">Создать транзакцию</a><br>
            <br>
            <a href="personal-area">Вернуться в основное меню</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>