import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class WriteBytesToFile {

    // Метод записи информации из байтового массива в файл
    public void writeBytesToFile(byte[] byteArray, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(byteArray);
            System.out.println("\n" + "Массив: " + Arrays.toString(byteArray) + ", успешно записан в файл " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
