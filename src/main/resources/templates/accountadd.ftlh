<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Создание счёта</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <form action="/web/accountadd" method="post">
                <div class="mb-3">
                    <label for="exampleInputAccountName" class="form-label">Account name</label>
                    <@spring.formInput "form.name" "class=\"form-control\" id=\"accountName\" placeholder=\"Enter account name\""/>
                    <@spring.showErrors "br"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputBalance" class="form-label">Balance</label>
                    <@spring.formInput "form.balance" "class=\"form-control\" id=\"balance\" placeholder=\"Enter balance\""/>
                    <@spring.showErrors "br"/>
                </div>
                <button type="submit" class="btn btn-primary">Создать</button>
            </form>
            <br>
            <a href="personal-area">Вернуться в основное меню</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>