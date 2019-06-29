package com.example.akllreklamynetimsistemi;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class sifreGuncelle extends AppCompatActivity {

    EditText MevcutSifre;
    Button btnGuncelle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_guncelle);

        btnGuncelle = findViewById(R.id.btnGuncelle);
        MevcutSifre = findViewById(R.id.editTextMevcutMail);

        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MevcutSifre.getText().toString().trim().equals("")){

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Gerekli yerleri doldurmalısınız. ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

