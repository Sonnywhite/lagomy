<div class="navigation">
	<?php 
	
		echo "<a ";
		if(basename($_SERVER[REQUEST_URI])=='index.php') echo "style='text-decoration:underline;'";
		echo " href='./index.php'>Home</a>";
		
		if(!$LOGGED_IN) {
			echo " | <a href='./login.php'>Login</a>";
			echo " | <a href='./new-user.php'>Register</a>";
			
		}  
		else {
			echo " | <a ";
			if(basename($_SERVER[REQUEST_URI])=='new-product.php') echo "style='text-decoration:underline;'";
			echo " href='./new-product.php'>New Product</a>";
			
			echo " | <a ";
			if(basename($_SERVER[REQUEST_URI])=='messages.php') echo "style='text-decoration:underline;'";
			echo " href='./messages.php'>My Messages</a>";
			
			echo " | <a ";
			if(basename($_SERVER[REQUEST_URI])=='rating.php') echo "style='text-decoration:underline;'";
			echo " href='./rating.php'>Rating Orders (".count(getRatingOrders($_SESSION["username"])).")</a>";
			
			echo " | <a href='./logout.php'>Logout (".$_SESSION['username'].")</a>";
		}
	?>
</div>