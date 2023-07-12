<#import "/spring.ftl" as spring>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Создание транзакции</title>
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
            <h1>Ваши типы транзакций</h1>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Name</th>
                </tr>
                </thead>
                <tbody>
                <#list categories as c>
                    <tr>
                        <th scope="row">${c.getId()}</th>
                        <th>${c.getCategory()}</th>
                    </tr>
                </#list>
                </tbody>
            </table>
            <br>
            <form action="/web/transactionadd" method="post">
                <div class="mb-3">
                    <label for="exampleInputValue" class="form-label">Value</label>
                    <@spring.formInput "transactionForm.value" "class=\"form-control\" id=\"valuemk\" placeholder=\"Enter value to transaction\""/>
                    <@spring.showErrors "br"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputFromAccId" class="form-label">From</label>
                    <@spring.formInput "transactionForm.fromAccountId" "class=\"form-control\" id=\"fromAccountId\" placeholder=\"Enter fromAccountId\""/>
                    <@spring.showErrors "br"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputToAccId" class="form-label">To</label>
                    <@spring.formInput "transactionForm.toAccountId" "class=\"form-control\" id=\"toAccountId\" placeholder=\"Enter toAccountId\""/>
                    <@spring.showErrors "br"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputCategories" class="form-label">Categories</label>
                    <@spring.formInput "transactionForm.categories" "class=\"form-control\" id=\"categories\" placeholder=\"Enter categories\""/>
                    <@spring.showErrors "br"/>
                </div>
                <button type="submit" class="btn btn-primary">Создать</button>
            </form>
            <br>
            <br>
            <a href="personal-area">Вернуться в основное меню</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>