package com.example.kamil.godzinypracownikow;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Glowna extends AppCompatActivity {

    CustomAdapter customAdapter;

    private HashMap<String, String> mapka;
    private HashMap<String, String> hashMapGODZINYOD;
    private HashMap<String, String> hashMapGODZINYDO;

    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";

    private String URL = "https://socoffee-2ec22.firebaseio.com/";
    private String APP = "APP";
    private ListView listView;
    private Firebase firebase;
    private Button button;
    private FirebaseAuth firebaseAuth;
    public ArrayList<PracownicyBeen> pracownicy;
    private ArrayList<String> lista = new ArrayList<>();
    public String login;
    public String iloscgGodzin;


    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Button etPickADate;

    private String localYear;
    private String monthString;


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
   /*
    * return new DatePickerDialog(this, mDateSetListner, mYear, mMonth,
    * mDay);
    */
                DatePickerDialog datePickerDialog = this.customDatePicker();
                return datePickerDialog;
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowna);
        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebase = new Firebase(URL);

        etPickADate = (Button) findViewById(R.id.et_datePicker);
        etPickADate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);







        pracownicy = new ArrayList<>();


        button = (Button) findViewById(R.id.buttonWyloguj);
        listView = (ListView) findViewById(R.id.listView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

        retrieveData();
    }
    DatePickerDialog.OnDateSetListener mDateSetListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDate();

        }
    };



    protected void updateDate() {
        int localMonth = (mMonth + 1);
        monthString = localMonth < 10 ? "0" + localMonth : Integer
                .toString(localMonth);
        localYear = Integer.toString(mYear);
        etPickADate.setText(monthString +"/"+ localYear);
        showDialog(DATE_DIALOG_ID);
    }

    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner,
                mYear, mMonth, mDay);
        try {

            Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField
                            .get(dpd);
                    Field datePickerFields[] = datePickerDialogField.getType()
                            .getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName())
                                || "mDaySpinner".equals(datePickerField
                                .getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }

            }
        } catch (Exception ex) {
        }
        return dpd;
    }




    private void retrieveData() {

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private void getUpdates(DataSnapshot dataSnapshot) {

        pracownicy.clear();


        for (DataSnapshot data : dataSnapshot.getChildren()) {

            mapka = new HashMap<String, String>();
            hashMapGODZINYOD = new HashMap<String, String>();
            hashMapGODZINYDO = new HashMap<String, String>();
            PracownicyBeen pracownicyBeen = new PracownicyBeen();

            login = data.getKey().toString();
            //    Log.d(APP, login+"login wyzej");
            String godzinyOd = null, godzinyDo = null;
            for (DataSnapshot child : data.getChildren()) {

                String dni = child.getKey();
//                Log.d(APP, dni);
                for (DataSnapshot childchild : child.getChildren()) {
                    //Log.d(APP, login);
                    //Log.d(APP, "child child get Key"+childchild.getKey().equals("liczbaGodzin") );
                    //Log.d(APP, "child child get Value"+childchild.getValue());
                    if (childchild.getKey().equals("liczbaGodzin")) {
                        //Log.d(APP, login +" "+ dni+" " + childchild.getValue());
                        //Log.d(APP, "child child get Value"+childchild.getValue());
                        mapka.put(dni, (String) childchild.getValue());
                    }
                    if (childchild.getKey().equals("godzinyOd")) {
                       // Log.d(APP, login +" "+ dni+" " + childchild.getValue()+"                        godzinyOd");
                        godzinyOd = String.valueOf(childchild.getValue());
                        //Log.d(APP, "child child get Value"+childchild.getValue()+"                        godzinyOd");
                        //mapka.put(dni, (String) childchild.getValue());
                        hashMapGODZINYOD.put(dni, (String) childchild.getValue());
                    }
                    if (childchild.getKey().equals("godzinyDo")) {
                       // Log.d(APP, login +" "+ dni+" " + childchild.getValue()+"                        godzinyDo");
                        godzinyDo = String.valueOf(childchild.getValue());
                        //Log.d(APP, "child child get Value"+childchild.getValue()+"                        godzinyOd");
                        //mapka.put(dni, (String) childchild.getValue());
                        hashMapGODZINYDO.put(dni, (String) childchild.getValue());
                    }
                }
                Log.d(APP, login + " " + dni + " " + godzinyOd + " " + godzinyDo);
            }
            pracownicy.add(new PracownicyBeen(login, mapka,hashMapGODZINYOD,hashMapGODZINYDO));
        }

        for (int i = 0; i < pracownicy.size(); i++) {
            Log.d(APP, pracownicy.get(i).getLogin() + "tutaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaj");
            Log.d(APP, String.valueOf(pracownicy.get(i).getGetDniAndGodziny().size()));

        }

        if (pracownicy.size() > 0) {
            // ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, pracownicy);
            //listView.setAdapter(adapter);

            customAdapter = new CustomAdapter(this, pracownicy);
            listView.setAdapter(customAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(Glowna.this, SzczegolowyWidok.class);
                    i.putExtra("Login", pracownicy.get(position).getLogin());
                    i.putExtra("Mapa", pracownicy.get(position).getGetDniAndGodziny());
                    i.putExtra("DrugaMapa", pracownicy.get(position).getHashMapGODZINYOD());
                    i.putExtra("TrzeciaMapa", pracownicy.get(position).getHashMapGODZINYDO());
                    i.putExtra("Miesiac", monthString);
                    i.putExtra("Rok", mYear);
                    Log.d(APP, "Jestem w listenerze ListView+ " + pracownicy.get(position).getGetDniAndGodziny().size());
                    Log.d(APP, "Jestem w listenerze ListView+  ODDDDDDD" + pracownicy.get(position).getHashMapGODZINYOD().size());
                    Log.d(APP, "Jestem w listenerze ListView+  DOOOOOOO" + pracownicy.get(position).getHashMapGODZINYDO().size());
                    startActivity(i);
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "brak danych", Toast.LENGTH_SHORT).show();
        }

    }

}
