import java.util.*;

public class Decoder {

    private WriteBytesToFile writeBytesToFile = new WriteBytesToFile();

    private List<Byte> decodedBytes;
    private byte[] byteArray;
    private List<Byte> byteDecoderListFromData;
    private StringBuilder bitStringFromCodedFile;
    private StringBuilder bitFinalStringFromCodedFile;
    private Map<Byte, String> haffmanAlphabet;
    private StringBuilder addedString;

    public void setAddedString(StringBuilder addedString) {
        this.addedString = addedString;
    }

    public StringBuilder getAddedString() {
        return addedString;
    }

    public void setByteDecoderListFromData(byte[] array) {
        // Создаем новый ArrayList
        byteDecoderListFromData = new ArrayList<>(array.length);
        // Копируем элементы из массива в ArrayList
        for (byte value : array) {
            byteDecoderListFromData.add(value);
        }
    }

    public List<Byte> getByteDecoderListFromData() {
        return byteDecoderListFromData;
    }

    public StringBuilder getBitStringFromCodedFile() {
        return bitStringFromCodedFile;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public WriteBytesToFile getWriteBytesToFile() {
        return writeBytesToFile;
    }

    // Метод для конвертирования байтов в строковое представление битов
    public void convertByteToBitString(List<Byte> byteList, String fileName) {
        bitStringFromCodedFile = new StringBuilder();
        for (int i = 0; i < byteList.size(); i++) {
            byte b = byteList.get(i);
            // Преобразуем байт в беззнаковое целое число, чтобы получить все 8 бит
            int unsignedByte = b & 0xFF;
            // Представляем беззнаковое целое число в виде строки с использованием двоичного формата
            String binaryString = Integer.toBinaryString(unsignedByte);
            // Дополняем строку нулями слева до 8 символов
            String addString = String.format("%8s", binaryString).replace(' ', '0');
            // Преобразуем каждый байт в его битовое представление
            bitStringFromCodedFile.append(addString);
            // Добавляем пробел, если это не последний байт
            if (i != byteList.size() - 1) {
                bitStringFromCodedFile.append(" ");
            }
        }
        System.out.println("\nБитовое представление 'сжатого' массива из файла " + fileName + " в строке: " + bitStringFromCodedFile);
    }

    // Метод для удаления "добавленных" битов из последнего байта
    public void deleteAddedString(StringBuilder bitStringFromCodedFile, StringBuilder substringToRemove, String fileName) {
        int originalLength = bitStringFromCodedFile.length();
        int removeLength = substringToRemove.length();
        if (originalLength >= removeLength && bitStringFromCodedFile.substring(originalLength - removeLength).contentEquals(substringToRemove)) {
            bitFinalStringFromCodedFile = bitStringFromCodedFile.delete(originalLength - removeLength, originalLength);
            bitFinalStringFromCodedFile = new StringBuilder(bitFinalStringFromCodedFile.toString().replaceAll("\\s", ""));
            System.out.println("\nБитовое представление 'сжатого' массива из файла " + fileName + " без учета добавленной строки: " + substringToRemove + ", в последнем байте: " + bitFinalStringFromCodedFile);
        }
    }

    // Метод для расшифрования битов в разархивированные байты при помощи Алфавита Хаффмана
    public void decodeBitFinalStringToByteList() {
        decodedBytes = new ArrayList<>();
        StringBuilder currentCode = new StringBuilder();

        // Проходим по каждому символу в закодированной строке
        for (int i = 0; i < bitFinalStringFromCodedFile.length(); i++) {
            // Добавляем текущий символ к текущему коду
            currentCode.append(bitFinalStringFromCodedFile.charAt(i));
            // Проверяем, если текущий код есть в алфавите Хаффмана
            for (Map.Entry<Byte, String> entry : haffmanAlphabet.entrySet()) {
                if (entry.getValue().equals(currentCode.toString())) {
                    // Если нашли совпадение, добавляем соответствующий байт к раскодированным байтам
                    decodedBytes.add(entry.getKey());

                    // Сбрасываем текущий код
                    currentCode.setLength(0);
                    break;
                }
            }
        }

        // Копируем ArrayList<Byte> в массив байтов
        byteArray = new byte[decodedBytes.size()];
        for (int i = 0; i < decodedBytes.size(); i++) {
            byteArray[i] = decodedBytes.get(i);
        }
        System.out.println("\nРасшифрованные байты из строки " + bitFinalStringFromCodedFile + " при помощи Алфавита Хаффмана " + haffmanAlphabet + " имеют вид: " + decodedBytes);
    }

    // Метод для записи прочитанных данных объекта в декодер
    public void decodeData(Data decodedData) {
        byteDecoderListFromData = new ArrayList<>();
        // Выводим содержимое мапы
        if (decodedData != null) {
            System.out.println("\nСодержимое мапы сохранено в декодер:");
            haffmanAlphabet = decodedData.getHaffmanAlphabet();
            for (Map.Entry<Byte, String> entry : haffmanAlphabet.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            setByteDecoderListFromData(decodedData.getByteArray());
            setAddedString(decodedData.getAddedString());
            // Выводим зашифрованный массив
            System.out.println("Прочитанный 'сжатый' массив из файла: " + getByteDecoderListFromData() + " сохранен в decoder");

            // Выводим последний сохраненный бит
            System.out.println("Прочитанная добавленная строка к последнему байту из файла: " + addedString + " сохранена в decoder");
        } else {
            System.out.println("Data пустая");
        }
    }
}




