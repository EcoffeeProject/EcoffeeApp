<?php
    $con = mysqli_connect("localhost", "auddms", "wise0517", "auddms");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $Balance ;
    $Coupon ;
    $Stamp ;


    $statement = mysqli_prepare($con, "SELECT * FROM ECOFFE WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword,$Balance,$Stamp,$Coupon);

    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;
  
     
    }

    echo json_encode($response);



?>