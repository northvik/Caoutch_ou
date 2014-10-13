<?php
// example of how to use basic selector to retrieve HTML contents
include('../simple_html_dom.php');

unlink("example_homepage.txt");
$fp = fopen("example_homepage.txt", "a+");

for ($i=1; $i <  192; $i++) {
	$url = "http://www.cityvox.fr/bars-et-cafes_paris/Liste?searchPage=" . $i;
	$html = file_get_html($url);
	$output='';

	// find all link
	foreach($html->find('li.media') as $e){
		foreach($e->find('h2.media-heading a') as $titre){
			    $output = ($titre->plaintext);
		}
		foreach($e->find('adress.light-grey') as $titre){
			    $tmp = str_replace(CHR(10),'',$titre->plaintext);
			    $tmp = str_replace(CHR(13),' ',$tmp);
		    	while (preg_match('#  #', $tmp)) {
					$tmp = str_replace('  ',' ',$tmp);
				}
			    $output.=";".$tmp.";\n";
		}
		fputs($fp, $output);
	}

}
fclose($fp);
?>