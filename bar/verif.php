



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


    // private String name;//rs
    // private Double lat;//wgs84[0]
    // private Double lng;//wgs84[1]
    // private Integer telephone;
    // private String adresse_complete;// numvoie+' '+typvoie+' '+voie+', '+cp+' '+commune

unlink("../fichier/preservatif2.json");
$fp = fopen("../fichier/preservatif2.json", "a");
$google=json_decode(file_get_contents("../fichier/preservatif.json"),true);
$tab= array();
foreach ($google as $value) {
	$tab[]=array('name'=>$value['fields']['site'],
					'lat'=>$value['fields']['geo_coordinates'][0],
					'lng'=>$value['fields']['geo_coordinates'][1],
					'horaires_normal'=>$value['fields']['horaires_normal'],
					'adresse_complete'=>$value['fields']['adresse_complete']);

}

fputs($fp, json_encode($tab));
fclose($fp);

//unlink("../fichier/pharmacie-idf2.json");
//$fp = fopen("../fichier/pharmacie-idf2.json", "a");
//$google=json_decode(file_get_contents("../fichier/pharmacie-idf.json"),true);
//$tab= array();
//foreach ($google as $value) {
//    $tab[]=array('name'=>$value['fields']['rs'],
//        'lat'=>$value['fields']['wgs84'][0],
//        'lng'=>$value['fields']['wgs84'][1],
//        'telephone'=>'0'.$value['fields']['telephone'],
//        'adresse_complete'=>$value['fields']['numvoie'].' '.$value['fields']['typvoie'].' '.$value['fields']['voie'].', '.$value['fields']['cp'].' '.$value['fields']['commune']);
//
//}
//
//fputs($fp, json_encode($tab));
//fclose($fp);
