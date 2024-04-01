public class Main {
    public static void main(String[] args) {

        Coder coderForFileText = new Coder();

        // Считываем данные из файла File в байты в ArrayList
        coderForFileText.readCoderBytesFromFile("File");

        // Отображаем битовое представление файла File
        coderForFileText.byteToBitString(coderForFileText.getByteCoderListFromfile(), "File");

        // Сортируем полученные байты по количеству их значений
        coderForFileText.convertToSortedBytesMap(coderForFileText.getByteCoderListFromfile());

        // Кодируем байты из файла в алфавит битов с помощью алгоритма Хаффмана
        coderForFileText.codeBytesIntoAlphabet(coderForFileText.getSortedBytesMapFromFile());

        // Кодируем данные из файла в строку по нашему Алфавиту
        coderForFileText.codeBytesToBitString(coderForFileText.getByteCoderListFromfile(), coderForFileText.getHaffmanAlphabet());

        // Записываем закодированную финальную битовую строку в байтовый массив. Происходит архивация файла
        coderForFileText.convertBitStringToBytes(coderForFileText.getCodedFinalBitString());

        // Записываем сжатую информацию в файл CodedFile
        // coderForFileText.getWriteBytesToFile().writeBytesToFile(coderForFileText.getByteArray(), "CodedFile");

        // Создаем объект, в который заносим сжатую инфу, Алфавит, добавленную строку
        Data writedData = new Data(coderForFileText.getHaffmanAlphabet(), coderForFileText.getByteArray(), coderForFileText.getAddedString());

        // Записываем объект в файл
        WriteReaderData writeReaderData = new WriteReaderData();
        writeReaderData.saveObjectToFile(writedData, "CodedFile");

        // Считываем данные из файла в новый объект
        Data readedData = writeReaderData.readObjectFromFile("CodedFile");
        Decoder decoder = new Decoder();

        // Переносим данные из объекта в наш декодер
        decoder.decodeData(readedData);

        // Считываем данные из файла CodedFile в байты в ArrayList
        // decoder.readDecoderBytesFromFile("CodedFile");

        // Отображаем битовое представление из сжатого файла CodedFile
        decoder.convertByteToBitString(decoder.getByteDecoderListFromData(), "CodedFile");

        // Удаляем из битовой строки добавленную строку (недостающие биты до байта)
        decoder.deleteAddedString(decoder.getBitStringFromCodedFile(), decoder.getAddedString(), "CodedFile");

        // Используем Алфавит Хаффмана для раскодировки в первичные байты
        decoder.decodeBitFinalStringToByteList();

        // Байты записываем в новый файл
        decoder.getWriteBytesToFile().writeBytesToFile(decoder.getByteArray(), "DecodedFile");
    }
}
