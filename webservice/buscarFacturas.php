<?php
require_once("conexion.php");
$json = file_get_contents('php://input');
$obj = json_decode($json, true);
$campo = $obj['campo'];
$valor = $obj['valor'];

$consulta = "SELECT nregfac, nfac, fecha, imp_total, cif_nif, area FROM factura WHERE $campo LIKE '$valor'";
$resultado = mysqli_query($conectar, $consulta);
$json = array();
if(mysqli_num_rows($resultado) != 0) {
	while($fila = mysqli_fetch_assoc($resultado)) {
		$json['facturas'][] = $fila;
	}
} else {
		$json['facturas'][] = null;
}
mysqli_close($conectar);
echo json_encode($json);
?>
