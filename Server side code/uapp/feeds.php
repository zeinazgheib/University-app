

<?php


	if( isset($_REQUEST['branchid'])&& isset($_REQUEST['departmentid']))
	{
		
		$sql = "SELECT * FROM feeds WHERE feeds.branchid =  '". $_REQUEST['branchid'] ."' AND feeds.departmentid =  '". $_REQUEST['departmentid'] ."'";

		include_once("class.database.php");
		$k = new Database();
		$res = $k->Query($sql);
		//lal json
		$rows = array();
		if(mysql_num_rows($res) > 0)
		{
			$response = array();
			
			echo '{"feeds":';
			while($r = mysql_fetch_assoc($res))
			{
				$rows[] = $r;
			}
			echo  json_encode($rows).'}';
		}
		
	}
?>