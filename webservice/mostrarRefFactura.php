<?php
require_once("conexion.php");
$json = file_get_contents('php://input');
$obj = json_decode($json, true);
$ref = $obj['ref'];
$consulta = "SELECT nregfac, nfac, fecha, base_imp, igic, imp_total, cif_nif, area FROM factura WHERE nregfac LIKE '$ref'";
$resultado = mysqli_query($conectar, $consulta);
$json = array();
$json['facturas'][] = mysqli_fetch_assoc($resultado);
mysqli_close($conectar);
echo json_encode($json);
?>
