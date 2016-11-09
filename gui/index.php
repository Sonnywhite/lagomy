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
					<th>Name</th><th>Description</th><th>Price</th><th>Seller (Rating)</th><?php if($LOGGED_IN) echo "<th></th>"; ?>
				</tr>
				<?php
				$products = getAllProducts();
				$arrLen = count($products);
				for($i = 0; $i < $arrLen; $i++) {
					echo "<tr id='product_row_".$i."'>"
							."<td>".$products[$i]['name']."</td>"
							."<td>".$products[$i]['description']."</td>"
							."<td>".$products[$i]['price']."</td>"
							."<td>".$products[$i]['seller']." (".$products[$i]['rating'].")</td>"
							."<td><button onclick=''>Expand</button></td>"
						."</tr>";
					echo "<tr class='invis_row' id='invis_product_row_".$i."'>"
							."<td>".$products[$i]['description']."</td>";
							if($LOGGED_IN) echo "<td><button>Buy</button></td>";
					echo "</tr>";
				}
				
				?>
			</table>
		</div>
		
	</body>
</html>
