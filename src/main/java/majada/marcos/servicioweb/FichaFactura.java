package majada.marcos.servicioweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Esta actividad muestra la ficha de una Factura
 */
@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
public class FichaFactura extends AppCompatActivity {
    TextView nregfac, nfac, fecha, base, igic, importe, empresa, area;
    String ref, fact;
    String[] datos;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ficha_factura);
        nregfac = (TextView) findViewById(R.id.nregfac);
        nfac = (TextView) findViewById(R.id.nfac);
        fecha = (TextView) findViewById(R.id.fecha_f);
        base = (TextView) findViewById(R.id.base_imp);
        igic = (TextView) findViewById(R.id.igic);
        importe = (TextView) findViewById(R.id.imp_total);
        empresa = (TextView) findViewById(R.id.empresa);
        area = (TextView) findViewById(R.id.area_f);
        i = getIntent();
        ref = i.getStringExtra("ref");

        try {
            fact = new Factura().execute(ref).get();
            datos = fact.split("@");
            nregfac.setText(datos[0]);
            nfac.setText(datos[1]);
            fecha.setText(datos[2]);
            base.setText(datos[3]);
            igic.setText(datos[4]);
            importe.setText(datos[5]);
            empresa.setText(datos[6]);
            area.setText(datos[7]);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void Salir(View v) {
        finish();
    }
}
