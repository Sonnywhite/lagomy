<?php

// get all products
function getAllProducts() {
	
	// TODO: connect to microservice
	// TODO: prepare response	
	
	
	$url2 = "http://localhost:9000/api/hello/World";
	$url = "http://localhost:9000/api/product/get/all";
	$curl = curl_init();
	
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($curl, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
	
	$result = curl_exec($curl);
	
	curl_close($curl);
	
	$test_product1 = array("name" => "Bed","description" => $result,"price" => "2000kr","seller" => "linne123","rating" => "4.5");
	$test_product2 = array("name" => "Bed2","description" => "Just another bed","price" => "2040kr","seller" => "linne123","rating" => "4.5");
	$test_product3 = array("name" => "desk chair","description" => "Hello World!","price" => "300kr","seller" => "anon","rating" => "3.5");
	$test_product4 = array("name" => "big wardrobe","description" => "Hello World!","price" => "300kr","seller" => "resu","rating" => "3.0");
	$test_product5 = array("name" => "Table","description" => "Very huge table to store a lot of things on it","price" => "100kr","seller" => "linne123","rating" => "4.5");
	
	$products_arr = array($test_product1,$test_product2,$test_product3,$test_product4,$test_product5);
	
	return $products_arr;
}

// get my products
function getMyProducts() {
	
}

// get my messages
function getMyMessages($username) {
	
	$test_message1 = array("from" => "resu", "content" => "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.", "date" => "12.10.2016 20:16");
	$test_message2 = array("from" => "anoN", "content" => "Hello this is only a small text", "date" => "2.10.2016 20:16");
	$test_message3 = array("from" => "anoN", "content" => "Hello this is only a small text", "date" => "3.9.2016 15:43");
	$test_message4 = array("from" => "anoN", "content" => "Hello this is only a small text", "date" => "2.9.2016 10:49");
	$test_message5 = array("from" => "anoN", "content" => "Hello this is only a small text", "date" => "2.9.2016 10:12");
	$test_message6 = array("from" => "anoN", "content" => "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At", "date" => "2.9.2016 09:45");
	$test_message7 = array("from" => "anoN", "content" => "There is a string function (strtok) which can be used to split a string into smaller strings (tokens) based on some separator(s). For the purposes of this thread, the first word (defined as anything before the first space character) of Test me more can be obtained by tokenizing the string on the space character.", "date" => "2.9.2016 09:16");

	$messages_arr = array($test_message1,$test_message2,$test_message3,$test_message4,$test_message5,$test_message6,$test_message7);
	
	return $messages_arr;
}

// login
function checkLogin($username,$password) {
	
	$data_array = array(
		"username" => $username,
		"password" => $password
	);
	$data_string = json_encode($data_array);
	
	$url = "http://localhost:9000/api/users/check";
	$curl = curl_init();
	
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_POST, 1);
	curl_setopt($curl, CURLOPT_POSTFIELDS, $data_string);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($curl, CURLOPT_HTTPHEADER, array(                                                                          
		'Content-Type: application/json',                                                                                
		'Content-Length: ' . strlen($data_string))                                                                       
	); 
	
	$result = curl_exec($curl);
	
	curl_close($curl);
	
	return $result;
}

// create user

// create new product
function addProduct($productName, $sellerName, $description, $photoPath, $price) {
	
	$data_array = array(
		"productId" => "test",
		"productName" => $productName,
		"sellerName" => $sellerName,
		"description" => $description,
		"photoPath" => $photoPath,
		"price" => 12,
		"sold" => false
	);
	$data_string = json_encode($data_array);
	
	$url = "http://localhost:9000/api/product/add";
	$curl = curl_init();
	
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_POST, 1);
	curl_setopt($curl, CURLOPT_POSTFIELDS, $data_string);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($curl, CURLOPT_HTTPHEADER, array(                                                                          
		'Content-Type: application/json',                                                                                
		'Content-Length: ' . strlen($data_string))                                                                       
	);
	
	$result = curl_exec($curl);
	
	curl_close($curl);
	
	return $result;
	
}

// mark as sold | choose buyer

// rate seller

?>