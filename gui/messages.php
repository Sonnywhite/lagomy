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
				<tr>
					<td>resu</td><td>Hey, I want to buy your bed!</td><td>12.10.2016 20:16</td><td><button>Expand</button></td>
				</tr>
				<tr>
					<td>resu</td><td>Is the bed still available?</td><td>11.10.2016 7:50</td><td><button>Expand</button></td>
				</tr>
				<tr>
					<td>linne123</td><td>Hey man, I just have a question...</td><td>7.10.2016 21:44</td><td><button>Expand</button></td>
				</tr>
				<tr>
					<td>resu</td><td>Thanks!</td><td>16.9.2016 14:00</td><td><button>Expand</button></td>
				</tr>
				<tr>
					<td>linne123</td><td>Please rate me: ...</td><td>16.9.2016 13:48</td><td><button onclick="switchVisibility('row5')">Expand</button></td>
				</tr>
				<tr id="row5" class="invis_row">
					<td><button>Reply</button></td><td>Please rate me: Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. <button>Rate linne123</button></td><td></td><td></td>
				</tr>
			</table>
		</div>
		
	</body>
</html>
