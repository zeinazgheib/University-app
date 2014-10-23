<?php
class Database
 { // Class : begin
 
 var $host;  		//Hostname, Server
 var $password; 	//Passwort MySQL
 var $user; 		//User MySQL
 var $database; 	//Datenbankname MySQL
 var $link;
 var $query;
 var $result;
 var $rows;
 
 function Database()
 { // Method : begin

  $this->host = "127.0.0.1";                  //          <<---------
  $this->password = "";           //          <<---------
  $this->user = "root";                   //          <<---------
  $this->database = "uapp";           //          <<---------
  $this->rows = 0;
 
  
 } // Method : end
 
 function OpenLink()
 { // Method : begin
  $this->link = @mysql_connect($this->host,$this->user,$this->password) or die (print "Class Database: Error while connecting to DB (link)");
 } // Method : end
 
 function SelectDB()
 { // Method : begin
 
 @mysql_select_db($this->database,$this->link) or die (print "Class Database: Error while selecting DB");
  
 } // Method : end
 
 function CloseDB()
 { // Method : begin
 mysql_close();
 } // Method : end
 
function Query($query)
{ // Method : begin
	//echo $query;
	$this->OpenLink();
	$this->SelectDB();
	$this->query = $query;
	//$this->result = mysql_query($query,$this->link) or die (print "Class Database: Error while executing Query");
	$this->result = mysql_query($query,$this->link);
	$this->CloseDB();
	return $this->result;
} // Method : end	
  
 } // Class : end
 
?>
