import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;

public class PlayFairCipher {
    private String[][] keyMat;
    private String key;
    private String excludedChar = "J";
    private String replaceChar = "I";
    private String paddingChar = "X";
    private String completingChar = "S";

    // Constructors
    public PlayFairCipher(String key){
        this.key = unique(key.toUpperCase());
        this.keyMat = method1();
    }
    public PlayFairCipher(String key, String paddingChar){
        this.key = unique(key.toUpperCase());
        this.keyMat = method1();
        this.paddingChar = paddingChar;
    }
    public PlayFairCipher(String key, String paddingChar, String completingChar){
        this.key = unique(key.toUpperCase());
        this.keyMat = method1();
        this.paddingChar = paddingChar;
        this.completingChar = ""+completingChar;
    }
    public PlayFairCipher(String key, String replace, String replaceWith, String paddingChar){
        this.key = unique(key.toUpperCase());
        this.keyMat = method1();
        excludedChar = replace;
        replaceChar = replaceWith;
        this.paddingChar = paddingChar;
    }
    public PlayFairCipher(String key, String replace, String replaceWith, String paddingChar, String completingChar){
        this.key = unique(key.toUpperCase());
        this.keyMat = method1();
        excludedChar = replace;
        replaceChar = replaceWith;
        this.paddingChar = paddingChar;
        this.completingChar = completingChar;
    }

    // Required methods
    public String[][] method1(){
        keyMat = new String[5][5];
        String[] keyChar = key.split("");
        String[] alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
        ArrayList<String> arr = new ArrayList<String>();
        Collections.addAll(arr, alpha);
        arr.remove(excludedChar);
        int index;
        for(int i = 0; i<5; i++){
            for(int j = 0; j<5; j++){
                index =  i*5 + j;
                if(index < keyChar.length){
                    keyMat[i][j] = keyChar[index];
                    arr.remove(keyChar[index]);
                }else{
                    String temp = arr.get(0);
                    keyMat[i][j] = temp;
                    arr.remove(temp);
                }
            }
        }
        return keyMat;
    }

    public String method2(String plainText){
        plainText = plainText.toUpperCase();
        if(plainText.contains("\n")){
            return splitedRecurse(plainText,"\n", true);
        }else if(plainText.contains(".")){
            return splitedRecurse(plainText,".", true);
        }else if(plainText.contains(",")){
            return splitedRecurse(plainText,",", true);
        }else if(plainText.contains(" ")){
            return splitedRecurse(plainText," ", true);
        }else{
            return encrypt(plainText);
        }
    }

    public String method3(String ciphered){
        ciphered = ciphered.toUpperCase();
        if(ciphered.contains("\n")){
            return splitedRecurse(ciphered,"\n", false);
        }else if(ciphered.contains(".")){
            return splitedRecurse(ciphered,".", false);
        }else if(ciphered.contains(",")){
            return splitedRecurse(ciphered,",", false);
        }else if(ciphered.contains(" ")){
            return splitedRecurse(ciphered," ", false);
        }else{
            return decrypt(ciphered);
        }
    }


    // Helping functions
    private String encrypt(String plainText){
        ArrayList<String> pairs = splitPairs(plainText.toUpperCase(), true);
        String ciphered = "";
        for (String s: pairs){
            String result = "";
            String a = s.split("")[0];
            String b = s.split("")[1];
            int[] locA = findIndex(a);
            int[] locB = findIndex(b);
            if(locA[0]==locB[0]){
                result += keyMat[locA[0]][(locA[1]+1)%5] + keyMat[locB[0]][(locB[1]+1)%5];
            }else if(locA[1]==locB[1]){
                result += keyMat[(locA[0]+1)%5][locA[1]] + keyMat[(locB[0]+1)%5][locB[1]];
            }else{
                result += keyMat[locA[0]][locB[1]] + keyMat[locB[0]][locA[1]];
            }ciphered += result;
        }
        return ciphered;
    }

    private String decrypt(String ciphered){
        ArrayList<String> pairs = splitPairs(ciphered.toUpperCase(), false);
        String plaintext = "";
        for (String s: pairs){
            String result = "";
            String a = s.split("")[0];
            String b = s.split("")[1];
            int[] locA = findIndex(a);
            int[] locB = findIndex(b);
            if(locA[0]==locB[0]){
                result += keyMat[locA[0]][(locA[1]+4)%5] + keyMat[locB[0]][(locB[1]+4)%5];
            }else if(locA[1]==locB[1]){
                result += keyMat[(locA[0]+4)%5][locA[1]] + keyMat[(locB[0]+4)%5][locB[1]];
            }else{
                result += keyMat[locA[0]][locB[1]] + keyMat[locB[0]][locA[1]];
            }plaintext += result;
        }
        return plaintext;
    }

    private String splitedRecurse(String plainText, String pattern, boolean encryption){
        StringJoiner join = new StringJoiner(pattern);
        String[] strs = plainText.split("["+pattern+"]");
        for (String str : strs) {
            if(encryption){
                join.add(method2(str));
            }else{
                join.add(method3(str));
            }
        }
        return join.toString();
    }

    private String unique(String key){
        String unique="";
        for(int i = 0;i<key.length(); i++){
            if(key.substring(0,i).contains(""+key.charAt(i)) || (key.charAt(i)+"").equals(excludedChar)){
                continue;
            }
            unique+=key.charAt(i);
        }
        return unique;
    }
    public int[] findIndex(String chr){
        for ( int i = 0; i < 5; ++i ) {
            for ( int j = 0; j < 5; ++j ) {
                if (keyMat[i][j].equals(chr)) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {-1, -1};
    }
    public ArrayList splitPairs(String text, boolean encryption){
        ArrayList<String> pairs = new ArrayList<String>();
        text = text.replace(excludedChar, replaceChar);
        for(int i = 0; i<text.length() ; i+=2){
            if(encryption){
                if(text.length() == i+1){
                    text += completingChar;
                }
                else if(text.charAt(i) == text.charAt(i+1)){
                    text = text.substring(0, i+1)+ paddingChar + text.substring(i+1, text.length());
                }
            }
            pairs.add(""+text.charAt(i)+text.charAt(i+1));
        }
        return pairs;
    }
    public static void print(Object O){
        System.out.print(O);
    }

    // Getters and Setters
    public String[][] getKeyMat() {
        return keyMat;
    }

    public String getCompletingChar() {
        return completingChar;
    }

    public void setCompletingChar(String completingChar) {
        this.completingChar = completingChar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = unique(key.toUpperCase());
        keyMat = method1();
    }

    public String getExcludedChar() {
        return excludedChar;
    }

    public void setExcludedChar(String excludedChar) {
        this.excludedChar = excludedChar;
        keyMat = method1();
    }

    public String getReplaceChar() {
        return replaceChar;
    }

    public void setReplaceChar(String replaceChar) {
        this.replaceChar = replaceChar;
    }

    public String getPaddingChar() {
        return paddingChar;
    }

    public void setPaddingChar(String paddingChar) {
        this.paddingChar = paddingChar;
    }
}
