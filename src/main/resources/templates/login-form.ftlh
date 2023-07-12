<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Авторизация</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <h1>Авторизация</h1>
            <form action="/login" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Email address</label>
                    <input name="email" class="form-control" id="exampleInputEmail" placeholder="Enter email" type="text">
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label">Password</label>
                    <input name="password" class="form-control" id="exampleInputPassword" placeholder="Password" type="password">
                </div>
                <button type="submit" class="btn btn-primary">Войти</button>
            </form>
            <br>
            <a href="/registration">Регистрация</a>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>