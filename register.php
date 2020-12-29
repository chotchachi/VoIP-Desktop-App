<?php
require_once('dbconn.php');
mysqli_set_charset($con, 'utf8');
$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $phone_number = $_POST['phone_number'];
    $password = $_POST['password'];
    $name =  $_POST['name'];
    $sqlInsert = "INSERT INTO account (phone_number,password,name) VALUES ('$phone_number','$password','$name')";
    $resultInsert = @mysqli_query($con, $sqlInsert);
    if ($resultInsert) {
        $sqlGetInfo = "SELECT * FROM account WHERE phone_number = '$phone_number' AND password = '$password'";
        $result = mysqli_query($con, $sqlGetInfo);
        $row = mysqli_fetch_array($result);

        $phone = $row["phone_number"];
        $name = $row["name"];
        $id = $row["id"];

        $response["status"] = 1;
        $response["id"] = $id;
        $response["phone"] = $phone;
        $response["name"] = $name;
    } else {
        $response["status"] = 0;
    }

    echo json_encode($response);
}
