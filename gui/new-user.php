<?php
	require_once './core.php';
	require_once './auth.php'; 
?>
<?php  

if(isset($_POST["action"]) && $_POST["action"] == "register") {
	$gotAllParams = isset($_POST["username"])?$_POST["username"]!="":false;
	if($gotAllParams) $gotAllParams = isset($_POST["password"])?$_POST["password"]!="":false;
	
	if(!$gotAllParams) {
		$result = "One or both of the parameters was not set!";
	}
	else {	
	
		$result = register(
			$_POST["username"],
			$_POST["password"]
		);
		
		header( "refresh:5;url=./index.php" );
	}
}

?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_box">
			<?php include "./navigation.php"; ?>
			<form class="new_product_form" action="./new-user.php" method="post">
				Username: <br/>
				<input name="username" type="text" /><br/>
				Password: <br/>
				<input name="password" type="password" /><br/>
				<button name="action" value="register">Register</button>
			</form>
			<?php if($result != "") {
				echo $result;
			}?>
		</div>
		
	</body>
</html>
