<?php
	require_once './core.php';
	require_once './auth.php'; 
?>
<?php if(!$LOGGED_IN) exit(); 

if(isset($_POST["action"]) && $_POST["action"] == "addProduct") {
	$gotAllParams = isset($_POST["productName"])?$_POST["productName"]!="":false;
	if($gotAllParams) $gotAllParams = isset($_POST["description"])?$_POST["description"]!="":false;
	if($gotAllParams) $gotAllParams = isset($_POST["photoPath"])?$_POST["photoPath"]!="":false;
	if($gotAllParams) $gotAllParams = isset($_POST["price"])?$_POST["price"]!="":false;
	if($gotAllParams) $gotAllParams = isset($_SESSION["username"])?$_SESSION["username"]!="":false;
	
	if(!$gotAllParams) {
		$result = "Not every parameter was set!<br>"
			.'productName = '.$_POST["productName"].'<br>'
			.'description = '.$_POST["description"].'<br>'
			.'photoPath = '.$_POST["photoPath"].'<br>'
			.'price = '.$_POST["price"].'<br>'
			.'username = '.$_SESSION["username"].'<br>';
	}
	else {	
	
		$result = addProduct(
			$_POST["productName"],
			$_SESSION["username"],
			$_POST["description"],
			$_POST["photoPath"],
			$_POST["price"]
		);
	}
}

?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_box">
			<?php include "./navigation.php"; ?>
			<form class="new_product_form" action="./new-product.php" method="post">
				Product Name: <br/>
				<input name="productName" type="text" /><br/>
				Description: <br/>
				<textarea name="description" rows="4" cols="50"></textarea><br/>
				Price (in SEK): <br/>
				<input name="price" type="number" min="1" step="1"/><br/>
				Picture Upload (coming soon): <br/>
				<input readonly name="photoPath" type="text" value="placeholder" /><br/>
				<button name="action" value="addProduct">Add my product</button>
			</form>
			<?php if($result != "") {
				echo $result;
			}?>
		</div>
		
	</body>
</html>
