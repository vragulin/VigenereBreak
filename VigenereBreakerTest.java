
/**
 * Write a description of VigenereBreakerTest here.
 * 
 * @author V. Ragulin
 * @version (a version number or a date)
 */
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import edu.duke.*;

public class VigenereBreakerTest {

        private VigenereBreaker vb = new VigenereBreaker();
        
        @Test
        public void testSliceString() {
            assertEquals(vb.sliceString("abcdefghijklm", 0, 3), "adgjm");
            assertEquals(vb.sliceString("abcdefghijklm", 1, 3), "behk");
            assertEquals(vb.sliceString("abcdefghijklm", 2, 3), "cfil");
            
            assertEquals(vb.sliceString("abcdefghijklm", 0, 4), "aeim");
            assertEquals(vb.sliceString("abcdefghijklm", 1, 4), "bfj");
            assertEquals(vb.sliceString("abcdefghijklm", 2, 4), "cgk");
            assertEquals(vb.sliceString("abcdefghijklm", 3, 4), "dhl");
            
            assertEquals(vb.sliceString("abcdefghijklm", 0, 5), "afk");
            assertEquals(vb.sliceString("abcdefghijklm", 1, 5), "bgl");
            assertEquals(vb.sliceString("abcdefghijklm", 2, 5), "chm");
            assertEquals(vb.sliceString("abcdefghijklm", 3, 5), "di");
            assertEquals(vb.sliceString("abcdefghijklm", 4, 5), "ej");
        }
        
        /**
        private assertMostCommonCharIn(String path, char mostCommonChar){
            HashSet<String> dict = vb.readDictionary
        }*/
}
