import java.io.*;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;

import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class KisiBul {

    private final ArrayList gelennumara = new ArrayList();

    //Final değişkenler ilk değerini aldıktan sonra bir daha değiştirilemezler. Bu amaçla kullandık.
    public void KisiyiBul() {
        // OpenCV kütüphanesinden VideoCapture objesini çağırıyoruz.
        // VideoCapture sınıfı, kameralardan veya dosyalardan video akışlarına erişmenin bir yolunu sağlar
        // ve aygıt dizini, hangi kameranın kullanılacağını belirtir. Cihaz dizini 0 ise,
        // genellikle cihazdaki varsayılan kamerayı ifade eder.
        opencv_videoio.VideoCapture camera = new opencv_videoio.VideoCapture(0);

        // Bu ksıımda kameranın açılıp açmadığını kontrol ediyoruz.
        if (!camera.isOpened()) {
            System.out.println("Kamera açılamadı");
            return;
        }
        // Mat, OpenCV kitaplığında görüntüleri bir matris biçiminde depolamak ve değiştirmek için kullanılır.
        // Bu kodda frame isimli bir Mat nesnesi oluşturulmaktadır.
        // Mat nesnesi, kameradan bir video karesi depolamak için kullanılacaktır.
        // Kameradan bir kare almak ve bunu Mat nesnesinde saklamak için VideoCapture nesnesinin
        // read() yöntemini kullanabilirsiniz. Pencereyi döngü içerisinde güncellerken bu metodu kullanacağız.
        opencv_core.Mat frame = new opencv_core.Mat();

        // Görüntüyü ekranda göstermek için pencere oluştur
        opencv_highgui.namedWindow("Live Camera Stream", opencv_highgui.WINDOW_AUTOSIZE);


        // Pencereyi güncellemeyi döngü içinde yap

        // Görüntüyü al
        camera.read(frame);

        opencv_highgui.imshow("Live Camera Stream", frame);
        opencv_imgcodecs.imwrite("captured_image.jpg", frame);

        //Tanımlama için kullanacağım veri setinin dizini
        String trainingDir = "E:\\OOP\\OOP\\Faces";
        //Eşleştirme için kullanacağım diğer yüz
        File root = new File(trainingDir);
        // Bu kod, dosya sistemindeki bir dizini temsil eden root adlı bir File nesnesi oluşturur.
        // File sınıfı, dosya sistemindeki dosyalara ve dizinlere erişmenin bir yolunu sağlar.
        // trainingDir değişkeni, File yapıcısına bir argüman olarak iletilir ve
        // File nesnesinin temsil ettiği dizinin yolunu belirtir.
        FilenameFilter imgFilter = (dir, name) -> { // -dir dediğimiz kısım resimlerin konumu konum için
            name = name.toLowerCase();
            return name.endsWith(".jpg") || name.endsWith(".pgm") ||
                    name.endsWith(".png");
        };
        //Bu kodda imgFilter isimli dosya dizisi, listFiles() yöntemi çağrılarak imageFiles adlı bir File dizisi oluşturulur.
        // Bu yöntem, imgFilter nesnesi tarafından tanımlanan filtreyle eşleşen dizindeki dosyaları
        // temsil eden bir File nesneleri dizisi döndürür.
        File[] imageFiles = root.listFiles(imgFilter);
        MatVector images = new MatVector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer();
        int counter = 0;
        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            int label = Integer.parseInt(image.getName().split("\\-")[0]);
            System.out.println("Taranan Yüz: " + "2" + label);
            images.put(counter, img);
            labelsBuf.put(counter, label);
            counter++;
            //Bu kod, imageFiles dizisindeki File nesneleri üzerinde yinelenir ve her görüntü dosyasını işler.
            //Her bir File nesnesi için kod, görüntü dosyasını OpenCV kitaplığının imread() metodunu kullanarak okur
            // ve onu img adlı bir Mat nesnesinde saklar. imread() metodu, bağımsız değişken olarak görüntü dosyasının
            // yolunu alır ve görüntünün gri tonlamalı sürümünü temsil eden bir Mat nesnesi döndürür.

            //Neden görüntüleri gri tonlarına çeviriyor?
            //Çünkü makine öğrenmesi ,gri tonlarında daha doğru sonuç veriyor.

        }
        FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
        faceRecognizer.train(images, labels);


        opencv_highgui.waitKey(300);
        Mat kaydet = imread("captured_image.jpg",
                CV_LOAD_IMAGE_GRAYSCALE);
        long predictedLabel = faceRecognizer.predict(kaydet);
        faceRecognizer.predict(kaydet);
        System.out.println("Bulunan Yüz ID: 2" + predictedLabel);
        cvWaitKey(0);
        System.out.println("Numaralı ogrenci derse katilmistir: 2" + predictedLabel);
        // Bu kod, kaydet Mat nesnesi üzerinde bir tahmin yapmak için eğitilmiş FaceRecognizer nesnesini kullanır
        // ve kameraya yüzünü taratan kişiyyle en uyuşan kişinin bilgilerini yazdırır.

        try {
            // Dosya adını verin
            File file = new File("myfile.txt");

            // Dosya yoksa oluşturun
            if (!file.exists()) {
                file.createNewFile();
            }
            // Dosyaya eklemek istediğiniz satırı verin
            String content = "2" + predictedLabel;
            String numara = content;
            gelennumara.add(numara);
            // Dosyayı okuma/yazma modunda açın
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            // Satır ekleyin
            bw.write(content);
            bw.newLine();
            // Değişiklikleri kaydedin ve dosyayı kapatın
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Görüntüyü güncelleme hızını belirle (ms cinsinden)
        opencv_highgui.waitKey(30);
        opencv_highgui.destroyWindow("Live Camera Stream");
        camera.release();


    }

}
/*

DÜZENLENMEDEN ÖNCEKİ KOD

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class Main {
    public static void main(String[] args) {
            // OpenCV kütüphanesinden VideoCapture objesini çağırıyoruz.
            // VideoCapture sınıfı, kameralardan veya dosyalardan video akışlarına erişmenin bir yolunu sağlar
            // ve aygıt dizini, hangi kameranın kullanılacağını belirtir. Cihaz dizini 0 ise,
            // genellikle cihazdaki varsayılan kamerayı ifade eder.
        opencv_videoio.VideoCapture camera = new opencv_videoio.VideoCapture(0);

            // Bu ksıımda kameranın açılıp açmadığını kontrol ediyoruz.
        if(!camera.isOpened()){
            System.out.println("Kamera açılamadı");
            return;
        }

            // Mat, OpenCV kitaplığında görüntüleri bir matris biçiminde depolamak ve değiştirmek için kullanılır.
            // Bu kodda frame isimli bir Mat nesnesi oluşturulmaktadır.
            // Mat nesnesi, kameradan bir video karesi depolamak için kullanılacaktır.
            // Kameradan bir kare almak ve bunu Mat nesnesinde saklamak için VideoCapture nesnesinin
            // read() yöntemini kullanabilirsiniz. Pencereyi döngü içerisinde güncellerken bu metodu kullanacağız.
        opencv_core.Mat frame = new opencv_core.Mat();

            // Görüntüyü ekranda göstermek için arayüz oluştur
        opencv_highgui.namedWindow("Live Camera Stream", opencv_highgui.WINDOW_AUTOSIZE);

            //Sistemimizde bulunan yüzleri taramak için kullanacağım veri seti dizininin tanımlanması
        String trainingDir = "C:\\Users\\LENOVO\\Desktop\\eth\\Faces";

            // Bu kod, dosya sistemindeki bir dizini temsil eden root adlı bir File nesnesi oluşturur.
            // File sınıfı, dosya sistemindeki dosyalara ve dizinlere erişmenin bir yolunu sağlar.
            // trainingDir değişkeni, File yapıcısına bir argüman olarak iletilir ve
            // File nesnesinin temsil ettiği dizinin yolunu belirtir.

            //Dizinde yoksa dizini oluşturmak, içeriğini listelemek veya silmek gibi çeşitli işlemleri gerçekleştirmek için
            // File nesnesini kullanabilirsiniz.
        File root = new File(trainingDir);
        FilenameFilter imgFilter = new FilenameFilter() {
            //Bu kod, dosya sistemindeki bir dizini temsil eden root adlı bir File nesnesi ve dizindeki dosyaların
            // adlarını filtrelemek için kullanılan imgFilter adlı bir FilenameFilter nesnesi oluşturur.

            public boolean accept(File dir, String name) { // -dir dediğimiz kısım resimlerin konumu konum için
                name = name.toLowerCase(); //Resimlere lower-case adlandırma yaptığımız için
                return name.endsWith(".jpg") || name.endsWith(".pgm") ||
                        name.endsWith(".png");
            }
            };
            // Bu kod, bir resim koleksiyonunu depolamak için kullanılacak resimler adlı bir MatVector nesnesi ve
            // resimler için karşılık gelen etiketleri saklamak için kullanılacak labels adlı bir Mat nesnesi oluşturur.


        File[] imageFiles = root.listFiles(imgFilter);
            //Bu kodda imgFilter isimli dosya dizisi, listFiles() yöntemi çağrılarak imageFiles adlı bir File dizisi oluşturulur.
            // Bu yöntem, imgFilter nesnesi tarafından tanımlanan filtreyle eşleşen dizindeki dosyaları
            // temsil eden bir File nesneleri dizisi döndürür.
        MatVector images = new MatVector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer(); //ntBuffer sınıfı, Java NIO (Yeni G/Ç) API'sinin bir parçasıdır ve
            // bir tamsayı dizisi için bir arabellek sağlar.
        int counter = 0; //Bir kişiye ait resimleri döngü ile taramak ve makine öğrenmesinde klullanmak için

            // Bu nesneler daha sonra kodda, bir makine öğrenimi modelinin eğitimi için görüntüleri ve etiketleri depolamak
            // için kullanılacaktır. Görüntüler MatVector nesnesinde, etiketler ise Mat nesnesinde saklanacaktır.
            // IntBuffer nesnesi, Mat nesnesinin öğelerine erişmek için kullanılacaktır.


        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            int label = Integer.parseInt(image.getName().split("\\-")[0]);
            System.out.println("Tanımlanan Yüz: "+label);
            images.put(counter, img);
            labelsBuf.put(counter, label);
            counter++;
        }

            //Bu kod, imageFiles dizisindeki File nesneleri üzerinde yinelenir ve her görüntü dosyasını işler.
            //Her bir File nesnesi için kod, görüntü dosyasını OpenCV kitaplığının imread() metodunu kullanarak okur
            // ve onu img adlı bir Mat nesnesinde saklar. imread() metodu, bağımsız değişken olarak görüntü dosyasının
            // yolunu alır ve görüntünün gri tonlamalı sürümünü temsil eden bir Mat nesnesi döndürür.

            //Neden görüntüleri gri tonlarına çeviriyor?
            //Çünkü makine öğrenmesi ,gri tonlarında daha doğru sonuç veriyor.




        FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
        faceRecognizer.train(images, labels);
            //FaceRecognizer sınıfı, OpenCV kitaplığının bir parçasıdır ve yüz tanıma için bir makine öğrenme modelini
            // uygulamak için kullanılır. Modeli bir görüntü ve etiket veri kümesi üzerinde eğitmek ve yeni görüntüler üzerinde
            // tahminler yapmak için yöntemler sağlar.


        while (true) {
            camera.read(frame);
            // Açtığımız frame'ı bir döngü ile güncelliyoruz. Eş zamanlı olarak  kodu çalıştırdığımız cihazın kamerasından
            // birçok resim çekiniyor. Bunu döngü ile yapıyoruz ki gerçek zamanlı bir videoymuş gibi sürekliliği sağlayabilelim.


            opencv_imgcodecs.imwrite("captured_image.jpg", frame);
            // Bu kod, frame Mat nesnesini dosya sisteminde bir görüntü dosyası olarak kaydeder ve ardından onu
            // kaydet adlı bir Mat nesnesine geri okur. (imwrite/iread ile yazma ve okuma işlemini yapar)
            Mat kaydet = imread("captured_image.jpg", CV_LOAD_IMAGE_GRAYSCALE);

            // OpenCV kitaplığından imwrite() işlevi, çerçeve Mat nesnesini dosya sisteminde bir görüntü dosyası olarak
            // kaydetmek için kullanılır. Dosya yolunu ve Mat nesnesini bağımsız değişken olarak alır ve işlemin başarılı
            // olup olmadığını gösteren bir boole değeri döndürür.

            opencv_highgui.imshow("Canlı Kamera Görüntüsü", frame);
            opencv_highgui.waitKey(1); //Resimlerin 1 milisaniye gecikmeli olarak çekilmesini sağlar. (Video etkisi için)



            int predictedLabel = faceRecognizer.predict(kaydet);
            faceRecognizer.predict(kaydet);
            System.out.println("Bulunan Yüz ID: " + predictedLabel);
            // Bu kod, kaydet Mat nesnesi üzerinde bir tahmin yapmak için eğitilmiş FaceRecognizer nesnesini kullanır
            // ve kameraya yüzünü taratan kişiyyle en uyuşan kişinin bilgilerini yazdırır.
        }
    }
}
 */