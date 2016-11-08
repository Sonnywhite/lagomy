<?php
	session_start();
	session_regenerate_id();
	
	$LOGGED_IN = false;
	if(!empty($_SESSION['username']))
		$LOGGED_IN = true;
?>