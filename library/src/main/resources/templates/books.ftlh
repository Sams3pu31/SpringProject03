<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Books</title>
  <link rel="stylesheet" href="/books.css" type="text/css">
</head>
<body>
  <div class="container">
    <h2>BOOKS</h2>
    <button class="toggle-button" onclick="toggleDarkMode()">Toggle Dark Mode</button>
    <ul class="responsive-table">
      <li class="table-header">
        <div class="col col-1">ID</div>
        <div class="col col-2">NAME</div>
        <div class="col col-3">GENRE</div>
      </li>
      <#list books as book>
        <li class="table-row">
          <div class="col col-1">${book.id}</div>
          <div class="col col-2">${book.name}</div>
          <div class="col col-3">${book.genre}</div>
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
