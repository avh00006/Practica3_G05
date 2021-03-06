package es.ujaen.git.Practica3_G05;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Francisco on 29/11/2016.
 */

/**Clase utilizada para la autenticación
 *
 */
public class Logeo extends Activity implements View.OnClickListener,Protocolo{


    static String sesion=null;
    static String id = null;
    static String expiracion = null;
    static String expire = null;
    private Button btn;
    public String user;
    public String pass;
    static public String mUser;
    static public String mPass;
    EditText usuario;
    EditText contraseña;
    public String cadena=null;
    public static final String PREFERENCE = "Sesion";
    public static final String PREFERENCE1 = "Identificacion de sesion";
    public static final String PREFERENCE2 = "Expiracion";
    public static final String PREFUS= "Usuario";
    public static final String PREFCONT = "Contraseña";

    /**Método que crea el layout de autenticación
     * @param savedInstanceState recibe los datos almacenados
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        boolean expira = false;

        SharedPreferences pref = getSharedPreferences(PREFERENCE,MODE_PRIVATE);
        if(pref!=null){

            cadena = pref.getString(PREFERENCE2,expire);
            user = pref.getString(PREFUS,mUser);
            pass = pref.getString(PREFCONT,mPass);


        }



        if(cadena!=null){

            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd-H-m-s");

            try {

                Date expiraSesion= dt1.parse(cadena);
                Date fecha2=new Date(System.currentTimeMillis()+3600000);

                if(expiraSesion.before(fecha2)){

                    expira=true;

                }else{

                    expira=false;

                }



            } catch (ParseException e) {

                e.printStackTrace();
                //expira=true;

            }

        }
        if((cadena==null)||(expira)){

            setContentView(R.layout.login);
            usuario = (EditText) findViewById(R.id.txtNick);
            contraseña = (EditText)findViewById(R.id.txtPass);
            btn=(Button)findViewById(R.id.btnLogin);
            btn.setOnClickListener(this);

        }else if(!expira){

            Intent i = new Intent(this, SharedPrefsActivity.class);
            i.putExtra(PREFERENCE2, cadena);
            i.putExtra(PREFUS,user);
            i.putExtra(PREFCONT,pass);
            i.setClass(this,SharedPrefsActivity.class);
            startActivity(i);

        }

    }


    /**Método que se utiliza para conectarse al servidor
     * @param v es el objeto de la vista
     */
    @Override
    public void onClick(View v) {



        try{

            InetSocketAddress direccion = new InetSocketAddress("192.168.56.1",6000);
            user = usuario.getText().toString();
            pass = contraseña.getText().toString();
            MiTareaAsincrona conectar = new MiTareaAsincrona(this,user,pass);
            conectar.execute(direccion);

        }catch(Exception ex){

            System.out.println("Error en el cliente"+ex.getMessage());
            String error = "IOException: " + ex.toString();

        }

    }

}




