import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadBytesFromFile {

    private List<Byte> byteListFromfile;
    public List<Byte> getByteListFromfile() {
        return byteListFromfile;
    }

    // Метод считывания информации в виде байтов из файла в ArrayList. Является сеттером для поля List<Byte> bytesArrayFromfile
    public void readBytesFromFile(String fileName) {
        byteListFromfile = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte byteRead;
            while ((byteRead = (byte) fis.read()) != -1) {
                byteListFromfile.add(byteRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
