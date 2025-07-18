<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>


<head>
    <meta charset="UTF-8">
    <meta name="description" content="Job Recommendation">
    <meta name="author" content="Your Name">
    <title>Job Recommendation</title>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<header class="top-header">
    <nav class="top-nav">
        <a href="#">Home</a>
        <a href="#">Contact</a>
        <a href="#">About</a>
    </nav>
    <span id="welcome-msg"></span>
    <a id="logout-link" href="logout">Logout</a>
    <i id="avatar" class="avatar fa fa-user fa-2x"></i>
</header>

<div class="container">
    <header>
        <p>
        <span>Item</span>
        <br/>Recommendations
        </p>
    </header>

    <section class="main-section">
        <div id="login-form">
            <label for="username">Username:</label>
            <input id="username" name="username" type="text">
            <label for="password">Password:</label>
            <input id="password" name="password" type="password">
            <button id="register-form-btn">New User? Register</button>
            <button id="login-btn">Login</button>
            <p id="login-error"></p>
        </div>

        <div id="register-form">
            <label for="username">Username: *</label>
            <input id="register-username" name="username" type="text">
            <label for="password">Password: *</label>
            <input id="register-password" name="password" type="password">
            <label for="register-first-name">First Name: *</label>
            <input id="register-first-name" name="first-name" type="text">
            <label for="register-last-name">Last Name: *</label>
            <input id="register-last-name" name="last-name" type="text">
            <button id="login-form-btn">Back to Login</button>
            <button id="register-btn">Register</button>
            <p id="register-result"></p>
        </div>

        <aside id="item-nav">
            <div class="nav-icon">
                <i class="fa fa-sitemap fa-2x"></i>
            </div>
            <nav class="main-nav">
                <a href="#" id="nearby-btn" class="main-nav-btn active">
                    <i class="fa fa-map-marker"></i> Nearby
                </a>
                <a href="#" id="fav-btn" class="main-nav-btn">
                    <i class="fa fa-heart"></i> My Favorites
                </a>
                <a href="#" id="recommend-btn" class="main-nav-btn">
                    <i class="fa fa-thumbs-up"></i> Recommendation
                </a>
            </nav>
        </aside>

        <ul id="item-list"></ul>
    </section>
</div>
<footer>
    <p class="title">What We Do</p>
    <p>"Help you find the best place around."</p>
    <ul>
        <li>
            <p><i class="fa fa-map-o fa-2x"></i></p>
            <p>CA</p>
        </li>
        <li>
            <p><i class="fa fa-envelope-o fa-2x"></i></p>
            <p>huaqunce@gmail.com</p>
        </li>
        <li>
            <p><i class="fa fa-phone fa-2x"></i></p>
            <p>+1 800 123 456</p>
        </li>
    </ul>
</footer>

<script type="text/html" id="tpl">
    <li id="{{id}}" class="item">
        <img src="{{company_logo}}">
        <div>
            <a class="item-name" href="{{url}}" target="_blank">
                {{title}}
            </a>
            <p class="item-keyword">Keyword: {{keywords}}
            </p>
        </div>
        <p class="item-address">{{location}}</p>
        <p class="fav-link">
            <i class="{{favorite}}" data-item_id="{{id}}"></i>
        </p>
    </li>
</script>

<script src="https://rawgit.com/emn178/js-md5/master/build/md5.min.js"></script>

<script src="scripts/main.js"></script>


</body>
</html>