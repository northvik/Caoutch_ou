<?php

unlink("bar_liste_clean2.txt");
$fp = fopen("bar_liste_clean2.txt", "a");

$filename = "bar_liste.txt";
$handle = fopen($filename, "r");
$re = "@^(.*); (.*) ([0-9]{5}) (.*) ;$@m";
$tab=array();
$i=0;
$j=0;
while(!feof($handle)){
    $j++;
    echo $j;
    $line = fgets($handle);
    preg_match_all($re, $line, $matches);

    $output='';
    $ch = curl_init();
    $url='https://maps.googleapis.com/maps/api/geocode/json?address='.str_replace(' ','+',$matches[2][0]).',+'.$matches[3][0].'+'.$matches[4][0].'&key=AIzaSyCdaUECZVkDChkpv0ZCMI2ejGwgLYXx9J4';

    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_HEADER, 0);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $output = curl_exec($ch);
    $google=json_decode($output,true);
    if ($google["error_message"] == "You have exceeded your daily request quota for this API.") {
        sleep(600);
        $output = curl_exec($ch);
        $google=json_decode($output,true);
    }
    if (!$google["results"][0]) {
        echo $i."\n";
        $i++;
        var_dump($google);
        echo $url."\nlat: ".$google["results"][0]["geometry"]["location"]["lat"]."\nlng: ".$google["results"][0]["geometry"]["location"]["lng"]."\n";
    }else{
        $tab[]=array("name"=>$matches[1][0],
                    "address"=>$matches[2][0],
                    "cp"=>$matches[3][0],
                    "city"=>$matches[4][0],
                    "lat"=>$google["results"][0]["geometry"]["location"]["lat"],
                    "lng"=>$google["results"][0]["geometry"]["location"]["lng"]);
    }
    curl_close($ch);
}
echo $i."\n";
fputs($fp, json_encode($tab));

//var_dump($tab);

fclose($fp);
fclose($handle);