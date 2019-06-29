package com.example.akllreklamynetimsistemi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public Button btnKayit,btnGiris ;
    public EditText mail,sifre;
    public TextView sifreunuttum;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        btnKayit = findViewById(R.id.btnKaydol);
        btnGiris = findViewById(R.id.btnGiris);
        mail = findViewById(R.id.editTextKullaniciAdi);
        sifre = findViewById(R.id.editTxtSifre);
        sifreunuttum = findViewById(R.id.txtSifreUnuttum);

        sifreunuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Email adresinizi giriniz.", Toast.LENGTH_LONG).show();
                    return;
                }

               auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Yeni paralo için gerekli bağlantı adresinize gönderildi.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "mail gönderme hatası.. ", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //diğer sayfaya geçiş
                Intent intent = new Intent(MainActivity.this, KayitEkrani.class);
                startActivity(intent);
            }
        });

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alanlar dolu mu boş mu kontrolü yapar.
                if(mail.getText().toString().trim().equals("")  && sifre.getText().toString().trim().equals("") )
                {
                    Toast.makeText(getApplicationContext(), "Boş alan bırakmayınız..", Toast.LENGTH_LONG).show();
                }
                else{
                    oturumAc(mail.getText().toString(),sifre.getText().toString());

                }
            }
        });
    }
    private void oturumAc(String mail, String sifre) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,sifre)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //başarılı ise
                            Toast.makeText(getApplicationContext(), "Başarılı giriş :" + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, anasayfa.class);//anasayfaya geçiş yapmaktadır.
                            startActivity(intent);
                        }
                        else
                        {
                            //başarısız ise
                            Toast.makeText(getApplicationContext(), "Hata oluştu." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
