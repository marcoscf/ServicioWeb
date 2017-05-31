package majada.marcos.servicioweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Esta actividad muestra la ficha de una RC
 */
@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
public class FichaRC extends AppCompatActivity {
    TextView tv_rc, tv_area, tv_proyecto, tv_patri, tv_ppresup, tv_jgasto, tv_cant,
            tv_cant_letra, tv_fecha, tv_resp, tv_cons, tv_factura;
    String ref, rc;
    String[] datos;
    Intent i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ficha_rc);
        tv_rc = (TextView) findViewById(R.id.tv_rc);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_proyecto = (TextView) findViewById(R.id.tv_proyecto);
        tv_patri = (TextView) findViewById(R.id.tv_patri);
        tv_ppresup = (TextView) findViewById(R.id.tv_ppresup);
        tv_jgasto = (TextView) findViewById(R.id.tv_jgasto);
        tv_cant = (TextView) findViewById(R.id.tv_cant);
        tv_cant_letra = (TextView) findViewById(R.id.tv_cant_letra);
        tv_fecha = (TextView) findViewById(R.id.tv_fecha);
        tv_resp = (TextView) findViewById(R.id.tv_resp);
        tv_cons = (TextView) findViewById(R.id.tv_cons);
        tv_factura = (TextView) findViewById(R.id.tv_factura);

        i = getIntent();
        ref = i.getStringExtra("ref");
        try {
            rc = new RC().execute(ref).get();
            datos = rc.split("@");
            tv_rc.setText(datos[0]);
            tv_area.setText(datos[1]);
            tv_proyecto.setText(datos[2]);
            tv_patri.setText(datos[3]);
            tv_ppresup.setText(datos[4]);
            tv_jgasto.setText(datos[5]);
            tv_cant.setText(datos[6]);
            tv_cant_letra.setText(datos[7]);
            tv_fecha.setText(datos[8]);
            tv_resp.setText(datos[9]);
            tv_cons.setText(datos[10]);
            tv_factura.setText(datos[11]);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void Salir(View v) {
        finish();
    }
}
