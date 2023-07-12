<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Отчёты по доходам/расходам</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <form action="/web/reportform" method="post">
                <div class="mb-3">
                    <label for="exampleInputBeginDate" class="form-label">Account name</label>
                    <@spring.formInput "form.beginDate" "class=\"form-control\" id=\"beginDate\" placeholder=\"Enter begin date\""/>
                    <@spring.showErrors "br"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputEndDate" class="form-label">Balance</label>
                    <@spring.formInput "form.endDate" "class=\"form-control\" id=\"endDate\" placeholder=\"Enter end date\""/>
                    <@spring.showErrors "br"/>
                </div>
                <button type="submit" class="btn btn-primary">Вывести отчёт</button>
            </form>
            <a href="personal-area">Вернуться в основное меню</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>