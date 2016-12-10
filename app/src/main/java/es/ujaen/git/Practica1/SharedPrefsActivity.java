package es.ujaen.git.Practica1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


public class SharedPrefsActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "SESION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        /*Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

           
        }

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("key", "some value");
        editor.commit();

        // Reading from SharedPreferences
        String value = settings.getString("key", "");
        Log.i("Preferences","SESION:"+value);*/

    }

}
