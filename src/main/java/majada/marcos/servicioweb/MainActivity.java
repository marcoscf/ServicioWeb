package majada.marcos.servicioweb;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.concurrent.ExecutionException;

@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
public class MainActivity extends AppCompatActivity {
    private TabHost tabs;
    private static TabHost.TabSpec spec1, spec2;
    private ListView lv1, lv2;
    private String fila, ref;
    private String[] lista1, lista2, datos;
    Intent i;
    private int num;
    private ArrayAdapter<String> adaptador1, adaptador2;
    private Drawable buscar, actu;

    //Cargamos la barra de menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barra, menu);
        buscar = menu.findItem(R.id.buscar).getIcon();
        actu = menu.findItem(R.id.actualizar).getIcon();
        buscar.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        actu.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        return super.onCreateOptionsMenu(menu);
    }

    //indicamos las acciones de los iconos de la barra de menu
    public boolean onOptionsItemSelected(MenuItem item) {
        num = tabs.getCurrentTab();
        switch (item.getItemId()) {
            case R.id.buscar:
                //Dejar esta llamada de clase aqui.
                new Dialogo(MainActivity.this, num, lv1, lv2, lista1, lista2, adaptador1, adaptador2).show();
                return true;
            case R.id.actualizar:
                lv1.setAdapter(adaptador1);
                lv2.setAdapter(adaptador2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Registramos el el cuadro de pestañas.
        tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        //Creamos las pestañas.
        spec1 = tabs.newTabSpec("tab1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Retenciones de credito");
        tabs.addTab(spec1);

        spec2 = tabs.newTabSpec("tab2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Facturas");
        tabs.addTab(spec2);

        tabs.setCurrentTab(0);

        //Ponemos el resultado del asynctask en un array.
        try {
            lista1 = new ListaRC().execute().get();
            lista2 = new ListaFacturas().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        /**
         * Rellenamos los listviews y creamos eventos onItemClickListener para obtener el nº de
         * referencia y enviarlo a otra actividad que mostrara toda la informacion sobre la rc o
         * factura con ese nº de referencia.
         */
        lv1 = (ListView) findViewById(R.id.listviewRC);
        adaptador1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista1);
        lv1.setAdapter(adaptador1);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fila = lv1.getItemAtPosition(position).toString();
                datos = fila.split(" ");
                ref = datos[1];
                i = new Intent(MainActivity.this, FichaRC.class);
                i.putExtra("ref", ref);
                startActivity(i);
            }
        });

        lv2 = (ListView) findViewById(R.id.listviewFactura);
        adaptador2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista2);
        lv2.setAdapter(adaptador2);

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fila = lv2.getItemAtPosition(position).toString();
                datos = fila.split(" ");
                ref = datos[1];
                i = new Intent(MainActivity.this, FichaFactura.class);
                i.putExtra("ref", ref);
                startActivity(i);
            }
        });
    }
}
