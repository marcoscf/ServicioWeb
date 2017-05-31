package majada.marcos.servicioweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Permite conectarse al servicio web y convertir los datos a todas las otras clases.
 */
@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
class Conexion {
    private HttpURLConnection con;
    private URL url;
    private BufferedReader br;
    private StringBuilder sb;
    private String linea;

    //Para conectarnos al servicio web
    HttpURLConnection conectar(String dir) {
        con = null;
        try {
            url = new URL(dir);
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }

    //Convierte el stream de datos recibidos a String
    String convertir(InputStream is) {
        br = new BufferedReader(new InputStreamReader(is));
        sb = new StringBuilder();
        try {
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
