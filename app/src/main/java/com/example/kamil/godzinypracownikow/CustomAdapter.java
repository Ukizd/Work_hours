package com.example.kamil.godzinypracownikow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil on 19.08.2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    public List<PracownicyBeen> data;
    public Context context;

    ArrayList<PracownicyBeen> arrayList;


    public CustomAdapter(Context context, List<PracownicyBeen> data) {
        super(context, R.layout.linear_list_row);
        this.data = data;
        this.context = context;
        arrayList = new ArrayList<PracownicyBeen>();
        arrayList.addAll(data);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View row = convertView;
        Holder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.linear_list_row, null);

            holder = new Holder();
            holder.login = (TextView) row.findViewById(R.id.textViewLogin);
            row.setTag(holder);


        }
        else {
            holder = (Holder) row.getTag();
        }

       PracownicyBeen pracownicyBeen = data.get(position);

        holder.login.setText(pracownicyBeen.getLogin());

        return row;



    }

    static class Holder {
        TextView login;

    }
    @Override
    public int getCount() {

        return data.size();
    }
}
