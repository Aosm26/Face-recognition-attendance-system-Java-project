import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Arayuz extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JTextArea textArea1;
    private final JTextArea textArea2;
    private final JButton ogrenciGosterButton;
    private final JButton OgrenciTarat;
    private final JButton gelmeyen;
    private final JButton ogretmen;

    public Arayuz() {


        setLayout(new FlowLayout());

        textArea1 = new JTextArea(20, 30);
        textArea1.append("Tam Öğrenci Listesi:\n");
        add(textArea1);

        textArea2 = new JTextArea(20, 30);
        textArea2.append("Gelmeyen Öğrenci Listesi:\n");
        add(textArea2);

        ogrenciGosterButton = new JButton("Öğrencileri Göster");
        ogrenciGosterButton.addActionListener(this);
        add(ogrenciGosterButton);

        gelmeyen = new JButton("Gelmeyen öğrencileri Göster");
        gelmeyen.addActionListener(this);
        add(gelmeyen);

        ogretmen = new JButton("Ogretmen geldi");
        ogretmen.addActionListener(this);
        add(ogretmen);




        OgrenciTarat = new JButton("Öğrencileri Tarat");
        OgrenciTarat.addActionListener(this);
        add(OgrenciTarat);

        // Önceki kodlar...

        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ogrenciGosterButton) {
            DosyaOku dosyaOku = new DosyaOku();
            ArrayList<Ogrenci> tumsinif = dosyaOku.getVeriler();


            for (Ogrenci kisi : tumsinif) {
                textArea1.removeAll();
                if (kisi instanceof Ogrenci) {
                    textArea1.append(kisi.getNumara() + " " + kisi.getAd() + " " + kisi.getSoyad() + " (öğrenci)\n");
                } else {
                    textArea1.append(kisi.getNumara() + " " + kisi.getAd() + " " + kisi.getSoyad() + "\n");

                }
            }

        }

        if (e.getSource() == ogretmen){
            Ogretmen ogretmen = new Ogretmen();
            ogretmen.Ogretmen();

        }

        if (e.getSource() == gelmeyen) {
            DosyaOku dosyaOku = new DosyaOku();
            ArrayList<Ogrenci> veriler = dosyaOku.getVeriler();
            ArrayList<Ogrenci> tumsinif = dosyaOku.getVeriler();

            ArrayList<String> gelenogr = dosyaOku.Gelenogr();



            for (Ogrenci ogrenci : veriler) {
                System.out.println(tumsinif);
                if (!gelenogr.contains(String.valueOf(ogrenci.getNumara()))) {

                    System.out.println(dosyaOku.gelenogr);
                    System.out.println("gelmeyen yazdı");
                    textArea2.append(ogrenci.getNumara() + " " + ogrenci.getAd() + " " + ogrenci.getSoyad() + "\n");


                }
            }
        }

        if (e.getSource() == OgrenciTarat) {
            KisiBul kisibul = new KisiBul();
            kisibul.KisiyiBul();
        }
    }
}




