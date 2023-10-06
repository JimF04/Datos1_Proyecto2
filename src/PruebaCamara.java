import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.File;

public class PruebaCamara {

    static {

        System.setProperty("java.library.path", "C:\\Users\\josee\\OneDrive\\Escritorio\\librerias_java\\jar_files\\opencv-4.7.0-1.5.9.jar");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        // Inicializa la cámara
        VideoCapture camera = new VideoCapture(0); // 0 indica la cámara predeterminada

        if (!camera.isOpened()) {
            System.out.println("No se pudo abrir la cámara.");
            return;
        }

        // Inicializa Tesseract OCR
        ITesseract tesseract = new Tesseract();

        while (true) {
            Mat frame = new Mat();
            if (camera.read(frame)) {
                // Guarda la imagen capturada en un archivo temporal
                Imgcodecs.imwrite("temp_image.png", frame);

                try {
                    // Reconoce el texto en la imagen
                    String result = tesseract.doOCR(new File("temp_image.png"));
                    System.out.println("Texto reconocido: \n" + result);
                } catch (TesseractException e) {
                    System.err.println("Error al reconocer el texto: " + e.getMessage());
                }
            }

            // Espera un tiempo antes de capturar la siguiente imagen (ajusta según sea necesario)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}