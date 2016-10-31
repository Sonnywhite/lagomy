<?php

// get all products
function getAllProducts() {
	
	// TODO: connect to microservice
	// TODO: prepare response
	
	/*
	$url = "http://localhost:9000/api/hello/World";
	$curl = curl_init();
	
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	
	$result = curl_exec($curl);
	
	curl_close($curl);*/
	
	$product1 = array ("name" => "Bed","description" => "This is a very long description","price" => "2000kr","seller" => "linne123","rating" => "4.5");
	$product2 = array ("name" => "Bed2","description" => "Just another bed","price" => "2040kr","seller" => "linne123","rating" => "4.5");
	$product3 = array ("name" => "desk chair","description" => "Hello World!","price" => "300kr","seller" => "anon","rating" => "3.5");
	$product4 = array ("name" => "big wardrobe","description" => "Hello World!","price" => "300kr","seller" => "resu","rating" => "3.0");
	$product5 = array ("name" => "Table","description" => "Very huge table to store a lot of things on it","price" => "100kr","seller" => "linne123","rating" => "4.5");
	
	$products_arr = array($product1,$product2,$product3,$product4,$product5);
	
	return $products_arr;
}

// get my products
function getMyProducts($username,$token) {
	
}

// get my messages
function getMyMessages($username,$token) {
	
}

// login

// create user

// create new product

// mark as sold | choose buyer

// rate seller

?>