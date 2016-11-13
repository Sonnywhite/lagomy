<?php
	require_once './core.php';
	require_once './auth.php'; 
?>
<?php if(!$LOGGED_IN) exit(); 

if(isset($_POST["action"]) && $_POST["action"] == "sendMessage") {
	$gotAllParams = isset($_POST["receiver"])?$_POST["receiver"]!="":false;
	if($gotAllParams) $gotAllParams = isset($_POST["content"])?$_POST["content"]!="":false;
	if($gotAllParams) $gotAllParams = isset($_SESSION["username"])?$_SESSION["username"]!="":false;
	
	if(!$gotAllParams) {
		$result = "Not every parameter was set!<br>"
			.'receiver = '.$_POST["receiver"].'<br>'
			.'content = '.$_POST["content"].'<br>'
			.'username = '.$_SESSION["username"].'<br>';
	}
	else {	
		
		$result = sendMessage(
			"".microtime(),
			$_SESSION["username"],
			$_POST["content"],
			$_POST["receiver"]
		);
	}
}

?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_box">
			<?php include "./navigation.php"; ?>
			<form class="new_product_form" action="./messages.php" method="post">
				<b>Send a message</b><br><br>
				To (username): <br/>
				<input name="receiver" type="text" /><br/>
				Content: <br/>
				<textarea name="content" rows="4" cols="50"></textarea><br/>
				<button name="action" value="sendMessage">Send</button>
			</form>
			<?php if($result != "") {
				echo $result;
			}?>
			<table>
				<tr>
					<th>From</th><th>To</th><th>Content</th>
				</tr>
				<?php
				$messages = getMyMessages($_SESSION['username']);
				$arrLen = count($messages);
				for($i = 0; $i < $arrLen; $i++) {
					echo "<tr>"
							."<td style='width:10%'>".$messages[$i]['sender']."</td>"
							."<td style='width:10%'>".$messages[$i]['receiver']."</td>"
							."<td>".$messages[$i]['message']."</td>"
						."</tr>";
				}
				
				?>
			</table>
		</div>
		
	</body>
</html>
