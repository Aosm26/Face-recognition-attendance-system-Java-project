import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Numara implements IGelen{
    boolean geldi=false;
    public boolean NumaraKarsilas(String numara){
        List<String> lines = new ArrayList<>();

        try {
            ArrayList<String> gelenogr = new ArrayList<String>();
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

        // Listeyi for döngüsüyle döndürme
        for (String line : lines) {
            if (numara==line) return geldi=true;
        }
        return this.geldi;
    }
}
