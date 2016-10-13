package com.example.kamil.godzinypracownikow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SzczegolowyWidok extends AppCompatActivity {


    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN ="Fourth";


    String key;

    private ArrayList<HashMap<String, String>> list;
    ListView listViewSzczegolowyWidok;
    TextView textView1;
    TextView textView2;

    private final String TAG = "APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegolowy_widok);

        listViewSzczegolowyWidok = (ListView) findViewById(R.id.listViewSzczegolowyWidok);
        textView1 = (TextView) findViewById(R.id.textViewlaCZNIEGODZIN);
        textView2 = (TextView) findViewById(R.id.textViewGodzinyszczegol);


        Intent intent = getIntent();

        HashMap<String, String> mapka = (HashMap<String, String>)intent.getSerializableExtra("Mapa");
        String login = intent.getStringExtra("Login");
        HashMap<String, String> mapkaGODZINYOD = (HashMap<String, String>)intent.getSerializableExtra("DrugaMapa");
        HashMap<String, String> mapkaGODZINYDO = (HashMap<String, String>)intent.getSerializableExtra("TrzeciaMapa");
        String miesiac = intent.getStringExtra("Miesiac");
        String rok = String.valueOf(intent.getIntExtra("Rok",0));


        Log.d("APP", "MIESIAC" + miesiac);
        Log.d("APP", "Rok" + rok);

        list=new ArrayList<>();

        Log.d("APP", "co to jest mapka" + mapka);


        Iterator<String> keySetIterator = mapka.keySet().iterator();

        ArrayList arrayList = new ArrayList();

        int pozycja=1;
            while (keySetIterator.hasNext()) {

                HashMap<String,String> temp=new HashMap<String, String>();
                key = keySetIterator.next();
                String[] parts = key.split("_");
                //Log.d(APP, ("key: " + key + " value: " + mapka.get(key)));
                //temp.put(key, mapka.get(key));
                //list.add(temp);
               // Log.d(APP, ("key: " + key + " value: " + mapka.get(key))+parts[1]);
                Log.d(TAG, parts[1] +"          "+ miesiac);
                if(parts[1].equals(miesiac) ){
                    temp.put(FIRST_COLUMN, key);

                    temp.put(SECOND_COLUMN, mapkaGODZINYOD.get(key));
                    temp.put(THIRD_COLUMN, mapkaGODZINYDO.get(key));
                    temp.put(FOURTH_COLUMN, mapka.get(key));

                    arrayList.add(mapka.get(key));
                    list.add(temp);
                    pozycja++;


                }

            }
        Log.d(TAG, "przed "+list+ "wielkosc" +list.size());
        Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                return m1.get("First").compareTo(m2.get("First"));
            }
        };

        Collections.sort(list,mapComparator);
        Log.d(TAG, "po "+list+ "wielkosc" +list.size());

        AdapterSzczegolowy adapter=new AdapterSzczegolowy(this, list);

        listViewSzczegolowyWidok.setAdapter(adapter);


        int hour = 0;
        int minute = 0;

        for (int i = 0;i<arrayList.size();i++) {
            String pomocnica = String.valueOf(arrayList.get(i));
            String[] time = pomocnica.split(":");
            hour += Integer.parseInt(time[0]);
            minute += Integer.parseInt(time[1]);

        }

        hour += minute / 60;
        minute %= 60;

        //Collections.sort(list, mapComparator);

        textView2.setText(hour+":"+minute);




        Log.d(TAG, hour + ":" + minute);






    }


}
