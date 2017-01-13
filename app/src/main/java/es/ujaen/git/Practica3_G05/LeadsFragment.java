package es.ujaen.git.Practica3_G05;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Vista para los leads del CRM
 */
public class LeadsFragment extends Fragment {

    static String datajson;
    public LeadsFragment() {
        // Required empty public constructor
    }

    public static LeadsFragment newInstance(String RespServidor) {
        LeadsFragment fragment = new LeadsFragment();
        datajson = RespServidor;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Gets parámetros
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leads, container, false);

        TextView grupmusc = (TextView) root.findViewById(R.id.btnlun);
        TextView Ej1 = (TextView) root.findViewById(R.id.ejerc1);
        TextView Ej2 = (TextView) root.findViewById(R.id.ejerc2);

        TextView Rep1 = (TextView) root.findViewById(R.id.rep1);
        TextView Rep2 = (TextView) root.findViewById(R.id.rep2);

        try {

            final JSONObject obj = new JSONObject(datajson);
            final JSONArray Ejerc1;
            final JSONArray Ejerc2;
            Ejerc1= obj.getJSONArray("ejercicio1");
            final int n = Ejerc1.length();
            for (int i = 0; i < n; ++i) {

                final JSONObject ejercicios = Ejerc1.getJSONObject(i);
                grupmusc.setText(ejercicios.getString("grupo"));
                Ej1.setText(ejercicios.getString("nombre"));
                Rep1.setText("Series:"+ejercicios.getString("serie") +"\r"+ "Repeticiones:"+ejercicios.getString("repeticion"));

            }

            Ejerc2= obj.getJSONArray("ejercicio2");
            final int s = Ejerc2.length();
            for (int i = 0; i < s; ++i) {

                final JSONObject ejercicios = Ejerc2.getJSONObject(i);
                grupmusc.setText(ejercicios.getString("grupo"));
                Ej2.setText(ejercicios.getString("nombre"));
                Rep2.setText("Series:"+ejercicios.getString("serie") +"\r"+"Repeticiones"+ ejercicios.getString("repeticion"));

            }

        } catch (JSONException e) {

            e.printStackTrace();
        }


        return root;
    }
}
