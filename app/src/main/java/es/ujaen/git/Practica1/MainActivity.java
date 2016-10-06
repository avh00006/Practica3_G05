package es.ujaen.git.Practica1;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AuthFragment au = AuthFragment.newInstance("pepe","12345");
        ft.add(R.id.main_frame,au);
        ft.addToBackStack(null);
        ft.commit();

        Button boton = (Button)findViewById(R.id.loginbtn);

        boton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String nombre = mEditUser.getText().toString();
                Autenticacion datos = new Autentication (nombre,null,null,null);
                Toast.makeText(MainActivity.this,"Pulsado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSend(View view){
        Toast.makeText(this,"Pulsado", Toast.LENGTH_SHORT).show();
    }
}
