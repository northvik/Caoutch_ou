
<?php
//192  </a>.*<adress class="light-grey" style="display:block;"><br />(.*)<br /><br><br />([0-9]{5}) (.*)<br /></adress>
unlink("example_homepage.txt");
$fp = fopen("example_homepage.txt", "a+");

for ($i=1; $i <  4; $i++) {
	$output='';
	$ch = curl_init();
	$url = "http://www.cityvox.fr/bars-et-cafes_paris/Liste?searchPage=" . $i;
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_HEADER, 0);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$output = curl_exec($ch);

	// $output = str_replace(CHR(10),'',$output);
	// $output = str_replace(CHR(13),'',$output);
	// $output = str_replace('\n\n','',$output);
	// $output = str_replace('  ',' ',$output);
	// $output = str_replace(chr(9),' ',$output);
	// while (preg_match('#  #', $output)) {
	// 	$output = str_replace('  ',' ',$output);
	// }

	$matches = array();
	preg_match_all('#;">([a-z A-Z]*)</a>.*<adress class="light-grey" style="display:block;"> (.*)<br />([0-9]{5}) (.*) </adress>#', $output, $matches);

		$matches[0][0]='';
	var_dump($matches);
	fputs($fp, $output);
	curl_close($ch);
}
fclose($fp);
// $url='https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyCdaUECZVkDChkpv0ZCMI2ejGwgLYXx9J4'

?>

