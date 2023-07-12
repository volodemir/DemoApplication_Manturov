<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Регистация</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <h1>Регистрация</h1>
            <form action="/web/registration" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Email address</label>
                    <@spring.formInput "form.email" "class=\"form-control\" id=\"exampleInputEmail\" placeholder=\"Enter email\"" "email"/>
                    <@spring.showErrors "br"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label">Password</label>
                    <@spring.formInput "form.password" "class=\"form-control\" id=\"exampleInputPassword\" placeholder=\"Password\"" "password"/>
                    <@spring.showErrors "br"/>
                </div>
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Check me out</label>
                </div>
                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </form>
            <br>
            <a href="/login-form">Авторизация</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>