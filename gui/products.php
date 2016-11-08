<?php require_once './auth.php'; ?>
<?php if(!$LOGGED_IN) exit(); ?>
<html>
	<?php include "./header.php"; ?>
	<body>
	
		<div class="centered_box">
			<?php include "./navigation.php"; ?>
			<table>
				<tr>
					<th>Name</th><th>Description</th><th>Price</th><th></th>
				</tr>
				<tr>
					<td>Desk Chair</td><td>Just a normal desk chair...</td><td>200kr</td><td><button>Select Buyer</button></td>
				</tr>
				<tr>
					<td>Simple Table</td><td>A table that can be used for..</td><td>500kr</td><td><button>Select Buyer</button></td>
				</tr>
				<tr>
					<td>Wardrobe</td><td>Big wardrobe to store all your clothes.</td><td>1000kr</td><td><button>Select Buyer</button></td>
				</tr>
				<tr>
					<td>Desk Chair 2</td><td>Another normal desk chair...</td><td>210kr</td><td><button>Select Buyer</button></td>
				</tr>
				<tr>
					<td>Hifi Sound System</td><td>Pretty cool soundsystem ...</td><td>500kr</td><td><button>Select Buyer</button></td>
				</tr>
				<tr>
					<td>Bed (200x120cm)</td><td>Bigger sized bed ...</td><td>750kr</td><td><button>Select Buyer</button></td>
				</tr>
			</table>
		</div>
		
	</body>
</html>
