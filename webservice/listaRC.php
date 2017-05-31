<?php
require_once("conexion.php");
$consulta = "SELECT nregrc, area, organica, programa, economica, cant_numero, fecha FROM rc";
$resultado = mysqli_query($conectar, $consulta);
/* Se ponen los resultados de la consulta dentro de un array */
$json = array();
if(mysqli_num_rows($resultado) != 0) {
	while($fila = mysqli_fetch_assoc($resultado)) {
		$json['rc'][] = $fila;
	}
} else {
		$json['rc'][] = null;
}
mysqli_close($conectar);
/* El array se envia al cliente con JSON */
echo json_encode($json);
?>
