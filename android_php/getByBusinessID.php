<?php
    $objConnect = mysql_connect("mysql.eecs.oregonstate.edu","cs419-g14","fhN8WsZMqhcSSrLB");
	$objDB = mysql_select_db("cs419-g14");

	$strbiz_id = $_POST["sbiz_id"];
	$strSQL = "SELECT * FROM businesses WHERE 1 AND biz_id = '".$strbiz_id."'  ";

	$objQuery = mysql_query($strSQL);
	$obResult = mysql_fetch_array($objQuery);
	if($obResult)
	{
		$arr["biz_id"] = $obResult["biz_id"];
		$arr["biz_name"] = $obResult["biz_name"];
		$arr["address"] = $obResult["address"];
		$arr["address2"] = $obResult["address2"];
		$arr["city"] = $obResult["city"];
		$arr["zipcode"] = $obResult["zipcode"];
		$arr["phone"] = $obResult["phone"];
		$arr["website"] = $obResult["website"];
		$arr["hours"] = $obResult["hours"];
		$arr["category_id"] = $obResult["category_id"];
	}

	
	mysql_close($objConnect);
	
	echo json_encode($arr);
?>
