import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class DosyaOku {
    public ArrayList<Ogrenci> veriler;
    public ArrayList<String> gelenogr;

    public DosyaOku() {
        veriler = new ArrayList<Ogrenci>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("veriler.txt"));
            String satir = br.readLine();
            while (satir != null) {
                String[] parcalar = satir.split(",");
                long numara = Long.parseLong(parcalar[0]);
                String ad = parcalar[1];
                String soyad = parcalar[2];
                Kisi ogrenci = new Ogrenci(numara, ad, soyad);
                veriler.add((Ogrenci) ogrenci);
                satir = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Dosya okunurken hata oluştu!");
        }


        try {
            gelenogr = new ArrayList<String>();
            BufferedReader br2 = new BufferedReader(new FileReader("myfile.txt"));
            String satir2 = br2.readLine();
            while (satir2 != null) {
                String gelenNumara = satir2;
                gelenogr.add(gelenNumara);
                System.out.println(gelenogr);
                satir2 = br2.readLine();
            }
            br2.close();
        } catch (Exception e) {
            System.out.println("Dosya okunurken hata oluştu!");
        }

    }

    public ArrayList<Ogrenci> getVeriler() {
        return veriler;
    }


    public ArrayList<String> Gelenogr() {
        return gelenogr;
    }

}
