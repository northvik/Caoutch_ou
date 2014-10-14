



<?php

// unlink("bar_liste_clean2.txt");
// $fp = fopen("bar_liste_clean2.txt", "a");
// $google=json_decode(file_get_contents("../fichier/liste_bar.json"),true);
// $i=0;
// foreach ($google as $value) {
// 	if($value["lat"]==NULL){
// 	$ch = curl_init();
//     $url='https://maps.googleapis.com/maps/api/geocode/json?address='.str_replace(' ','+',$value["address"]).',+'.$value["cp"].'+'.$value["city"].'&key=AIzaSyCdaUECZVkDChkpv0ZCMI2ejGwgLYXx9J4';

//     curl_setopt($ch, CURLOPT_URL, $url);
//     curl_setopt($ch, CURLOPT_HEADER, 0);
//     curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
//     $output = curl_exec($ch);
//     $out=json_decode($output,true);
//     $value["lat"]=$out["results"][0]["geometry"]["location"]["lat"];
//     $value["lng"]=$out["results"][0]["geometry"]["location"]["lng"];
//     curl_close($ch);
// 	$i++;
// 	}
// }

// echo $i;
// fputs($fp, json_encode($google));
// fclose($fp);



$google=json_decode(file_get_contents("bar_liste_clean2.txt"),true);
$i=0;
foreach ($google as $value) {
	if($value["lat"]==NULL){
		echo "---------------".$value["name"]."\n ".$value["address"].", ".$value["cp"]." ".$value["city"]."\n";
		$i++;
	}
}

echo $i;
