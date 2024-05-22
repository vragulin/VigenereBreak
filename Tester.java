
/**
 * Write a description of Tester here.
 * 
 * @author V. Ragulin
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;


public class Tester {
    private CaesarCipher cc;
    private CaesarCracker crack;
    private VigenereBreaker vb;
    
    private <T> void print(T t) {
        System.out.println(t);
    }
    
    public Tester(int key, char mostCommon) {
        this.cc = new CaesarCipher(key);
        this.crack = new CaesarCracker(mostCommon);
        this.vb = new VigenereBreaker();
    }
    
    public void testCaesarCipher() {
        String text = new FileResource().asString();
        print("Text to encrypt:");
        print(text);
        print("Encrypted text:");
        print(cc.encrypt(text));
    }
    
    public void testCaesarCrackr() {
        String text = new FileResource().asString();
        print("Encrypted text:");
        String encrypted = cc.encrypt(text);
        print(encrypted);
        
        print("Cracker text:");
        print(crack.decrypt(encrypted));
        print("Key found:");
        print(crack.getKey(encrypted));
    }
    
    public void testVigenereCipher(String key) {
        VigenereCipher stringvc = new VigenereCipher(key);
        int[] intkey = {17, 14, 12, 4};  // ROME
        VigenereCipher intvc = new VigenereCipher(intkey);
        String input = new FileResource().asString();
        
        String intsencrypted = intvc.encrypt(input);
        String stringencrypted = stringvc.encrypt(input);
        
        print("Text to encrypt:");
        print(input);
        
        print("Encrypted with ints;");
        print(intsencrypted);

        print("Encrypted with string;");
        print(stringencrypted);
    }
    
    public void testTryKeyLength() {
        String enc = new FileResource("VigenereTestData/athens_keyflute.txt").asString();
        int[] key = vb.tryKeyLength(enc, 5, 'e');
        print("Encrypted");
        print(enc);
        print("Key");
        print(vb.translateKey(key));
    }
}
