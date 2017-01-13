package es.ujaen.git.Practica3_G05;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link WeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER="Usuario";
    private static final String ARG_PASS="Contraseña";
    private static final String ARG_SESION="Sesion";
    private static final String ARG_DIA="Dia";


    static String muser = null;
    static String mpass = null;
    static String msesion = null;
    String mUSER;
    String mPASS;
    String mSESION;
    String mDia;
    static Handler mHandler=null;


    //private OnFragmentInteractionListener mListener;

    public WeekFragment(){}



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param us Parameter 1.
     * @param pas Parameter 2.
     * @return A new instance of fragment WeekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekFragment newInstance(Handler handler,String us, String pas,String ses,String dia) {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER, us);
        args.putString(ARG_PASS, pas);
        args.putString(ARG_SESION, ses);
        args.putString(ARG_DIA, dia);
        fragment.setArguments(args);
        mHandler=handler;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
            mUSER = getArguments().getString(ARG_USER);
            mPASS = getArguments().getString(ARG_PASS);
            mSESION= getArguments().getString(ARG_SESION);
            mDia= getArguments().getString(ARG_DIA);
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mUSER = getArguments().getString(ARG_USER);
        mPASS = getArguments().getString(ARG_PASS);
        mSESION= getArguments().getString(ARG_SESION);
        mDia= getArguments().getString(ARG_DIA);

        /**Infla el layout para este fragmento*/
        View fragmento = inflater.inflate(R.layout.fragment_week, container, false);

        Button Lunes = (Button) fragmento.findViewById(R.id.btnlu);
        Button Martes = (Button) fragmento.findViewById(R.id.btnma);
        final String respuesta=null;



        Lunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDia= "Lunes";
                rutinaEntrenamiento(mUSER,mPASS,mDia);

            }
        });


        Martes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDia= "Martes";
                rutinaEntrenamiento(mUSER,mPASS,mDia);

            }
        });

        return fragmento;

    }


    public void rutinaEntrenamiento(String user,String pass,String dia){
        final String us = user;
        final String cont = pass;
        final String giorno = dia;

        Thread background = new Thread(new Runnable() {

            @Override
            public void run() {

                Socket cli = null;
                String entrada=null;
                String datos;

                try{

                    InetSocketAddress direccion = new InetSocketAddress("192.168.56.1",6000);
                    cli=new Socket();
                    cli.connect(direccion);
                    BufferedReader is = new BufferedReader(new InputStreamReader(cli.getInputStream()));
                    DataOutputStream flujo = new DataOutputStream(cli.getOutputStream());
                    String usuario = Protocolo.USER + Protocolo.SP+us+ Protocolo.CRLF;
                    flujo.write(usuario.getBytes());
                    entrada=is.readLine();
                    String contraseña = Protocolo.PASS+ Protocolo.SP+cont+ Protocolo.CRLF;
                    flujo.write(contraseña.getBytes());
                    entrada=is.readLine();

                    if(entrada.equals("ERROR")||entrada.equals("ERROR Usuario o clave incorrectos")){

                        Logeo.sesion=null;


                    }else{

                        Logeo.sesion=entrada;

                    }

                    if((giorno!=null)){

                        String enviaDia = Protocolo.ECHO + Protocolo.SP+giorno+ Protocolo.CRLF;
                        flujo.write(enviaDia.getBytes());
                        entrada=is.readLine();
                        entrada=is.readLine();
                        //entrada=is.readLine();


                        if(giorno.equals("Lunes")){

                            threadMsg(1,entrada);

                        }else if(giorno.equals("Martes")){

                            threadMsg(2,entrada);

                        }



                    }

                    String salir = Protocolo.QUIT + Protocolo.CRLF;
                    flujo.write(salir.getBytes());
                    //entrada=is.readLine();
                    flujo.flush();
                }catch(IOException ex){

                    System.out.println("Error en el cliente"+ex.getMessage());
                    entrada = "IOException: " + ex.toString();
                    threadMsg(0,"Error en la conexión");
                }


            }


            private void threadMsg(int tipo,String msg) {



                if (!msg.equals(null) && !msg.equals("")) {
                    Message msgObj = mHandler.obtainMessage(tipo);
                    Bundle b = new Bundle();
                    b.putString("message", msg);
                    msgObj.setData(b);
                    mHandler.sendMessage(msgObj);
                }
            }





        });

        background.start();

    }

}


