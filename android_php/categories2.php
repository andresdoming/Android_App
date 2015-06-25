<?php

include_once './data2.php';
$cats = array();

foreach ($categories as $cat) {
    $tmp = array();
    $tmp["id"] = $cat["id"];
    $tmp["name"] = $cat["catname"];
    $tmp["subcategories_count"] = count($cat["subcategories"]);

    array_push($cats, $tmp);
}

// printing json
echo json_encode($cats);
?>
