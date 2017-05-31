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
 * Contiene las clases para trabajar con Facturas.
 */
@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
class ListaFacturas extends AsyncTask<Void, Void, String[]> {
    private String n_ref = "Nº_Ref: ", cadena, datos, ref, nfact, fecha,
            importe, empresa, area, url = "http://10.0.2.2/servicioWeb/listaFacturas.php";
    private HttpURLConnection con;
    private int i;
    private Conexion c = new Conexion();
    private InputStream in;
    private JSONObject objeto, fact;
    private JSONArray array;
    private String[] listaFacturas;

    ListaFacturas() {
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
                array = objeto.getJSONArray("facturas");
                listaFacturas = new String[array.length()];
                if (array.isNull(0)) {
                    listaFacturas = new String[]{"No hay resultados"};
                } else {
                    for (i = 0; i < array.length(); i++) {
                        fact = array.getJSONObject(i);
                        ref = String.valueOf(fact.getInt("nregfac"));
                        nfact = fact.getString("nfac");
                        fecha = fact.getString("fecha");
                        importe = String.valueOf(fact.getDouble("imp_total"));
                        empresa = fact.getString("cif_nif");
                        area = fact.getString("area");
                        cadena = n_ref.concat(ref).concat(" \nNº_Factura: ").concat(nfact).concat(" Fecha: ")
                                .concat(fecha).concat("\nImporte: ").concat(importe).concat(" € Empresa: ")
                                .concat(empresa).concat("\nArea: ").concat(area);
                        listaFacturas[i] = cadena;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        con.disconnect();
        return listaFacturas;
    }
}

@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
class BuscarFacturas extends AsyncTask<String, Void, String[]> {
    private String n_ref = "Nº_Ref: ", ref, nfact, cadena, datos, importe,
            empresa, area, fecha, url = "http://10.0.2.2/servicioWeb/buscarFacturas.php";
    private HttpURLConnection con;
    private int i;
    private Conexion c = new Conexion();
    private InputStream in;
    private JSONObject objeto, fact, env;
    private JSONArray array;
    private String[] listaFacturas;
    private DataOutputStream wr;

    BuscarFacturas() {
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
                array = objeto.getJSONArray("facturas");
                listaFacturas = new String[array.length()];
                if (array.isNull(0)) {
                    listaFacturas = new String[]{"No hay resultados"};
                } else {
                    for (i = 0; i < array.length(); i++) {
                        fact = array.getJSONObject(i);
                        ref = String.valueOf(fact.getInt("nregfac"));
                        nfact = fact.getString("nfac");
                        fecha = fact.getString("fecha");
                        importe = String.valueOf(fact.getDouble("imp_total"));
                        empresa = fact.getString("cif_nif");
                        area = fact.getString("area");
                        cadena = n_ref.concat(ref).concat(" \nNº_Factura: ").concat(nfact).concat(" Fecha: ")
                                .concat(fecha).concat("\nImporte: ").concat(importe).concat(" € Empresa: ")
                                .concat(empresa).concat("\nArea: ").concat(area);
                        listaFacturas[i] = cadena;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        con.disconnect();
        return listaFacturas;
    }

}

@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
class Factura extends AsyncTask<String, Void, String> {
    private String factura, ref, nfact, fecha, base, igic, importe,
            empresa, area, cadena, datos, url = "http://10.0.2.2/servicioWeb/mostrarRefFactura.php";
    private HttpURLConnection con;
    private Conexion c = new Conexion();
    private InputStream in;
    private JSONObject objeto, fact, env;
    private JSONArray array;
    private DataOutputStream wr;

    Factura() {
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
                array = objeto.getJSONArray("facturas");
                fact = array.getJSONObject(0);
                ref = String.valueOf(fact.getInt("nregfac"));
                nfact = fact.getString("nfac");
                fecha = fact.getString("fecha");
                base = String.valueOf(fact.getInt("base_imp"));
                igic = String.valueOf(fact.getInt("igic"));
                importe = String.valueOf(fact.getDouble("imp_total"));
                empresa = fact.getString("cif_nif");
                area = fact.getString("area");
                cadena = ref.concat("@").concat(nfact).concat("@").concat(fecha).concat("@").concat(base).concat("@")
                        .concat(igic).concat("@").concat(importe).concat("@").concat(empresa).concat("@").concat(area);
                factura = cadena;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        con.disconnect();
        return factura;
    }
}