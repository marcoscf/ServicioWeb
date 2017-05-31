<?php
require_once("conexion.php");
$json = file_get_contents('php://input');
$obj = json_decode($json, true);
$ref = $obj['ref'];
$json = array();
$consulta = "SELECT nregrc, area, proyecto, clasificapatri, organica, programa, economica, 
justif_gasto, cant_numero, cant_letra, fecha, id_resp, id_cons, nrefrc FROM rc WHERE nregrc LIKE '$ref'";
$resultado = mysqli_query($conectar, $consulta);
$json['rc'][] = mysqli_fetch_assoc($resultado);

mysqli_close($conectar);
echo json_encode($json);
?>
