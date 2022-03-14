import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixTranspositionCipher {
    private int KEY;
    private String key;
    private boolean considerSpaces = false;
    private String completingChar = "X";

    public MatrixTranspositionCipher(String key){
        this.key = key;
        keyConvert();
    }
    public MatrixTranspositionCipher(String key, String completingChar){
        this.key = key;
        keyConvert();
        this.completingChar = completingChar;
    }
    public MatrixTranspositionCipher(String key, boolean considerSpaces){
        this.key = key;
        keyConvert();
        this.considerSpaces = considerSpaces;
    }
    public MatrixTranspositionCipher(String key, String completingChar, boolean considerSpaces){
        this.key = key;
        keyConvert();
        this.considerSpaces = considerSpaces;
        this.completingChar = completingChar;
    }
    public MatrixTranspositionCipher(int key){
        this.key = key+"";
        keyConvert();
    }
    public MatrixTranspositionCipher(int key, String completingChar){
        this.key = key+"";
        keyConvert();
        this.completingChar = completingChar;
    }
    public MatrixTranspositionCipher(int key, boolean considerSpaces){
        this.key = key+"";
        keyConvert();
        this.considerSpaces = considerSpaces;
    }
    public MatrixTranspositionCipher(int key, String completingChar, boolean considerSpaces){
        this.key = key+"";
        keyConvert();
        this.considerSpaces = considerSpaces;
        this.completingChar = completingChar;
    }

    // Required Methods
    public String method1(String plainText){
        plainText = plainText.toUpperCase();
        if(!considerSpaces){
            plainText = plainText.replace(" ", "");
        }
        int columns = (KEY+"").length();
        int rows = (int) Math.ceil(plainText.length()/(double)columns);
        String[][] Mat = new String[rows][columns];
        ArrayList<Integer> keyArr;
        String ciphered = "";
        for(int i = 0; i<rows*columns; i++){
            if(i < plainText.length()){
                Mat[i/columns][i%columns] = plainText.charAt(i)+"";
            }else{
                Mat[i/columns][i%columns] = completingChar;
            }
        }
        String tempKey = ""+KEY;
        keyArr = IntStream.range(0, key.length()).mapToObj(i -> Integer.parseInt(tempKey.charAt(i) + "")).collect(Collectors.toCollection(ArrayList::new));
        for(int i = 1; i <= keyArr.size(); i++){
            int r = keyArr.indexOf(i);
            for(int j = 0; j<rows; j++){
                ciphered += Mat[j][r];
            }
        }return ciphered;
    }

    public String method2(String ciphered){
        ciphered = ciphered.toUpperCase();
        int columns = (KEY+"").length();
        int rows = (int) Math.ceil(ciphered.length()/(double)columns);
        String[][] Mat = new String[rows][columns];
        ArrayList<Integer> keyArr;
        String plainText = "";
        String tempKey = ""+KEY;
        keyArr = IntStream.range(0, key.length()).mapToObj(i -> Integer.parseInt(tempKey.charAt(i) + "")).collect(Collectors.toCollection(ArrayList::new));
        int k = 0;
        for(int i = 1; i <= keyArr.size(); i++){
            int r = keyArr.indexOf(i);
            for(int j = 0; j<rows; j++){
                Mat[j][r] = ciphered.charAt(k++)+"";
            }
        }
        for(int i = 0; i<rows*columns; i++){
            plainText += Mat[i/columns][i%columns];
        }return plainText;
    }

    // Helping Methods
    private void keyConvert(){
        int[] arr = new int[key.length()];
        String Key="";
        for(int i = 0; i<key.length();i++){
            arr[i] = key.charAt(i);
        }
        Arrays.sort(arr);
        for(int i = 0; i<key.length();i++){
            for(int j = 0; j<key.length(); j++){
                if(arr[j]==key.charAt(i)){
                    Key+=(j+1);
                    break;
                }
            }
        }
        KEY = Integer.parseInt(Key);
    }
    public static void print(Object O){
        System.out.print(O);
    }
    public void print(String[][] O){
        for(int i = 0; i<O.length; i++){
            for(int j = 0; j<O[0].length; j++){
                print(O[i][j]+" ");
            }
            print("\n");
        }
    }

    //Getters and Setters
    public int getKEY() {
        return KEY;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        keyConvert();
    }

    public boolean isConsiderSpaces() {
        return considerSpaces;
    }

    public void setConsiderSpaces(boolean considerSpaces) {
        this.considerSpaces = considerSpaces;
    }

    public String getCompletingChar() {
        return completingChar;
    }

    public void setCompletingChar(String completingChar) {
        this.completingChar = completingChar;
    }
}
