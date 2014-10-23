<?php

	if(isset($_REQUEST['username']) && isset($_REQUEST['password'])&& isset($_REQUEST['name'])  && isset($_REQUEST['email']) && isset($_REQUEST['phonenumber'])){
		
		$sql = "INSERT INTO users (name, username, password, email, occupation, isadmin, id, phonenumber) VALUES ('".$_REQUEST['name']."', '".$_REQUEST['username']."', '".$_REQUEST['password']."', '".$_REQUEST['email']."','', ".$_REQUEST['isadmin'].", null, '".$_REQUEST['phonenumber']."')";
		

		include_once("class.database.php");
		$k = new Database();
		$res = $k->Query($sql);
		//lal json
		$rows = array();
		
	echo $res ;
		
	}


?>