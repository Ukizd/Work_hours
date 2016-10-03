package com.example.kamil.godzinypracownikow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SzczegolowyWidok extends AppCompatActivity {

    //public HashMap mapka;

    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN ="Fourth";
    String key;

    private ArrayList<HashMap<String, String>> list;
    ListView listViewSzczegolowyWidok;
    TextView textView1;
    TextView textView2;

    private final String APP = "APP";

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

        list=new ArrayList<HashMap<String,String>>();



        Log.d("APP", String.valueOf(mapkaGODZINYOD.size())+ "godzinyODMAPKA");
        Log.d("APP", String.valueOf(mapkaGODZINYDO.size())+ "godzinyDOMAPKA");

        Iterator<String> keySetIterator = mapka.keySet().iterator();

        ArrayList arrayList = new ArrayList();




            while (keySetIterator.hasNext()) {

                HashMap<String,String> temp=new HashMap<String, String>();
                key = keySetIterator.next();
                Log.d(APP, ("key: " + key + " value: " + mapka.get(key)));
                //temp.put(key, mapka.get(key));
                //list.add(temp);
                temp.put(FIRST_COLUMN, key);
                temp.put(SECOND_COLUMN, mapkaGODZINYOD.get(key));
                temp.put(THIRD_COLUMN, mapkaGODZINYDO.get(key));
                temp.put(FOURTH_COLUMN, mapka.get(key));
                arrayList.add(mapka.get(key));

                list.add(temp);

            }

        AdapterSzczegolowy adapter=new AdapterSzczegolowy(this, list);






        listViewSzczegolowyWidok.setAdapter(adapter);

//        for(int i = 0; i < arrayList.size(); i += 2) {
//            String[] time = arrayList.get(i).split(":");
//            hour += Integer.parseInt(time[0]);
//            minute += Integer.parseInt(time[1]);
//        }
//
//        hour += minute / 60;
//        minute %= 60;
//
//        String result = hour + ":" + minute;

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




        Log.d(APP, hour + ":" + minute);






    }
//   public Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
//        public int compare(Map<String, String> m1, Map<String, String> m2) {
//            Log.d(APP, key);
//            return m1.get(FIRST_COLUMN).compareTo(m2.get(FIRST_COLUMN));
//
//        }
//    };
}
