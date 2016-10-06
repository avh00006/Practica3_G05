package es.ujaen.git.Practica1;

/**
 * Created by ANGEL on 29/09/2016.
 */
public class Autentication {

    public static int SERVICE_PORT=6000;

    protected String mUser="user";
    protected String mPass="";
    protected String mIP="192.168.0.0";
    protected int    mPort=SERVICE_PORT;

    /**
     * Creamos el constructor
     * @param user
     * @param pass
     * @param ip
     * @param port
     */

    public Autentication(String user,String pass, String ip, int port){

        mUser=user;
        mPass=pass;
        mIP=ip;
        mPort=port;

    }

    public String getUser (){
        return mUser;
    }

    public void setUser (String user){
        mUser = user;
    }

    Autentication auth = new Autentication("pepe", "125660", "192.168.1.1", Autentication.SERVICE_PORT);
}

