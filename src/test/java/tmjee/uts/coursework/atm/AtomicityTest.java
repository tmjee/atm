package tmjee.uts.coursework.atm;

import org.junit.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author tmjee
 */
public class AtomicityTest {

    @Test
    public void test_1() throws Exception {
        /**
         * Start ith
         *  - $20 x 10
         *  - $50 x 5
         */
        Map<Note, BigInteger> map = new HashMap<>();
        map.put(Note.$20, BigInteger.valueOf(10));
        map.put(Note.$50, BigInteger.valueOf(5));
        Atomicity singleThreadedAtomicity  = Atomicity.newSingleThreadedAtomicity(map);

        /**
         * After withdrawal1 $60 (success)
         *  - $20 x 7
         *  - $50 x 5
         */
        Withdrawal withdrawal1 = singleThreadedAtomicity.withdraw(60);
        System.out.println(withdrawal1);
        assertTrue(withdrawal1.isSuccessful());
        assertEquals(withdrawal1.sumWithdrawed(), 60);
        assertEquals(withdrawal1.outstanding(), 0);
        Summary summary1 = singleThreadedAtomicity.summary();
        assertEquals(summary1.notesQuantity(Note.$20), 7);
        assertEquals(summary1.notesQuantity(Note.$50), 5);
    }

}
