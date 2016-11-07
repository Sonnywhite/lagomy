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
			<table>
				<tr>
					<th>From</th><th>Content</th><th>Date</th><th></th>
				</tr>
				<?php
				$messages = getMyMessages("username", "token");
				$arrLen = count($messages);
				for($i = 0; $i < $arrLen; $i++) {
					$content_arr = explode(" ", trim($messages[$i]['content']));
					if(count($content_arr)>5)	
						$preview = $content_arr[0]." ".$content_arr[1]." ".$content_arr[2]." ".$content_arr[3]." ".$content_arr[4]." ...";
					else
						$preview = $messages[$i]['content'];
					echo "<tr id='product_row_".$i."'>"
							."<td>".$messages[$i]['from']."</td>"
							."<td id='preview_".$i."'>".$preview."</td>"
							."<td class='invis_row' id='invis_message_row_".$i."'>".$messages[$i]['content']."</td>"
							."<td>".$messages[$i]['date']."</td>"
							."<td><button onclick='switchVisibility(&quot;preview_".$i."&quot;,&quot;invis_message_row_".$i."&quot;)'>Expand</button></td>"
						."</tr>";
				}
				
				?>
			</table>
		</div>
		
	</body>
</html>
