<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Authors</title>
    <link rel="stylesheet" href="/authors.css">
</head>
<body>
    <div class="container">
        <h2>AUTHORS</h2>
        <button class="toggle-button" onclick="toggleDarkMode()">Toggle Dark Mode</button>
        <ul class="responsive-table">
            <li class="table-header">
                <div class="col col-1">ID</div>
                <div class="col col-2">NAME</div>
                <div class="col col-3">SURNAME</div>
            </li>
            <#list authors as author>
                <li class="table-row">
                    <div class="col col-1">${author.id}</div>
                    <div class="col col-2">${author.name}</div>
                    <div class="col col-3">${author.surname}</div>
                </li>
            </#list>
        </ul>
    </div>

    <script>
        function toggleDarkMode() {
            document.body.classList.toggle('dark-mode');
        }
    </script>
</body>
</html>
