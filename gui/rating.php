<?php
	require_once './core.php';
	require_once './auth.php'; 
?>
<?php if(!$LOGGED_IN) exit(); 

if(isset($_POST["action"]) && $_POST["action"] == "rate") {
	$gotAllParams = isset($_POST["seller"])?$_POST["seller"]!="":false;
	if($gotAllParams) $gotAllParams = isset($_POST["rating"])?$_POST["rating"]!="":false;
	
	if(!$gotAllParams) {
		$result = "Not every parameter was set!<br>"
			.'seller = '.$_POST["seller"].'<br>'
			.'rating = '.$_POST["rating"].'<br>';
	}
	else {	
		
		$result = rate(
			$_POST["seller"],
			$_POST["rating"]
		);
	}
}

?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_box">
			<?php include "./navigation.php"; ?>
			<?php if($result != "") {
				echo $result;
			}?>
			<table style="width:20%">
				<tr>
					<th>Seller</th><th></th><th></th>
				</tr>
				<?php
				$ratingOrders = getRatingOrders($_SESSION['username']);
				$arrLen = count($ratingOrders);
				for($i = 0; $i < $arrLen; $i++) {
					echo "<tr>"
							."<td>".$ratingOrders[$i]."</td>"
							."<td>"
								."<form action='./rating.php' method='post'>"
								."<input type='hidden' name='seller' value='".$ratingOrders[$i]."' />"
								."<select name='rating' required>"
								  ."<option value='1'>1</option>"
								  ."<option value='2'>2</option>"
								  ."<option value='3'>3</option>"
								  ."<option value='4'>4</option>"
								  ."<option value='5'>5</option>"
								."</select>"
								."<button name='action' value='rate'>Rate</button></form>"
							."</td>"
						."</tr>";
				}
				
				?>
			</table>
		</div>
		
	</body>
</html>
