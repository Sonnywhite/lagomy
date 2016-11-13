<?php
	require_once './core.php';
	require_once './auth.php'; 
	
	$result == "";
	if(isset($_POST['action']) && $_POST['action']=="showInterest") {
		showInterest($_SESSION['username'], $_POST['productId']);
	} elseif(isset($_POST['action']) && $_POST['action']=="sell") {
		$result = sellProduct($_POST['productId'], $_POST['buyerName'], $_SESSION['username']);
	}
?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_box">
			<?php include "./navigation.php"; 
			
			$products = getAllProducts();
			$arrLen = count($products);
			
			if($result != "")
				echo $result;
			
			?>
			<table>
				<tr>
					<th>Product Name</th><th>Description</th><th>Price</th><th>Seller (Rating)</th><?php if($LOGGED_IN) echo "<th>Interests</th>"; ?>
				</tr>
				<?php
				$products = getAllProducts();
				$arrLen = count($products);
				for($i = 0; $i < $arrLen; $i++) {
					echo "<tr id='product_row_".$i."'>"
							."<td>".$products[$i]['productName']."</td>"
							."<td>".$products[$i]['description']."</td>"
							."<td>".$products[$i]['price']."</td>"
							."<td>".$products[$i]['sellerName']." (".getRating($products[$i]['sellerName']).")"."</td>";
							if($LOGGED_IN) {
								if($_SESSION['username']==$products[$i]['sellerName']) {
									$allInterests = getAllInterests($products[$i]['productId']);
									echo "<td>";
									$arrLen2 = count($allInterests);
									for($j = 0; $j < $arrLen2; $j++) {
										echo "<form action='index.php' method='post'>".$allInterests[$j]["userName"]." <button name='action' value='sell'>Sell it</button><br>";
										echo "<input type='hidden' name='buyerName' value='".$allInterests[$j]["userName"]."'><input type='hidden' name='productId' value='".$products[$i]['productId']."'></form>";
									}
									echo "</td>";
								}
								else {
									echo "<td><form method='post' action='index.php'><input type='hidden' name='productId' value='".$products[$i]['productId']."' />";
									echo "<button name='action' value='showInterest'>Interested!</button></form></td>";
								}
								echo "</tr>";
							} 
							else 
								echo "</tr>";					
							
				}
				
				?>
			</table>
		</div>
		
	</body>
</html>
