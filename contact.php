<?php
require_once('dbconn.php');
mysqli_set_charset($con, 'utf8');
$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $phone_number = $_POST['phone_number'];
    //$phone_number = "0355152525";
    $sql = "SELECT account.id, account.phone_number, account.name FROM account LEFT JOIN danhba ON account.phone_number = danhba.contact WHERE danhba.account_phone = '$phone_number'";
    $result = mysqli_query($con, $sql);
    if (mysqli_num_rows($result) > 0) {
        while($row = mysqli_fetch_array($result)) {
            $phone = $row["phone_number"];
            $name = $row["name"];
            $id = $row["id"];

            $response[] = new Contact($id, $phone, $name);
        }            
    }

    echo json_encode($response);
}

class Contact {
    public function __construct($id, $phone_number, $name) {
        $this->id = $id;
        $this->phone_number = $phone_number;
        $this->name = $name;
    }
}

// class Result
// {
//     protected $status;
//     protected $contacts;

//     public function __construct($status, $data) 
//     {
//         $this->status = $status;
//         $this->contacts = $data;
//     }
// }
