<?php

include_once './data.php';

if (isset($_GET["id"]) && $_GET["id"] != "") {

    if (array_key_exists($_GET["id"], $categories)) {

        echo json_encode($categories[$_GET["id"]]);
    } else {

        echo "{}";
    }
}
?>
