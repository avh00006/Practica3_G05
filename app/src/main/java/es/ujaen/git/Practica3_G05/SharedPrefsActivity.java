package es.ujaen.git.Practica3_G05;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

/**Clase utilizada si la autenticación se ha realizado correctamente
 *
 */
public class SharedPrefsActivity extends AppCompatActivity {

    private String user="";
    private String pass="";
    private String sesion="";
    private String dia = null;
    private Button Lunes,Martes;
    private Handler mHandler=null;

    /**Método que crea el layout para la operación del servicio
     * @param savedInstanceState recibe los datos almacenados
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        user = getIntent().getStringExtra(Logeo.PREFUS);
        pass = getIntent().getStringExtra(Logeo.PREFCONT);
        sesion = getIntent().getStringExtra(Logeo.PREFERENCE2);

       mHandler = new Handler() {

            public void handleMessage(Message msg) {

                String aResponse = msg.getData().getString("message");

                if ((null != aResponse)) {

                    switch(msg.what){

                        case 0:

                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(),
                                            "Error", Toast.LENGTH_SHORT);

                            toast1.show();
                            break;

                        case 1:

                            FragmentManager fm = getSupportFragmentManager();
                            android.support.v4.app.Fragment f = fm.findFragmentById(R.id.main_frame2);
                            if(f==null) {
                                FragmentTransaction ft2 = fm.beginTransaction();
                                LeadsFragment lf = LeadsFragment.newInstance(aResponse);
                                ft2.add(R.id.main_frame2, lf);
                                ft2.addToBackStack(null);
                                ft2.commit();
                            }
                            break;

                        case 2:

                            FragmentManager fm2 = getSupportFragmentManager();
                            android.support.v4.app.Fragment f2 = fm2.findFragmentById(R.id.main_frame2);
                            if(f2==null) {
                                FragmentTransaction ft2 = fm2.beginTransaction();
                                LeadsFragment lf = LeadsFragment.newInstance(aResponse);
                                ft2.add(R.id.main_frame2, lf);
                                ft2.addToBackStack(null);
                                ft2.commit();
                            }
                            break;
                    }

                }else {

                    // ALERT MESSAGE
                        Toast.makeText(
                                getBaseContext(),
                                "Not Got Response From Server.",
                                Toast.LENGTH_SHORT).show();
                }

            }
        };

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        android.support.v4.app.Fragment f = fm.findFragmentById(R.id.main_frame1);
        if(f==null) {

            WeekFragment wf = WeekFragment.newInstance(mHandler,user, pass, sesion, dia);
            ft.add(R.id.main_frame1, wf);
            ft.commit();

        }

    }


}