import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Data implements Serializable {
    private Map<Byte, String> haffmanAlphabet;
    private byte[] byteArray;
    private StringBuilder addedString;

    public Data(Map<Byte, String> haffmanAlphabet, byte[] byteArray, StringBuilder addedString) {
        this.haffmanAlphabet = haffmanAlphabet;
        this.byteArray = byteArray;
        this.addedString = addedString;
    }

    public Map<Byte, String> getHaffmanAlphabet() {
        return haffmanAlphabet;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public StringBuilder getAddedString() {
        return addedString;
    }

    @Override
    public String toString() {
        return "Data, в который входят: " +
                "Алфавит Хаффмана: " + haffmanAlphabet +
                ", массив 'сжатых' байтов: " + Arrays.toString(byteArray) +
                ", добавленная строка до кратности последнего байта: " + addedString;
    }
}