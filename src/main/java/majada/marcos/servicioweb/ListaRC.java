package majada.marcos.servicioweb;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Contiene las clases para trabajar con RC.
 * El asynctask devuelve un array de tipo string.
 * Suprimimos los warnings sobre tipos y variables globales.
 */

@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
class ListaRC extends AsyncTask<Void, Void, String[]> {
    private String n_ref = "Nº_Ref: ", ref, cadena, datos, ppresup, cantidad,
            area, fecha, url = "http://10.0.2.2/servicioWeb/listaRC.php";
    private HttpURLConnection con;
    private int i;
    private Conexion c = new Conexion();
    private InputStream in;
    private JSONObject objeto, rc;
    private JSONArray array;
    private String[] listaRC;

    ListaRC() {
    }

    @Override
    protected String[] doInBackground(Void... params) {
        try {
            con = c.conectar(url);
            con.setRequestMethod("POST");
            in = new BufferedInputStream(con.getInputStream());
            datos = c.convertir(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (datos != null) {
            try {
                /**
                 * Se sacan los objetos rc del stream enviado y se sacan los datos que nos interesan
                 * y se concatenan en una sola variable para enviarlos al listview de la clase principal.
                 */
                objeto = new JSONObject(datos);
                array = objeto.getJSONArray("rc");
                listaRC = new String[array.length()];
                //Si no hay resultados el servicio web devuelve null.
                if (array.isNull(0)) {
                    listaRC = new String[]{"No hay resultados"};
                } else {
                    for (i = 0; i < array.length(); i++) {
                        rc = array.getJSONObject(i);
                        ref = String.valueOf(rc.getInt("nregrc"));
                        area = rc.getString("area");
                        ppresup = String.valueOf(rc.getInt("organica")).concat("-").concat(rc.getString("programa"))
                                .concat("-").concat(String.valueOf(rc.getInt("economica")));
                        cantidad = String.valueOf(rc.getInt("cant_numero"));
                        fecha = rc.getString("fecha");
                        cadena = n_ref.concat(ref).concat(" \nArea: ").concat(area).concat(" Cantidad: ").concat(cantidad)
                                .concat(" € \nPartida: ").concat(ppresup).concat(" Fecha: ").concat(fecha);
                        listaRC[i] = cadena;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        con.disconnect();
        return listaRC;
    }
}

@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
class BuscarRC extends AsyncTask<String, Void, String[]> {
    private String n_ref = "Nº_Ref: ", ref, cadena, datos, ppresup, cantidad,
            area, fecha, url = "http://10.0.2.2/servicioWeb/buscarRC.php";
    private HttpURLConnection con;
    private int i;
    private Conexion c = new Conexion();
    private InputStream in;
    private JSONObject objeto, rc, env;
    private JSONArray array;
    private String[] listaRC;
    private DataOutputStream wr;

    BuscarRC() {
    }

    @Override
    protected String[] doInBackground(String... params) {
        try {
            con = c.conectar(url);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            env = new JSONObject();
            env.put("campo", params[0]);
            env.put("valor", params[1]);
            wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(env.toString());
            wr.flush();
            wr.close();
            in = new BufferedInputStream(con.getInputStream());
            datos = c.convertir(in);
            in.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if (datos != null) {
            try {
                /**
                 * Se sacan los objetos rc del stream enviado y se sacan los datos que nos interesan
                 * y se concatenan en una sola variable para enviarlos al listview de la clase principal.
                 */
                objeto = new JSONObject(datos);
                array = objeto.getJSONArray("rc");
                listaRC = new String[array.length()];
                if (array.isNull(0)) {
                    listaRC = new String[]{"No hay resultados"};
                } else {
                    for (i = 0; i < array.length(); i++) {
                        rc = array.getJSONObject(i);
                        ref = String.valueOf(rc.getInt("nregrc"));
                        area = rc.getString("area");
                        ppresup = String.valueOf(rc.getInt("organica")).concat("-").concat(rc.getString("programa"))
                                .concat("-").concat(String.valueOf(rc.getInt("economica")));
                        cantidad = String.valueOf(rc.getInt("cant_numero"));
                        fecha = rc.getString("fecha");
                        cadena = n_ref.concat(ref).concat(" \nArea: ").concat(area).concat(" Cantidad: ").concat(cantidad)
                                .concat(" € \nPartida: ").concat(ppresup).concat(" Fecha: ").concat(fecha);
                        listaRC[i] = cadena;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        con.disconnect();
        return listaRC;
    }

}

@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
class RC extends AsyncTask<String, Void, String> {
    private String ref, area, proyecto, clasif, ppresup, justif, cantidad, cantidad_letra,
            fecha, resp, cons, nfac, cadena, datos, url = "http://10.0.2.2/servicioWeb/mostrarRefRC.php";
    private HttpURLConnection con;
    private Conexion c = new Conexion();
    private InputStream in;
    private JSONObject objeto, rc, env;
    private JSONArray array;
    private String listaRC;
    private DataOutputStream wr;

    RC() {
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            con = c.conectar(url);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            env = new JSONObject();
            env.put("ref", params[0]);
            wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(env.toString());
            wr.flush();
            wr.close();
            in = new BufferedInputStream(con.getInputStream());
            datos = c.convertir(in);
            in.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if (datos != null) {
            try {
                /**
                 * Se sacan los objetos rc del stream enviado y se sacan los datos que nos interesan
                 * y se concatenan en una sola variable para enviarlos al listview de la clase principal.
                 */
                objeto = new JSONObject(datos);
                array = objeto.getJSONArray("rc");
                rc = array.getJSONObject(0);
                ref = String.valueOf(rc.getInt("nregrc"));
                area = rc.getString("area");
                proyecto = rc.getString("proyecto");
                clasif = rc.getString("clasificapatri");
                ppresup = String.valueOf(rc.getInt("organica")).concat("-").concat(rc.getString("programa"))
                        .concat("-").concat(String.valueOf(rc.getInt("economica")));
                justif = rc.getString("justif_gasto");
                cantidad = String.valueOf(rc.getInt("cant_numero"));
                cantidad_letra = rc.getString("cant_letra");
                fecha = rc.getString("fecha");
                resp = String.valueOf(rc.getInt("id_resp"));
                cons = String.valueOf(rc.getInt("id_cons"));
                nfac = String.valueOf(rc.getInt("nrefrc"));
                cadena = ref.concat("@").concat(area).concat("@").concat(proyecto).concat("@").concat(clasif).concat("@")
                        .concat(ppresup).concat("@").concat(justif).concat("@").concat(cantidad).concat("@")
                        .concat(cantidad_letra).concat("@").concat(fecha).concat("@").concat(resp).concat("@")
                        .concat(cons).concat("@").concat(nfac);
                listaRC = cadena;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        con.disconnect();
        return listaRC;
    }

}