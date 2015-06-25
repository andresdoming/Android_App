<?php
    $objConnect = mysql_connect("mysql.eecs.oregonstate.edu","cs419-g14","fhN8WsZMqhcSSrLB");
	$objDB = mysql_select_db("cs419-g14");

	$strKeyword = $_POST["txtKeyword"];
	$strSQL = "SELECT * FROM businesses WHERE 1 AND biz_name LIKE '%".$strKeyword."%'  ORDER BY biz_id ASC  ";

	$objQuery = mysql_query($strSQL);
	$intNumField = mysql_num_fields($objQuery);
	$resultArray = array();
	while($obResult = mysql_fetch_array($objQuery))
	{
		$arrCol = array();
		for($i=0;$i<$intNumField;$i++)
		{
			$arrCol[mysql_field_name($objQuery,$i)] = $obResult[$i];
		}
		array_push($resultArray,$arrCol);
	}
	
	mysql_close($objConnect);
	
	echo json_encode($resultArray);
?>