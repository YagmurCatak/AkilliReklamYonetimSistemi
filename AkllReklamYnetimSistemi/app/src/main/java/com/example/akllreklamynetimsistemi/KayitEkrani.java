package com.example.akllreklamynetimsistemi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class KayitEkrani extends AppCompatActivity {
    Button btnKayitOl;
    EditText edtMail,edtSifreTekrar,edtSifre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        edtSifre = findViewById(R.id.editTextYeniSifre);
        edtSifreTekrar = findViewById(R.id.editTextYeniSifre2);
        edtMail = findViewById(R.id.editTextYeniKullanici);

        btnKayitOl = findViewById(R.id.btnYeniKayit);


        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((edtSifre.getText().toString().trim().equals("")) && (edtSifreTekrar.getText().toString().trim().equals("")) && (edtMail.getText().toString().trim().equals("")))
                {
                    Toast.makeText(getApplicationContext(), "Boş alan bırakmayınız.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(edtSifre.getText().toString().equals(edtSifreTekrar.getText().toString()))
                    {
                        //kullanıcı adı ve şifreyi kaydet.
                        yeniUyeKayit(edtMail.getText().toString(), edtSifre.getText().toString());

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Şifreler eşleşmemektedir.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private void yeniUyeKayit(String mail, String sifre) {

        //firebase tarafından kullanıcı oluşturulacak.
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,sifre)
                .addOnCompleteListener(KayitEkrani.this, new OnCompleteListener<AuthResult>() {

                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful())
                      {
                          //başarılı ise
                          Toast.makeText(getApplicationContext(), "üye kaydedildi.", Toast.LENGTH_LONG).show();
                          Intent intent = new Intent(KayitEkrani.this, anasayfa.class);//anasayfaya geçiş yapmaktadır.
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
