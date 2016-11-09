<?php 
	require_once './core.php';
	require_once './auth.php'; 
	
	if($_POST['action'] == "login") {
		
		$correct = checkLogin($_POST['username'],$_POST['password']);
		
		// if it is correct => set up SESSION
		$_SESSION = array(
			'username'  => $_POST['username']
		);
		
		header( "refresh:5;url=./index.php" );
	}
	
?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_centered_box">
			<form class="login_form" action="./login.php" method="post">
				<input type="hidden" name="action" value="login"/>
				Username: <input required type="text" name="username" /><br/>
				Password: <input required type="password" name="password" /><br/>
				<button>Login</button>
			</form>
			<?php if($_POST['action'] == "login") { echo $correct; } ?>
		</div>
		
	</body>
</html>
