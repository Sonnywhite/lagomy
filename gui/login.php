<?php 
	require_once './core.php';
	require_once './auth.php'; 
	
	if($_POST['action'] == "login") {
		// Verify the Login with CORE
		
		// if it is correct => set up SESSION
		// TODO: use username from CORE response instead of POST
		$_SESSION = array(
			'username'  => $_POST['username']
		);
		
		header('Location: ./index.php');
	}
	
?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_centered_box">
			<form class="login_form" action="" method="post">
				<input type="hidden" name="action" value="login"/>
				Username: <input required type="text" name="username" /><br/>
				Password: <input required type="password" name="password" /><br/>
				<button>Login</button>
			</form>
		</div>
		
	</body>
</html>
