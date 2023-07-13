<?php  

 $con = mysqli_connect("localhost", "auddms", "wise0517", "auddms");
    mysqli_query($con,'SET NAMES utf8');

if (!$con)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

$query="select * from ECOFFE";

$result=mysqli_query($con,$query);

    $response = array();

    while($row=mysqli_fetch_array($result)){
        array_push($response, 
            array('userID'=>$row[0],
            'userPassword'=>$row[1],
            'Balance'=>$row[2],
	'Stamp'=>$row[3],
	'Coupon'=>$row[4]
        ));
    }

header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("webnautes"=>$response), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;




   
?>