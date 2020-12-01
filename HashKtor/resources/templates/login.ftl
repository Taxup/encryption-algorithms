<html lang="">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: black;
        }

        * {
            box-sizing: border-box;
        }

        /* Add padding to containers */
        .container {
            padding: 16px;
            background-color: white;
        }

        /* Full-width input fields */
        input[type=email], input[type=password] {
            width: 100%;
            padding: 15px;
            margin: 5px 0 22px 0;
            display: inline-block;
            border: none;
            background: #f1f1f1;
        }

        input[type=email]:focus, input[type=password]:focus {
            background-color: #ddd;
            outline: none;
        }

        /* Overwrite default styles of hr */
        hr {
            border: 1px solid #f1f1f1;
            margin-bottom: 25px;
        }

        /* Set a style for the submit button */
        .btn {
            background-color: black;
            color: white;
            padding: 16px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 33.06%;
            opacity: 0.9;
        }

        .btn:hover {
            opacity: 1;
        }

        /* Add a blue text color to links */
        a {
            color: dodgerblue;
        }

        #ch{
            border: none;
            width: 0;
            background: none;
        }
    </style>
    <title></title>
</head>
<body>

<form action="/login" method="post">
    <div class="container">

        <label for="email"><b>Email</b></label>
        <input type="email" placeholder="Enter Email" name="email">

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password">

        <input type="submit" onclick="return check(this)" class="btn" name="register" value="Register">
        <input type="submit" onclick="return check(this)" class="btn" name="login" value="Login">
        <input type="submit" onclick="return check(this)" class="btn" name="list" value="See list of Users">

        <input id="ch" name="btn" value="">

    </div>
</form>

<script>
    function check(Element) {
        var ch = document.getElementById("ch");
        ch.value = Element.name;
        // alert(ch.value);
        location.reload();
    }
</script>
</body>
</html>