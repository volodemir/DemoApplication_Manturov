<#import "/spring.ftl" as spring>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Отчёты по доходам/расходам</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <h2>Отчёт по доходам</h2>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Balance</th>
                </tr>
                </thead>

                <tbody>
                <#list reportIncome as i>
                    <tr>
                        <th>${i.getCategoryName()}</th>
                        <th>${i.getValue()}</th>
                    </tr>
                </#list>
                </tbody>
            </table>
            <br>
            <h2>Отчёт по расходам</h2>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Balance</th>
                </tr>
                </thead>

                <tbody>
                <#list reportExpense as e>
                    <tr>
                        <th>${e.getCategoryName()}</th>
                        <th>${e.getValue()}</th>
                    </tr>
                </#list>
                </tbody>
            </table>
            <br><br>
            <a href="personal-area">Вернуться в основное меню</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>