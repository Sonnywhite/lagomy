<?php
	require_once './core.php';
	require_once './auth.php'; 
?>
<?php if(!$LOGGED_IN) exit(); ?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_box">
			<?php include "./navigation.php"; ?>
			<form class="new_product_form">
				Product Name: <br/>
				<input type="text" /><br/>
				Description: <br/>
				<textarea rows="4" cols="50"></textarea><br/>
				Price: <br/>
				<input type="text" /><br/>
				<button>Submit</button>
			</form>
		</div>
		
	</body>
</html>
