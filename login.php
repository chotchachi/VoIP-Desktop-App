<?php
require_once('dbconn.php');
mysqli_set_charset($con, 'utf8');
$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $phone_number = $_POST['phone_number'];
    $password = $_POST['password'];
    $sql = "SELECT * FROM account WHERE phone_number = '$phone_number'";
    if (mysqli_num_rows(@mysqli_query($con, $sql)) > 0) {
        $sql2 = "SELECT * FROM account WHERE phone_number = '$phone_number' AND password='$password'";
        if (mysqli_num_rows(@mysqli_query($con, $sql2)) > 0) {
            $result = mysqli_query($con, $sql2);
            $row = mysqli_fetch_array($result);
            $phone = $row["phone_number"];
            $name = $row["name"];
            $id = $row["id"];

            $response["status"] = 2;
            $response["id"] = $id;
            $response["phone"] = $phone;
            $response["name"] = $name;
        } else {
            $response["status"] = 0;
        }
    } else {
        $response["status"] = 1;
    }

    echo json_encode($response);
}
