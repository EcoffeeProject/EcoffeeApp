<?php 
    $con = mysqli_connect("localhost", "auddms", "wise0517", "auddms");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $Balance = 0;
    $Coupon = 0;
    $Stamp =0;

    $statement = mysqli_prepare($con, "INSERT INTO ECOFFE VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssiii", $userID, $userPassword,$Balance,$Coupon,$Stamp);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>