package com.example.kamil.godzinypracownikow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kamil on 20.08.2016.
 */
public class AdapterSzczegolowy extends BaseAdapter {


    private static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN ="Fourth";

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView dzien;
    TextView OD;
    TextView DO;
    TextView godziny;


    private class ViewHolder {
        TextView dzien;
        TextView OD;
        TextView DO;
        TextView godziny;
    }


    public AdapterSzczegolowy(Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.colmn_row, null);
            holder = new ViewHolder();
            holder.dzien=(TextView) convertView.findViewById(R.id.DZIEN);
            holder.godziny=(TextView) convertView.findViewById(R.id.GODZINY);
            holder.OD=(TextView) convertView.findViewById(R.id.OD);
            holder.DO=(TextView) convertView.findViewById(R.id.DO);

            convertView.setTag(holder);




        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map=this.list.get(position);
        holder.dzien.setText(map.get(FIRST_COLUMN));
        holder.OD.setText(map.get(THIRD_COLUMN));
        holder.DO.setText(map.get(SECOND_COLUMN));
        holder.godziny.setText(map.get(FOURTH_COLUMN));

        //value++;
        //Log.d("VALUSE", value + " valuse       adsfsdfasdfasdfasdf");


        return convertView;
    }



}
