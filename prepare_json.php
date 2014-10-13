<?php


$google=json_decode(file_get_contents("fichier/pharmacie.json"),true);
$i=0;
foreach ($google as $value) {
 var_dump($value);
 die();
}

echo $i;

//preservatif.json
//#####################################################################
// array(5) {
//   ["datasetid"]=>
//   string(43) "distributeurspreservatifsmasculinsparis2012"
//   ["recordid"]=>
//   string(40) "473e8d17b95ff99c532a41139a401ce929af4586"
//   ["fields"]=>
//   array(10) {
//     ["horaires_vacances_hiver"]=>
//     string(6) "fermé"
//     ["horaires_vacances_ete"]=>
//     string(9) "8h à 18h"
//     ["adresse"]=>
//     string(23) "56 Bd de l'Amiral Bruix"
//     ["site"]=>
//     string(25) "Stade Jean Pierre Wimille"
//     ["annee_installation"]=>
//     string(9) "2008/2009"
//     ["acces"]=>
//     string(10) "Intérieur"
//     ["geo_coordinates"]=>
//     array(2) {
//       [0]=>
//       float(48.872568)
//       [1]=>
//       float(2.275998)
//     }
//     ["adresse_complete"]=>
//     string(43) "56 Bd de l'Amiral Bruix 75016 Paris  France"
//     ["arrond"]=>
//     int(75016)
//     ["horaires_normal"]=>
//     string(11) "7h à 22h30"
//   }
//   ["geometry"]=>
//   array(2) {
//     ["type"]=>
//     string(5) "Point"
//     ["coordinates"]=>
//     array(2) {
//       [0]=>
//       float(2.275998)
//       [1]=>
//       float(48.872568)
//     }
//   }
//   ["record_timestamp"]=>
//   string(26) "2014-08-13T20:24:04.556853"
// }
//##########################################################
//pharmacie
//
// array(5) {
//   ["datasetid"]=>
//   string(29) "carte-des-pharmacies-de-paris"
//   ["recordid"]=>
//   string(40) "f3386de2f8cc396418ac4dfb553557418060c0fa"
//   ["fields"]=>
//   array(19) {
//     ["departement"]=>
//     int(75)
//     ["commune"]=>
//     string(5) "PARIS"
//     ["typvoie"]=>
//     string(3) "RUE"
//     ["wgs84"]=>
//     array(2) {
//       [0]=>
//       float(48.8287599)
//       [1]=>
//       float(2.3695644)
//     }
//     ["dateouv"]=>
//     string(10) "2011-06-30"
//     ["nofinesset"]=>
//     int(750023335)
//     ["rs"]=>
//     string(28) "SELARL PHARMACIE MATHIAU LAM"
//     ["dateautor"]=>
//     string(10) "1994-06-29"
//     ["voie"]=>
//     string(12) "JEANNE D'ARC"
//     ["complrs"]=>
//     string(21) "PHARMACIE MATHIAU LAM"
//     ["telephone"]=>
//     int(145834022)
//     ["telecopie"]=>
//     int(145864415)
//     ["datemaj"]=>
//     string(10) "2011-07-21"
//     ["libdepartement"]=>
//     string(5) "PARIS"
//     ["numvoie"]=>
//     int(3)
//     ["nofinessej"]=>
//     int(750023327)
//     ["lng"]=>
//     float(2.3695644)
//     ["cp"]=>
//     int(75013)
//     ["lat"]=>
//     float(48.8287599)
//   }
//   ["geometry"]=>
//   array(2) {
//     ["type"]=>
//     string(5) "Point"
//     ["coordinates"]=>
//     array(2) {
//       [0]=>
//       float(2.3695644)
//       [1]=>
//       float(48.8287599)
//     }
//   }
//   ["record_timestamp"]=>
//   string(26) "2014-08-13T22:11:43.295681"
// }
