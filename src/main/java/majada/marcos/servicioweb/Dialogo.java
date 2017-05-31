package majada.marcos.servicioweb;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.concurrent.ExecutionException;

import static majada.marcos.servicioweb.R.id.b1;
import static majada.marcos.servicioweb.R.id.b2;

/**
 * Ventana de dialogo con el formulario de busqueda en el servicio web
 */
@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal"})
class Dialogo extends Dialog {
    private int num, posicion;
    private String campo, valor;
    private Context context;
    private EditText et1;
    private Spinner sp;
    private String[] lista1, lista2, valores_rc, valores_fact;
    private Button buscar, salir;
    private ListView lv1, lv2;
    private ArrayAdapter<String> adaptador1, adaptador2;
    private ArrayAdapter<CharSequence> adaptador;

    //para abrir el dialogo desde la actividad principal
    Dialogo(Context context, int num, ListView lv1, ListView lv2, String[] lista1, String[] lista2,
            ArrayAdapter<String> adaptador1, ArrayAdapter<String> adaptador2) {
        super(context);
        this.context = context;
        this.num = num;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lista1 = lista1;
        this.lista2 = lista2;
        this.adaptador1 = adaptador1;
        this.adaptador2 = adaptador2;
    }

    //el constructor del dialogo
    private void dialogo() {
        setContentView(R.layout.dialogo);
        setTitle("BUSCAR");
        sp = (Spinner) findViewById(R.id.spinner);
        if (num == 0) {
            adaptador = ArrayAdapter.createFromResource(context, R.array.campos_rc, R.layout.spinner_item);
        } else {
            adaptador = ArrayAdapter.createFromResource(context, R.array.campos_fact, R.layout.spinner_item);
        }
        sp.setAdapter(adaptador);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogo();
        buscar = (Button) findViewById(b1);
        salir = (Button) findViewById(b2);
        valores_rc = new String[]{"nregrc", "area", "organica", "cant_numero", "fecha"};
        valores_fact = new String[]{"nregfac", "nfac", "fecha", "imp_total", "cif_nif", "fecha", "area"};
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1 = (EditText) findViewById(R.id.et1);
                valor = et1.getText().toString();
                //Se escoge el elemento del array valores con la misma posicion en el spinner, para que no haya problemas con la BD
                posicion = sp.getSelectedItemPosition();
                //Dependiendo de la pestaña que estuvieramos mirando se usaran metodos distintos
                if (num == 0) {
                    try {
                        campo = valores_rc[posicion];
                        lista1 = new BuscarRC().execute(campo, valor).get();
                        adaptador1 = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, lista1);
                        lv1.setAdapter(adaptador1);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    dismiss();
                } else {
                    try {
                        campo = valores_fact[posicion];
                        lista2 = new BuscarFacturas().execute(campo, valor).get();
                        adaptador2 = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, lista2);
                        lv2.setAdapter(adaptador2);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    dismiss();
                }
            }

        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
