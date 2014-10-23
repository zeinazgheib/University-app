<?php

	if(isset($_REQUEST['branch']) && isset($_REQUEST['department'])&& isset($_REQUEST['feed'])  && isset($_REQUEST['title'])&& isset($_REQUEST['date'])){
		
		$sql = "INSERT INTO feeds (feedid, feedtitle, feedtext, branchid, departmentid, datetime) VALUES (null, '".$_REQUEST['title']."', '".$_REQUEST['feed']."', ".$_REQUEST['branch'].", ".$_REQUEST['department'].", ".$_REQUEST['date'].")";
		

		include_once("class.database.php");
		$k = new Database();
		$res = $k->Query($sql);
		//lal json
		$rows = array();
		
	echo $res ;
		
	}


?>