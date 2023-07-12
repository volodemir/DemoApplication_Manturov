<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Переименование типа транзакции</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
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
            <form action="/web/categoryupd" method="post">
                <div class="mb-3">
                    <label for="exampleInputName" class="form-label">Category id</label>
                    <@spring.formInput "form.id" "class=\"form-control\" id=\"categoryId\" placeholder=\"Enter transaction id\""/>
                    <@spring.showErrors "br"/>
                    <label for="exampleInputName" class="form-label">Category name</label>
                    <@spring.formInput "form.name" "class=\"form-control\" id=\"categoryName\" placeholder=\"Enter new name of transaction type\""/>
                    <@spring.showErrors "br"/>
                </div>
                <button type="submit" class="btn btn-primary">Создать</button>
            </form>
            <h2>Доступные операции c типами транзакций</h2>
            <br>
            <a href="categoryadd">Создать тип транзакции</a><br>
            <br>
            <br>
            <a href="categorydel">Удалить тип транзакции</a><br>
            <br>
            <br>
            <a href="categoryupd">Переименовать тип транзакции</a><br>
            <br>
            <a href="personal-area">Вернуться в основное меню</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>