package es.ujaen.git.Practica1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.CRL;

;

/**
 * Created by Francisco on 29/11/2016.
 */
public class Logeo extends Activity implements View.OnClickListener,Protocolo{



    private Button btn;
    public String user;
    public String pass;
    EditText usuario;
    EditText contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usuario = (EditText) findViewById(R.id.txtNick);
        contraseña = (EditText)findViewById(R.id.txtPass);


        //usuario.addTextChangedListener();
        btn=(Button)findViewById(R.id.btnLogin);
        btn.setOnClickListener(this);






    }

    public void  Pini(View view){
        Intent pi = new Intent(this,MainActivity.class);

        startActivity(pi);

    }


    @Override
    public void onClick(View v) {



        try{

            InetSocketAddress direccion = new InetSocketAddress("169.254.83.12",6000);
            user = usuario.getText().toString();
            pass = contraseña.getText().toString();
            MiTareaAsincrona conectar = new MiTareaAsincrona(this,user,pass);
            conectar.execute(direccion);













        }catch(Exception ex){
            System.out.println("Error en el cliente"+ex.getMessage());
            //System.out.println("Error en el cliente"+ ex.printStackTrace());
            String error = "IOException: " + ex.toString();

        }

    }

}




class MiTareaAsincrona extends AsyncTask<InetSocketAddress, Void, String> implements Protocolo {

    String sesion;
    public static final String PREFERENCE = "Sesion";
    private Context context;
    private String usuario;
    private String contraseña;

    MiTareaAsincrona (Context context,String usuario,String contraseña){

        this.context = context;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }



    @Override
    protected String doInBackground(InetSocketAddress... arg0) {
        Socket cli = null;
        String entrada=null;



        try{

            cli=new Socket();
            cli.connect(arg0[0]);
            BufferedReader is = new BufferedReader(new InputStreamReader(cli.getInputStream()));
            DataOutputStream flujo = new DataOutputStream(cli.getOutputStream());
            entrada=is.readLine();


            String user = USER +SP+usuario+CRLF;

            //System.currentTimeMillis();
            flujo.write(user.getBytes());
            entrada=is.readLine();

            String pass = PASS+SP+contraseña+CRLF;

            flujo.write(pass.getBytes());
            entrada=is.readLine();

            if(entrada.equals(ERROR)){
                sesion=null;

            }else{

                entrada=sesion;
            }




            //String salir = QUIT + CRLF;
            //flujo.write(salir.getBytes());
            //entrada=is.readLine();
            flujo.flush();
        }catch(IOException ex){
            System.out.println("Error en el cliente"+ex.getMessage());
            entrada = "IOException: " + ex.toString();
        }

        return entrada;

    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCE,context.MODE_PRIVATE).edit();
        edit.putString("Sesion",sesion);
        edit.commit();
        if(sesion!=null){

            Intent i = new Intent(context, SharedPrefsActivity.class);
            i.putExtra("sesion", sesion.toString());
            i.setClass(context,SharedPrefsActivity.class);
            context.startActivity(i);


        }



    }

    @Override
    protected void onCancelled() {

    }
}




