<?php



	if(isset($_REQUEST['username']) && isset($_REQUEST['password']))
	{
		//$sql = "SELECT * FROM feeds WHERE feeds.branchid =  1 AND feeds.facultyid =  1";
		$sql = "SELECT * FROM branch";
	
		include_once("class.database.php");
		$k = new Database();
		$res = $k->Query($sql);
		//lal json
		$rows = array();
		 if(mysql_num_rows($res) > 0)	{
			$response = array();
			
			echo '{"branch":';
			while($r = mysql_fetch_assoc($res))
			{
				$rows[] = $r;
				
			}
			echo  json_encode($rows);
			
		}else{
			echo "error";
		}
		$sql2 = "SELECT * FROM departement";
		$rows2 = array();
		$res2 = $k->Query($sql2);
		 if(mysql_num_rows($res2) > 0)	{
			$response2 = array();
			
			echo ',"dep":';
			while($r2 = mysql_fetch_assoc($res2))
			{
				$rows2[] = $r2;
				
			}
			echo  json_encode($rows2) . "}";
			
		}else{
			echo "error";
		}
		
	}


?>