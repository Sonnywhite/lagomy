<?php
	require_once './core.php';
	require_once './auth.php'; 
?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_box">
			<?php include "./navigation.php"; 
			
			$products = getAllProducts();
			$arrLen = count($products);
			
			?>
			<table>
				<tr>
					<th>Product Name</th><th>Description</th><th>Price</th><th>Seller (Rating)</th><?php if($LOGGED_IN) echo "<th></th>"; ?>
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
							if($LOGGED_IN) echo "<td><button>Buy</button></td>"."</tr>";
							else echo "</tr>";					
							
				}
				
				?>
			</table>
		</div>
		
	</body>
</html>
