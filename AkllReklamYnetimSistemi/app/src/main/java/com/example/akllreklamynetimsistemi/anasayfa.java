package com.example.akllreklamynetimsistemi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class anasayfa extends AppCompatActivity {

    private String[] kategoriler = {"yiyecek", "Giyim", "elektronik eşya", "kozmetik"};
    private Spinner spinnerKategori;
    private ArrayAdapter<String> dataAdapterForKategori;
    EditText KonumBilgisi,magazaAdi, uzunlukBilgisi;
    public Button Arama,Belirle;
    private ArrayList<String> arrayListReklamlar;
    private ListView listreklamlar;
    private ArrayAdapter arrayAdapter;
    public int lat, lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        //spinner oluşturma
        spinnerKategori = findViewById(R.id.spinnerKategori);
        dataAdapterForKategori = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategoriler); //Spinner'lar için adapterleri hazırlıyoruz.
        spinnerKategori.setAdapter(dataAdapterForKategori);  //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.

        //listview oluşturma
        arrayListReklamlar = new ArrayList<>();
        listreklamlar = findViewById(R.id.listviewReklam);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1, arrayListReklamlar);
        listreklamlar.setAdapter(arrayAdapter);

        magazaAdi = findViewById(R.id.edtMagazaAdi);
        Arama = findViewById(R.id.btnAra);
        Belirle = findViewById(R.id.btnBelirle);
        uzunlukBilgisi =findViewById(R.id.edtMesafe);
        KonumBilgisi = findViewById(R.id.edtKonum);

        Belirle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPS();
            }
        });


        Arama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Kategori  =" ";

                if(spinnerKategori.getSelectedItem().toString().equals("yiyecek"))
                {
                    //yiyecek kategorisi seçildiyse
                    Kategori = "yiyecek";
                    Listele(Kategori, uzunlukBilgisi.getText().toString());
                }
                else if(spinnerKategori.getSelectedItem().toString().equals("Giyim"))
                {
                    Kategori = "Giyim";
                    Listele(Kategori, uzunlukBilgisi.getText().toString());
                }
                else if(spinnerKategori.getSelectedItem().toString().equals("elektronik eşya"))
                {
                    Kategori = "elektronik eşya";
                    Listele(Kategori, uzunlukBilgisi.getText().toString());
                }
                else
                {
                    Kategori = "kozmetik";
                    Listele(Kategori, uzunlukBilgisi.getText().toString());
                }
            }
        });
    }

    public void GPS()
    {
        //konum belirleniyor.
        LocationManager LocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener LocListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, LocListener);
    }

    private void Listele(final String kategori, final String uzunluk)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Reklam");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for(DataSnapshot ds:keys){
                    Reklam reklam = ds.getValue(Reklam.class);

                    if(kategori.equals(reklam.getKategori()))
                    {
                        //reklamın içeriğini bastır.
                        String[] konum = new String[2];
                        konum = KonumBilgisi.getText().toString().split(","); //split edildikten sonra tüm değerler dizide tutulur.
                        lat = Integer.valueOf(konum[0]);
                        lan = Integer.valueOf(konum[1]);
                        int lan2 = Integer.valueOf(reklam.getLocationLangitude());
                        int lat2 = Integer.valueOf(reklam.getLocationLatitude());
                        int mesafe = MesafeHesapla(lan,lat,lan2,lat2);
                        if(mesafe == Integer.valueOf(uzunluk))
                        {
                            arrayListReklamlar.add("Firma Adı: " + reklam.getName()+"\nKampanya İcerigi: "+reklam.getDetails()+"\nKampanya Süresi : " +reklam.getTime()+ "\nKategori:" +reklam.getKategori());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    public class MyLocationListener implements LocationListener{

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText( getApplicationContext(),"GPS ACILDI",Toast.LENGTH_LONG).show();
            KonumBilgisi.setText("GPS Veri bilgileri Alınıyor...");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText( getApplicationContext(),"GPS KAPATILDI",Toast.LENGTH_LONG).show();

            KonumBilgisi.setText("GPS Bağlantı Bekleniyor...");
        }

        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();

            String Text = loc.getLatitude() +","+ loc.getLongitude();
            KonumBilgisi.setText(Text);
        }
    }

    public int MesafeHesapla(int lan1,int lat1, int lan2, int lat2)
    {
        int yatayUzunluk = (lat2-lat1) * (lat2-lat1);
        int dikeyUzunluk = (lan2 -lan1) * (lan2-lan1);
        int toplamUzunluk = yatayUzunluk +dikeyUzunluk;
        int mesafe = (int) Math.sqrt(toplamUzunluk);
        return mesafe;
    }
}
