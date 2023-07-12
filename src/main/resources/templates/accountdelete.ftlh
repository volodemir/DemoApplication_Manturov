<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Удаление счёта</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container text-center">
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
            <div class="col"></div>
            <div class="col">
                <form action="/web/accountdelete" method="post">
                    <div class="mb-3">
                        <label for="exampleInputId" class="form-label">Account name</label>
                        <@spring.formInput "form.id" "class=\"form-control\" id=\"accountId\" placeholder=\"Enter accountId\""/>
                        <@spring.showErrors "br"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Удалить</button>
                </form>
            </div>
            <div class="col"></div>
            <br>
            <h2>Доступные операции cо счетами</h2>
            <br>
            <a href="accountadd">Создать счёт</a><br><br>
            <a href="personal-area">Вернуться в основное меню</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>