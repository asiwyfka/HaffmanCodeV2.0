import java.io.*;

public class WriteReaderData {

    // Метод сохранения объекта в файл
    public void saveObjectToFile(Data data, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Сериализуем объект в файл
            oos.writeObject(data);
            System.out.println("\nОбъект типа " + data.toString() + ", успешно сохранен в файл " + filename);
        } catch (IOException e) {
            System.out.println("\nОшибка при сохранении объекта в файл: " + e.getMessage());
        }
    }

    // Метод Чтения объекта из файла
    public Data readObjectFromFile(String filename) {
        Data data = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            // Десериализуем объект из файла
            data = (Data) ois.readObject();
            System.out.println("\nОбъект типа " + data.toString() + ", успешно прочитан из файла " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nОшибка при чтении объекта из файла: " + e.getMessage());
        }
        return data;
    }
}
