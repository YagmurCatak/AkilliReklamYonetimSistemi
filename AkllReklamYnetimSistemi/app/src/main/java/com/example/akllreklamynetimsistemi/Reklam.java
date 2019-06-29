package com.example.akllreklamynetimsistemi;

public class Reklam {
    private  String name, details, time,kategori;
    private String locationLangitude,locationLatitude;
    public Reklam(){}

    public Reklam(String FirmaAdi,String locationLangitude,String locationLatitude, String KampanyaIcerik,String KampanyaSuresi,String kategori )
    {
        this.name = FirmaAdi;
        this.locationLangitude = locationLangitude;
        this.locationLatitude = locationLatitude;
        this.details = KampanyaIcerik;
        this.time = KampanyaSuresi;
        this.kategori =kategori;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String FirmaAdi)
    {
        this.name = FirmaAdi;
    }

    public String getLocationLangitude()
    {
        return locationLangitude;
    }

    public void setLocationLangitude(String locationLangitude)
    {
        this.locationLangitude = locationLangitude;
    }
    public String getLocationLatitude()
    {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude)
    {
        this.locationLatitude = locationLatitude;
    }

    public String getKategori()
    {
        return kategori;
    }
    public void setKategori(String kategori)
    {
        this.kategori = kategori;
    }

    public String getDetails()
    {
        return details;
    }
    public void setDetails(String KampanyaIcerik)
    {
        this.details = KampanyaIcerik;
    }

    public String getTime()
    {
        return time;
    }
    public void setTime(String KampanyaSuresi)
    {
        this.time = KampanyaSuresi;
    }
}
