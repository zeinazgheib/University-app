<?php

if(isset($_REQUEST['isadmin'])){
	if($_REQUEST['isadmin'] == 1){
	$sql = "SELECT * FROM feeds";
		include_once("class.database.php");
		$k = new Database();
		$res = $k->Query($sql);
		$rows = array();
		
		if(mysql_num_rows($res) > 0)	{
			
			while($r = mysql_fetch_assoc($res))
			{
				$rows['feeds'][] = $r;
				
			}
			echo  json_encode($rows);
			
		}else{
			echo "error";
		}
	}

		
	}
	
?>