import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    // mapping of language names to language dictionary
    private HashMap<String, HashSet<String>> dicts;
    
    // mapping of most common characters to language
    private HashMap<String, Character> commonChars;
    
    public VigenereBreaker() {
        dicts = new HashMap<String, HashSet<String>>();
        commonChars = new HashMap<String, Character>();
        
	// read all dictionaries
	readDict("Danish");
	readDict("Dutch");
	readDict("English");
	readDict("French");
	readDict("German");
	readDict("Italian");
	readDict("Portuguese");
	readDict("Italian");
	readDict("Spanish");
	
	// find most common characters in dictionaries
	for (String langName : dicts.keySet()) {
		commonChars.put(langName, mostCommonCharIn(dicts.get(langName)));
	}
		
    }

        	private void readDict(String lang) {
		String path = "dictionaries/"+lang;
		dicts.put(lang, readDictionary(new FileResource(path)));
		
	}
	
        private <T> void print(T t) {
            System.out.println(t);
        }

    /**
     * This method clices string and returns one slice for further prcessing
     */
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sb = new StringBuilder();
        char[] ca = message.toCharArray();
        for (int i=whichSlice;i<ca.length;i+=totalSlices) sb.append(ca[i]);
        return sb.toString();
    }

    /**
     * Method for extracting key from encrypted input
     */
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        
        for (int i=0;i<klength;i++){
            String slice = sliceString(encrypted, i, klength);
            key[i] = cc.getKey(slice);
        }
        return key;
    }

    /**
     * Translates integer array key to string representation.
     */
    public String translateKey(int[] key) {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        
        for (int i=0;i<key.length;i++) sb.append(alphabet.charAt(key[i]));
        
        return sb.toString();
    }
    
        /**
     * Method for reading dictionary from a file
     */
    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> set = new HashSet<String>();
        
        for (String word : fr.words()) set.add(word.toLowerCase());
        
        return set;
    }
    
    /**
     * Count how many words in a message are from a dictionary
     */
    public int countWords(String message, HashSet<String> dict) {
        int count = 0;
        message = message.toLowerCase();
        
        String[] words = message.split("\\W");
        for (String word : words ) if (dict.contains(word)) count++;
        return count;
    }
    
    /**
     * Decrypt cipher for keys in range 1-100, based on dictionary provided
     */
    public String breakForLanguage(String encrypted, String langName) {
        HashMap<int[], Integer> keys = new HashMap<int[], Integer>();
        HashSet<String> dict = dicts.get(langName);
        char commonChar = commonChars.get(langName);
        
        for (int i=1; i<100; i++) {
            int[] key = tryKeyLength(encrypted, i, commonChar);
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            int cnt = countWords(decrypted, dict);
            keys.put(key, cnt);
        }
        
        int maxCount = 0;
        int[] foundKey = null;
        
        for (int[] key : keys.keySet() ) {
            if (maxCount < keys.get(key)) {
                maxCount = keys.get(key);
                foundKey = key;
            }
        }
        
        print("Language: ");
        print(langName);
        print("Key length: ");
        print(foundKey.length);
        print("Key:" + translateKey(foundKey));
        //for(int i=0; i<foundKey.length; i++) print(foundKey[i]);
        
        VigenereCipher vc = new VigenereCipher(foundKey);
        print("Decrypted word count:");
        print(countWords(vc.decrypt(encrypted), dict));
        
        return vc.decrypt(encrypted);
    }

    /**
     * This method should find out which character, of the letters in the English alphabet,
     * appears most often in the words in a dictionary.
     */
    public char mostCommonCharIn(HashSet<String> dict){
        HashMap<Character, Integer> charCounts = new HashMap<Character, Integer>();
        
        for (String word: dict) {
            for (char c: word.toLowerCase().toCharArray()) {
                if (!charCounts.containsKey(c)) charCounts.put(c,1);
                else charCounts.put(c, charCounts.get(c)+1);
            }
        }
        
        int maxFreq=0;
        char mostCommon = 'a';
        
        for (char c: charCounts.keySet()) {
            if (charCounts.get(c) > maxFreq) {
                maxFreq = charCounts.get(c);
                mostCommon = c;
            }
        }
        return mostCommon;
    }
    
    public void breakForAllLangs(String encrypted) {
        int currentHigh = 0;
        String decryptedMessage = "";
        String usedLanguage = "";
        
        for (String langName : dicts.keySet()) {
            String dec = breakForLanguage(encrypted, langName);
            int currentWordCount = countWords(encrypted, dicts.get(langName));
            if (currentWordCount > currentHigh) {
                decryptedMessage = dec;
                currentHigh = currentWordCount;
                usedLanguage = langName;
            }
            print("Language: " + langName + ", currenHigh: " + currentHigh);
        }

        print(decryptedMessage);
        print("Language "+usedLanguage);
    }
    
    
    /**
     * Decrypt and print selected file
    
     * Assignment 1:
    public void breakVigenere() {
       FileResource fr = new FileResource();
       String s = fr.asString();
       int[] key = tryKeyLength(s, 4, 'e');
       VigenereCipher vc = new VigenereCipher(key);
       String msg = vc.decrypt(s);
       print(msg);
       print("Key:" + translateKey(key));
       for(int i=0; i<key.length; i++) print(key[i]+1);
    } 
    
     * Assignment 2:
    public void breakVigenere () {
       String input = new FileResource().asString();
       String langName = "English";
       HashSet<String> dict = dicts.get(langName);
       String dec = breakForLanguage(input, langName);
       print(dec);
       print("\nTrying with key length 38");
       int[] foundKey = tryKeyLength(input, 38, 'e');
       VigenereCipher vc = new VigenereCipher(foundKey);
       print("Decrypted word count:");
       print(countWords(vc.decrypt(input), dict));
    } 
    
    *  Final Assignment:
    */
    public void breakVigenere() {
       String input = new FileResource().asString();
       breakForAllLangs(input);
    }
    
    
    
}
