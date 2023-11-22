package nikalas.boyanov.ejsharedpreferences1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtEdad;

    private Button btnGuardar;
    private Button btnBorrarTodos;
    private Button btnJson;

    private ImageButton btnBorrarNombre;
    private ImageButton btnBorrarEdad;

    //declarar shared preferences
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVista();

        sp = getSharedPreferences(Constantes.PERSONA, MODE_PRIVATE);

        //si hay un archivo de sp, cargo los datos
        rellenarDatos();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNombre.getText().toString().isEmpty() || txtEdad.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }else {
                    String nombre = txtNombre.getText().toString();
                    int edad = Integer.parseInt(txtEdad.getText().toString());

                    //rellenar el fichero
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(Constantes.NOMBRE, nombre);
                    editor.putInt(Constantes.EDAD,edad);
                    //commit, deja parado el hilo hasta que se escriba
                    //apply lo hace en paralelo con otro hilo
                    editor.apply();
                }

            }
        });
        //Borrar toda la info del fichero
        btnBorrarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                txtNombre.setText("");
                txtEdad.setText("");
            }
        });
        //borrar la info con la clave seleccionada
        btnBorrarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constantes.NOMBRE);
                editor.apply();
                txtNombre.setText("");
            }
        });
        //borrar la info con la clave seleccionada
        btnBorrarEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constantes.EDAD);
                editor.apply();
                txtEdad.setText("");
            }
        });

        btnJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JsonActivity.class));
            }
        });
    }

    private void rellenarDatos() {
        String nombre = sp.getString(Constantes.NOMBRE,"");
        int edad = sp.getInt(Constantes.EDAD,-1);

        if (!nombre.isEmpty()){
            txtNombre.setText(nombre);
        }
        if (edad != -1){
            txtEdad.setText(String.valueOf(edad));
        }
    }

    private void inicializarVista() {
        txtNombre = findViewById(R.id.txtNombreMain);
        txtEdad = findViewById(R.id.txtEdadMain);
        btnGuardar = findViewById(R.id.btnGuardarMain);
        btnBorrarTodos = findViewById(R.id.btnBorrarDatosMain);
        btnBorrarNombre = findViewById(R.id.btnBorrarNombreMain);
        btnBorrarEdad = findViewById(R.id.btnBorrarEdadMain);
        btnJson = findViewById(R.id.btnJsonMain);

    }


}