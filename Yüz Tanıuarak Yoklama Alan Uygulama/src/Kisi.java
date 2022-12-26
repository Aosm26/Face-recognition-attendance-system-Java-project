abstract class Kisi {
    public long numara;
    public String ad;
    public String soyad;

    public Kisi() {

    }

    public void Ogretmen() {
    }
    public Kisi(long numara, String ad, String soyad) {
        this.numara = numara;
        this.ad = ad;
        this.soyad = soyad;
    }

    public long getNumara() {
        return numara;
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }
}