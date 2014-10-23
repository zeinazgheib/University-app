<?php



	if(isset($_REQUEST['username']) && isset($_REQUEST['password']))
	{
		//$sql = "SELECT * FROM feeds WHERE feeds.branchid =  1 AND feeds.facultyid =  1";
		$sql = "SELECT * FROM users WHERE users.username =  '". $_REQUEST['username'] ."' AND users.password =  '". $_REQUEST['password'] ."'";
//		$sql = "SELECT `car`.`id`,`car`.`carnb`,`car`.`code`,`car`.`typeid`,`car`.`usedesc`,`car`.`productionyear`,`car`.`circulationyear`,`car`.`chassisnb`,`car`.`enginenb` FROM `car` WHERE `car`.`carnb` =  '' AND `car`.`code` =  ''";
		include_once("class.database.php");
		$k = new Database();
		$res = $k->Query($sql);
		//lal json
		$rows = array();
		if(mysql_num_rows($res) == 0){
			echo "error";
		}else if(mysql_num_rows($res) == 1)	{
			$response = array();
			
			echo '{"user":';
			while($r = mysql_fetch_assoc($res))
			{
				$rows[] = $r;
				
			}
			echo  json_encode($rows).'}';
			
		}else{
			echo "error";
		}
		
	}


?>