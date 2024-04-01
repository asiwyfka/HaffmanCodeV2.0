import java.util.*;

public class Coder {

    private ReadBytesFromFile readBytesFromFile = new ReadBytesFromFile();
    private WriteBytesToFile writeBytesToFile = new WriteBytesToFile();
    private List<Byte> byteCoderListFromfile;
    private StringBuilder bitStringFromFile;
    private Map<Byte, String> sortedBytesMapFromFile;
    private Map<Byte, String> haffmanAlphabet;
    private StringBuilder codedBitString;
    private StringBuilder codedFinalBitString;
    private StringBuilder addedString;
    private byte[] byteArray;
    public List<Byte> getByteCoderListFromfile() {
        return byteCoderListFromfile;
    }
    public Map<Byte, String> getSortedBytesMapFromFile() {
        return sortedBytesMapFromFile;
    }
    public Map<Byte, String> getHaffmanAlphabet() {
        return haffmanAlphabet;
    }
    public StringBuilder getCodedFinalBitString() {
        return codedFinalBitString;
    }
    public StringBuilder getAddedString() {
        return addedString;
    }
    public byte[] getByteArray() {
        return byteArray;
    }

    // Метод для считывания информации в виде байтов из файла в ArrayList. Является сеттером для поля List<Byte> byteCoderListFromfile
    public void readCoderBytesFromFile(String fileName) {
        byteCoderListFromfile = new ArrayList<>();
        readBytesFromFile.readBytesFromFile(fileName);
        byteCoderListFromfile = readBytesFromFile.getByteListFromfile();
        System.out.println("\nБайтовое представление файла " + fileName + " в ArrayList для кодера: " + getByteCoderListFromfile());
    }

    // Метод для конвертирования байтов в строковое представление битов
    public void byteToBitString(List<Byte> byteList, String fileName) {
        bitStringFromFile = new StringBuilder();
        for (byte b : byteList) {
            // Преобразуем байт в беззнаковое целое число, чтобы получить все 8 бит
            int unsignedByte = b & 0xFF;
            // Представляем беззнаковое целое число в виде строки с использованием двоичного формата
            String binaryString = Integer.toBinaryString(unsignedByte);
            // Дополняем строку нулями слева до 8 символов
            String addString = String.format("%8s", binaryString).replace(' ', '0');
            // Преобразуем каждый байт в его битовое представление
            bitStringFromFile.append(addString).append(" ");
        }
        System.out.println("\nБитовое представление файла " + fileName + " в строке: " + bitStringFromFile);
    }

    // Метод для сортировки ArrayList по количеству встречаемых байтов, прочитаных из файла
    public void convertToSortedBytesMap(List<Byte> bytesArray) {
        // Создаем Map для хранения данных и их количества
        Map<Byte, Integer> bytesMap = new HashMap<>();

        // Перебираем элементы из списка
        for (Byte data : bytesArray) {
            // Если данные уже есть в мапе, увеличиваем количество на 1
            if (bytesMap.containsKey(data)) {
                bytesMap.put(data, bytesMap.get(data) + 1);
            } else {
                // Если данных нет в мапе, добавляем и устанавливаем количество 1
                bytesMap.put(data, 1);
            }
        }

        // Создаем список записей из неотсортированной мапы
        List<Map.Entry<Byte, Integer>> list = new LinkedList<>(bytesMap.entrySet());

        // Сортируем список по значениям в порядке возрастания
        Collections.sort(list, new Comparator<Map.Entry<Byte, Integer>>() {
            @Override
            public int compare(Map.Entry<Byte, Integer> o1, Map.Entry<Byte, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Инициализируем нашу sortedBytesMap с помощью класса LinkedHashMap для хранения отсортированных записей
        sortedBytesMapFromFile = new LinkedHashMap<>();
        for (Map.Entry<Byte, Integer> entry : list) {
            sortedBytesMapFromFile.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        System.out.println("\nОтсортированная LinkedHashMap по количеству встречаемых байтов скопированных из файла: " + getSortedBytesMapFromFile());
    }

    // Метод для создания Алфавита Хаффмана представленного в виде LinkedHashMap из отсортированного LinkedHashMap по количеству встречаемых байтов скопированных из файла
    public void codeBytesIntoAlphabet(Map<Byte, String> sortedLinkedHashMap) {
        String code = "0";
        int count = 1;
        haffmanAlphabet = new LinkedHashMap<>();
        System.out.println("\nКодирование байтов согласно алгоритму Хаффмана:");
        for (Map.Entry<Byte, String> entry : sortedLinkedHashMap.entrySet()) {
            if (count == 1) {
                haffmanAlphabet.put(entry.getKey(), code);
            } else if (count == sortedLinkedHashMap.size()) {
                code = code.replace("0", "1");
                haffmanAlphabet.put(entry.getKey(), code);
            } else {
                code = "1" + code;
                haffmanAlphabet.put(entry.getKey(), code);
            }
            System.out.println(count + " итерация.");
            count++;
            System.out.println(haffmanAlphabet);
        }
        System.out.println("\nИтоговое представление Алфавита по алгоритму Хаффмана: " + haffmanAlphabet);
    }

    // Метод для кодирования информации из файла в виде байтового ArrayList при помощи Алфавита Хаффмана представленного в виде LinkedHashMap
    public void codeBytesToBitString(List<Byte> byteList, Map<Byte, String> haffmanAlphabet) {
        codedBitString = new StringBuilder();
        // Проходим по каждому байту в списке
        for (Byte b : byteList) {
            // Проверяем, есть ли байт в карте
            if (haffmanAlphabet.containsKey(b)) {
                // Если есть, добавляем соответствующее значение в строку
                codedBitString.append(haffmanAlphabet.get(b));
            }
        }
        System.out.println("\nЗакодированные данные из файла по Алфавиту: " + codedBitString);
        // Вычисляем количество символов, которые необходимо добавить для достижения кратной длины
        int remainder = codedBitString.length() % 8;

        if (remainder != 0) {
            // Дополняем строку нулями до кратной длины
            addedString = new StringBuilder("0".repeat(8 - remainder));
            System.out.println("Добавленная строка до кратности последнего байта: " + addedString);
        } else {
            addedString = new StringBuilder("");
        }
        codedFinalBitString = codedBitString.append(addedString);
        System.out.println("Закодированные данные из файла по Алфавиту с учетом добавленной строки: " + codedFinalBitString);
    }

    // Метод для конвертации информации из файла в виде битовой строки в сжатый байтовый массив
    public void convertBitStringToBytes(StringBuilder bitString) {
        byteArray = new byte[bitString.length() / 8];

        for (int i = 0; i < bitString.length(); i += 8) {
            String byteString = bitString.substring(i, i + 8);
            byte byteValue = (byte) Integer.parseInt(byteString, 2);
            byteArray[i / 8] = byteValue;
        }
        System.out.println("\nЗаписали закодированную битовую строку: " + bitString + " в представлении байтового массива " + Arrays.toString(byteArray));
    }
}



