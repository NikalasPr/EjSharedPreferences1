package nikalas.boyanov.ejsharedpreferences1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nikalas.boyanov.ejsharedpreferences1.Modelos.ContactosMatricula;

public class JsonActivity extends AppCompatActivity {

    /*Importante para trabajar con archivos JSON
    *   file
        project structure
        dependencies
        app
        + en la lista de dependencias
        com.google.code.gson
        el primero
        ok
        apply
        ok*/

    private TextView txtResultado;
    private Button btnCargar;
    private Button btnGuardar;

    private List<ContactosMatricula> listaContactos;

    private SharedPreferences spContactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        spContactos = getSharedPreferences(Constantes.CONTACTOS, MODE_PRIVATE);

        listaContactos = new ArrayList<>();
        inicializarVista();
        crearContactosMatricula();

        //guardar datos en un archivo JSON
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datosJSon = new Gson().toJson(listaContactos);
                SharedPreferences.Editor editor = spContactos.edit();
                editor.putString(Constantes.DATOS, datosJSon);
                editor.apply();
            }
        });
        //cargar datos del archivo JSON a la lista
        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type tipo = new TypeToken<ArrayList<ContactosMatricula>>(){}.getType();
                ArrayList<ContactosMatricula> temp = new Gson().fromJson(spContactos.getString(Constantes.DATOS,"[]"), tipo);
                listaContactos.clear();
                listaContactos.addAll(temp);
                txtResultado.setText("Tenemos "+listaContactos.size()+" contactos");
            }
        });
    }

    private void crearContactosMatricula() {
        for (int i = 0; i < 10; i++) {
            listaContactos.add(new ContactosMatricula("Nombre "+i, "Ciclo "+i,
                    "Email "+i, "Telefono "+i));
        }
    }

    private void inicializarVista() {
        txtResultado = findViewById(R.id.txtResultadoJson);
        btnCargar = findViewById(R.id.btnCargarJson);
        btnGuardar = findViewById(R.id.btnGuardarJson);
    }
}