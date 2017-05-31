<?php
require_once("conexion.php");
$consulta = "SELECT nregfac, nfac, fecha, imp_total, cif_nif, area FROM factura";
$resultados = mysqli_query($conectar, $consulta);
/* Se ponen los resultados de la consulta dentro de un array */
$json = array();
if(mysqli_num_rows($resultados) != 0) {
	while($fila = mysqli_fetch_assoc($resultados)) {
		$json['facturas'][] = $fila;
	}
} else {
	$json['facturas'][] = null;
}
mysqli_close($conectar);
/* El array se envia al cliente con JSON */
echo json_encode($json);
?>
