package com.example.omar_.epe2webmovil2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.omar_.epe2webmovil2.model.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Persona> listPerson = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;


    EditText nomP, appP, correoP, passP, ntP;
    ListView listv_P;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Persona personaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomP = findViewById(R.id.txt_nombre);
        appP = findViewById(R.id.txt_apellido);
        correoP = findViewById(R.id.txt_correo);
        passP = findViewById(R.id.txt_contraseña);
        ntP = findViewById(R.id.txt_cuentaNT);

        listv_P = findViewById(R.id.lv_datosPersonas);
        inicializarFirebase();
        listarDatos();

        listv_P.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            personaSelected = (Persona)parent.getItemAtPosition(position);
            nomP.setText(personaSelected.getNombre());
            appP.setText(personaSelected.getApellido());
            correoP.setText(personaSelected.getCorreo());
            passP.setText(personaSelected.getContraseña());
            ntP.setText(personaSelected.getCuentaNT());
            }
        });

    }

    private void listarDatos() {
        databaseReference.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               listPerson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Persona p = objSnaptshot.getValue(Persona.class);
                    listPerson.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, listPerson);
                   listv_P.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String nombre = nomP.getText().toString();
        String correo = correoP.getText().toString();
        String contraseña = passP.getText().toString();
        String apellido = appP.getText().toString();
        String cuentant = ntP.getText().toString();
        switch (item.getItemId()){
            case R.id.icon_add:{
                if(nombre.equals("")|| correo.equals("") || contraseña.equals("") || apellido.equals("")|| cuentant.equals("")){
                    validacion();
                }
                else {
                    Persona p = new Persona();
                    p.setIDP(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setCorreo(correo);
                    p.setCuentaNT(cuentant);
                    p.setContraseña(contraseña);
                    databaseReference.child("usuarios").child(p.getIDP()).setValue(p);
                    Toast.makeText(this, "Agregar", Toast.LENGTH_LONG).show();
                    limpiarCajas();

                }
                break;
            }
            case R.id.icon_save:{
                Persona p = new Persona();
                p.setIDP(personaSelected.getIDP());
                p.setNombre(nomP.getText().toString().trim());
                p.setApellido(appP.getText().toString().trim());
                p.setCorreo(correoP.getText().toString().trim());
                p.setContraseña(passP.getText().toString().trim());
                p.setCuentaNT(ntP.getText().toString().trim());
                databaseReference.child("usuarios").child(p.getIDP()).setValue(p);
                Toast.makeText(this, "Guardar", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delet:{
                Persona p = new Persona();
                p.setIDP(personaSelected.getIDP());
                databaseReference.child("usuarios").child(p.getIDP()).removeValue();
                Toast.makeText(this, "Borrar", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            default:break;
        }

        return true;
    }

    private void limpiarCajas() {
        nomP.setText("");
        correoP.setText("");
        appP.setText("");
        passP.setText("");
        ntP.setText("");
    }

    private void validacion() {
        String nombre = nomP.getText().toString();
        String correo = correoP.getText().toString();
        String contraseña = passP.getText().toString();
        String apellido = appP.getText().toString();
        String cuentant = ntP.getText().toString();

        if (nombre.equals("")){
            nomP.setError("Required");
        }
        else if (apellido.equals("")){
            appP.setError("Required");
        }
        else if (correo.equals("")){
            correoP.setError("Required");

        }
        else if (contraseña.equals("")){
            passP.setError("Required");
        }
        else if (cuentant.equals("")){
            ntP.setError("Required");
        }

    }


}

