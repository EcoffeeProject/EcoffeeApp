<?php

	$con = mysqli_connect("localhost", "auddms", "wise0517", "auddms");
	mysqli_query($con,'SET NAMES utf8');


   	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
   	$Balance = $_POST["Balance"];
	$Stamp = $_POST["Stamp"];
	$Coupon = $_POST["Coupon"];
    
 	


	$query = "UPDATE `ECOFFE` SET `userID` = '$userID',`userPassword` = '$userPassword',`Balance` = '$Balance',`Stamp` = '$Stamp',`Coupon` = '$Coupon' WHERE `ECOFFE`.`userID`='$userID'"; 
  	$result = mysqli_query($con,$query);  
 
  		if ($result) {
    			echo ' success ';
  		} 
		else { 
   	 		echo ' fail ';
 	 	}

   	$response = array();
   	$response["success"] = true;
 
   	 echo json_encode($response);


?>