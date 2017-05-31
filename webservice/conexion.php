<?php
$servidor = "localhost";
$base_datos = "pycabildo";
$usuario = "root";
$contraseña = "";
$conectar = mysqli_connect($servidor, $usuario, $contraseña, $base_datos);
if(!$conectar) {
	echo "Ha ocurrido un error al intentar conectarse a la base de datos.";
}
?>
