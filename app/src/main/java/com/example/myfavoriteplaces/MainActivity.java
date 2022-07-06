package com.example.myfavoriteplaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database1.getReference("mensaje");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d("Ejemplo de Firebase", "Valor: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Ejemplo de Firebase","Error al leer.", error.toException());
            }
        });

        Button cerrarSesion = (Button) findViewById(R.id.btn_cerrar_sesion);
        cerrarSesion.setOnClickListener (new View.OnClickListener()
        {
            public void onClick(View view)
            {
                AuthUI.getInstance().signOut( MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent( MainActivity.this,LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        MainActivity.this.finish();
                    }

                });
            }
        });

        Button datosUsuario = (Button) findViewById(R.id.btn_datos_usuario);
        datosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarDatosUsuario();
            }
        });

        Button escribirDB = (Button) findViewById(R.id.btn_escribir_db);
        escribirDB.setOnClickListener((v) -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("mensaje2");
            myRef.setValue("Hello World!");
        });

        Button show_db = (Button) findViewById(R.id.btn_base_datos);
        show_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarLecturaDatos();
            }
        });

    }

    public void lanzarDatosUsuario(){
        Intent i = new Intent(this, UsuarioActivity.class);
        startActivity(i);
    }

    public void lanzarLecturaDatos(){
        Intent i = new Intent(this, BaseDatosActivity.class);
        startActivity(i);
    }
}
