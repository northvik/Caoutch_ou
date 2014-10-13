<?php


$google=json_decode(file_get_contents("bar_liste_clean.txt"),true);
$i=0;
foreach ($google as $value) {
	if($value["lat"]==NULL){
		echo "---------------".$value["name"]."\n ".$value["address"].", ".$value["cp"]." ".$value["city"]."\n";
		$i++;
	}
}

echo $i;
