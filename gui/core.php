<?php

// get all products
function getAllProducts() {	
	
	$url = "http://localhost:9000/api/product/get/all";
	$curl = curl_init();
	
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($curl, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
	
	$result = curl_exec($curl);
	
	curl_close($curl);
	
	return json_decode($result, true);
}

// get my messages
function getMyMessages($username) {
	
	$url = "http://localhost:9000/messages/".$username;
	$curl = curl_init();
	
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($curl, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
	
	$result = curl_exec($curl);
	
	curl_close($curl);
	
	return json_decode($result, true);
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
function register($username, $password) {
	
	$data_array = array(
		"username" => $username,
		"password" => $password
	);
	$data_string = json_encode($data_array);
	
	$url = "http://localhost:9000/api/users/create";
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

// create new product
function addProduct($productName, $sellerName, $description, $photoPath, $price) {
	
	$data_array = array(
		"productId" => "test",
		"productName" => $productName,
		"sellerName" => $sellerName,
		"description" => $description,
		"photoPath" => $photoPath,
		"price" => $price,
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

// send a messages
function sendMessage($messageId, $sender, $message, $receiver) {
	
	$data_array = array(
		"messageId" => $messageId,
		"sender" => $sender,
		"message" => $message,
		"receiver" => $receiver
	);
	$data_string = json_encode($data_array);
	
	$url = "http://localhost:9000/messages";
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

function getRating($username) {
	
	$url = "http://localhost:9000/api/rating/get/".$username;
	$curl = curl_init();
	
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($curl, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
	
	$result = curl_exec($curl);
	
	curl_close($curl);
	
	$splitted_result = explode(": ",$result);
	
	return $splitted_result[1]<0?"-":$splitted_result[1];
	
}

function getRatingOrders($username) {
	
	$url = "http://localhost:9000/api/rating/ask/order/".$username;
	$curl = curl_init();
	
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($curl, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
	
	$result = curl_exec($curl);
	
	curl_close($curl);
	
	$splitted_result = explode(": ",$result);
	
	return json_decode($result,true);
	
}

function rate($sellerName, $rating) {
	
	$data_array = array(
		"newRating" => $rating
	);
	$data_string = json_encode($data_array);
	
	$url = "http://localhost:9000/api/rating/rate/".$sellerName;
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

?>