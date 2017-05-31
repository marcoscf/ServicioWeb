<?php
require_once("conexion.php");
$json = file_get_contents('php://input');
$obj = json_decode($json, true);
$campo = $obj['campo'];
$valor = $obj['valor'];

$consulta = "SELECT nregrc, area, organica, programa, economica, cant_numero, fecha FROM rc WHERE $campo LIKE '$valor'";
$resultado = mysqli_query($conectar, $consulta);
$json = array();
if(mysqli_num_rows($resultado) != 0) {
	while($fila = mysqli_fetch_assoc($resultado)) {
		$json['rc'][] = $fila;
	}
} else {
		$json['rc'][] = null;
}
mysqli_close($conectar);
echo json_encode($json);
?>
