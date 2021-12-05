<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="adminstyles.css">
  <title>Users</title>
</head>
<body>
<div class="logo">
        <img src="logo.png" alt="logo" width="60px">
    </div>
    <header>
        <h1>User Info</h1>
    </header>

    <table>
    <tr>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Password</th>
      <th>Sign-up Date</th>
    </tr>

    <?php
    //         where it's running | SQLusername | SQLpassword | nameofDB   
    $conn = mysqli_connect("localhost", "root", "password", "users");
    $sql = "SELECT * FROM users";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
      while ($row = $result-> fetch_assoc()) {
        echo "<tr><td>" . $row["user_Fname"] . "</td><td>" . $row["user_Lname"] . "</td><td>" . $row["email"] . "</td><td>" . $row["password"] . "</td><td>" . $row["sign_up_date"] . "</td></tr>";
      }
    } else {
      echo "No Users";
    }
    $conn->close();
    ?>
  </table>

<a href="https://www.youtube.com/watch?v=TLqZZjwhHlM">MySQL connection Tutorial video (9:01)</a>

</body>
</html>
