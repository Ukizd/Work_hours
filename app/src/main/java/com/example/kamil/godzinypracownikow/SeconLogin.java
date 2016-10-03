package com.example.kamil.godzinypracownikow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SeconLogin extends AppCompatActivity {

    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secon_login);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("123")) {
                    finish();
                    startActivity( new Intent(getApplicationContext(),Glowna.class));
                } else
                {
                    Toast.makeText(getApplicationContext(),"Nieporawne hasło,",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
