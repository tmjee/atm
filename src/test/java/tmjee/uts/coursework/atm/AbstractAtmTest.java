package tmjee.uts.coursework.atm;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by tmjee on 5/4/14.
 */
public abstract class AbstractAtmTest {

    protected abstract void preBuildOfAtmBuilder(Atm.Builder builder);

    @Test
    public void test_5() throws Exception {
        /**
         * start with
         *  $10 x 10
         *  $20 x 8
         *  $50 x 1
         */
        Atm.Builder builder = new Atm.Builder();
        builder.addNotes(Note.$10).withQuantityOf(10);
        builder.addNotes(Note.$20).withQuantityOf(8);
        builder.addNotes(Note.$50).withQuantityOf(1);
        preBuildOfAtmBuilder(builder);
        Atm atm = builder.build();

        /**
         * Withdrawal1 $100 (success)
         *  $10 x 5
         *  $20 x 8
         *  $50 x 0
         */
        Withdrawal withdrawal1 = atm.withdraw(100);
        System.out.println(withdrawal1);
        assertTrue(withdrawal1.isSuccessful());
        assertEquals(withdrawal1.sumWithdrawed(), 100);
        assertEquals(withdrawal1.outstanding(), 0);
        Summary summary1 = atm.summary();
        assertEquals(summary1.notesQuantity(Note.$10), 5);
        assertEquals(summary1.notesQuantity(Note.$20), 8);
        assertEquals(summary1.notesQuantity(Note.$50), 0);


        /**
         * Withdrawal2 $100 (success)
         *  $10 x 5
         *  $20 x 3
         *  $50 x 0
         */
        Withdrawal withdrawal2 = atm.withdraw(100);
        System.out.println(withdrawal2);
        assertTrue(withdrawal2.isSuccessful());
        assertEquals(withdrawal2.sumWithdrawed(), 100);
        assertEquals(withdrawal2.outstanding(), 0);
        Summary summary2 = atm.summary();
        assertEquals(summary2.notesQuantity(Note.$10), 5);
        assertEquals(summary2.notesQuantity(Note.$20), 3);
        assertEquals(summary2.notesQuantity(Note.$50), 0);


        /**
         * Withdrawal3 $100 (success)
         *  $10 x 1
         *  $20 x 0
         *  $50 x 0
         */
        Withdrawal withdrawal3 = atm.withdraw(100);
        assertTrue(withdrawal3.isSuccessful());
        assertEquals(withdrawal3.sumWithdrawed(), 100);
        assertEquals(withdrawal3.outstanding(), 0);
        Summary summary3 = atm.summary();
        assertEquals(summary3.notesQuantity(Note.$10), 1);
        assertEquals(summary3.notesQuantity(Note.$20), 0);
        assertEquals(summary3.notesQuantity(Note.$50), 0);


        /**
         * Withdrawal4 $100 (fail)
         *  $10 x 1
         *  $20 x 0
         *  $50 x 0
         */
        Withdrawal withdrawal4 = atm.withdraw(100);
        assertFalse(withdrawal4.isSuccessful());
        assertEquals(withdrawal4.sumWithdrawed(), 100);
        assertEquals(withdrawal4.outstanding(), 90);
        Summary summary4 = atm.summary();
        assertEquals(summary4.notesQuantity(Note.$10), 1);
        assertEquals(summary4.notesQuantity(Note.$20), 0);
        assertEquals(summary4.notesQuantity(Note.$50), 0);
    }

    @Test
    public void test_4() throws Exception {
        /**
         * start with
         *  $10 x 10
         *  $20 x 8
         *  $50 x 5
         */
         Atm.Builder builder = new Atm.Builder();
        builder.addNotes(Note.$10).withQuantityOf(10);
        builder.addNotes(Note.$20).withQuantityOf(8);
        builder.addNotes(Note.$50).withQuantityOf(5);
        preBuildOfAtmBuilder(builder);
        Atm atm = builder.build();

        /**
         * After withdrawal1
         *  $10 x 10
         *  $20 x 8
         *  $50 x 5
         */
        Withdrawal withdrawal1 = atm.withdraw(100);
        System.out.println(withdrawal1);
        assertTrue(withdrawal1.isSuccessful());
        assertEquals(withdrawal1.sumWithdrawed(), 100);
        assertEquals(withdrawal1.outstanding(), 0);
        Summary summary1 = atm.summary();
        assertEquals(summary1.notesQuantity(Note.$10), 10);
        assertEquals(summary1.notesQuantity(Note.$20), 8);
        assertEquals(summary1.notesQuantity(Note.$50), 3);

    }

    @Test
    public void test_3() throws Exception {
        /**
         * Start off with
         *  $20 x 10
         *  $50 x 5
         */
        Atm.Builder builder = new Atm.Builder();
        builder.addNotes(Note.$20).withQuantityOf(10);
        builder.addNotes(Note.$50).withQuantityOf(5);
        preBuildOfAtmBuilder(builder);
        Atm atm = builder.build();

        /**
         * After withdrawal1 $100
         *  $20 x 10
         *  $50 x 3
         */
        Withdrawal withdrawal1 = atm.withdraw(100);
        System.out.println(withdrawal1);
        assertTrue(withdrawal1.isSuccessful());
        assertEquals(withdrawal1.sumWithdrawed(), 100);
        assertEquals(withdrawal1.outstanding(), 0);
        Summary summary1 = atm.summary();
        assertEquals(summary1.notesQuantity(Note.$20), 10);
        assertEquals(summary1.notesQuantity(Note.$50), 3);
    }

    @Test
    public void test_2() throws Exception {
        Atm.Builder builder = new Atm.Builder();
        builder.addNotes(Note.$20).withQuantityOf(10);
        builder.addNotes(Note.$50).withQuantityOf(5);
        preBuildOfAtmBuilder(builder);
        Atm atm = builder.build();

        /**
         * After withdrawal1 $60
         *  $20 x 7
         *  $50 x 5
         */
        Withdrawal withdrawal1 = atm.withdraw(60);
        System.out.println(withdrawal1);
        assertTrue(withdrawal1.isSuccessful());
        assertEquals(withdrawal1.sumWithdrawed(), 60);
        assertEquals(withdrawal1.outstanding(), 0);
        Summary summary1 = atm.summary();
        assertEquals(summary1.notesQuantity(Note.$20), 7);
        assertEquals(summary1.notesQuantity(Note.$50), 5);

        /**
         * After withdrawal2 $60
         *  $20 x 4
         *  $50 x 5
         */
        Withdrawal withdrawal2 = atm.withdraw(60);
        assertTrue(withdrawal2.isSuccessful());
        assertEquals(withdrawal2.sumWithdrawed(), 60);
        assertEquals(withdrawal2.outstanding(), 0);
        Summary summary2 = atm.summary();
        assertEquals(summary2.notesQuantity(Note.$20), 4);
        assertEquals(summary2.notesQuantity(Note.$50), 5);
    }



    @Test
    public void test_1() throws Exception {
        /**
         * Initially
         *  $20 x 2
         *  $50 x 5
         */
        Atm.Builder builder = new Atm.Builder();
        builder.addNotes(Note.$20).withQuantityOf(2);
        builder.addNotes(Note.$50).withQuantityOf(5);
        preBuildOfAtmBuilder(builder);
        Atm atm = builder.build();

        /**
         * After withdrawal1 $10 (failed)
         *  $20 x 2
         *  $50 x 5
         */
        Withdrawal withdrawal1 = atm.withdraw(10);
        assertFalse(withdrawal1.isSuccessful());
        assertEquals(withdrawal1.sumWithdrawed(), 10);
        assertEquals(withdrawal1.outstanding(), 10);
        Summary summary1 = atm.summary();
        assertEquals(summary1.notesQuantity(Note.$20), 2);
        assertEquals(summary1.notesQuantity(Note.$50), 5);

        /**
         * After withdrawal2 $50 (success)
         *  $20 x 2
         *  $50 x 4
         */
        Withdrawal withdrawal2 = atm.withdraw(50);
        assertTrue(withdrawal2.isSuccessful());
        assertEquals(withdrawal2.sumWithdrawed(), 50);
        assertEquals(withdrawal2.outstanding(), 0);
        Summary summary2 = atm.summary();
        assertEquals(summary2.notesQuantity(Note.$20), 2);
        assertEquals(summary2.notesQuantity(Note.$50), 4);

        /**
         * After withdrawal3 $20 (success)
         *  $20 x 1
         *  $50 x 4
         */
        Withdrawal withdrawal3 = atm.withdraw(20);
        assertTrue(withdrawal3.isSuccessful());
        assertEquals(withdrawal3.sumWithdrawed(), 20);
        assertEquals(withdrawal3.outstanding(), 0);
        Summary summary3 = atm.summary();
        assertEquals(summary3.notesQuantity(Note.$20), 1);
        assertEquals(summary3.notesQuantity(Note.$50), 4);

        /**
         * After withdrawal4 $60 (failed)
         *  $20 x 1
         *  $50 x 4
         */
        Withdrawal withdrawal4 = atm.withdraw(60);
        assertFalse(withdrawal4.isSuccessful());
        assertEquals(withdrawal4.sumWithdrawed(), 60);
        assertEquals(withdrawal4.outstanding(), 40);
        Summary summary4 = atm.summary();
        assertEquals(summary4.notesQuantity(Note.$20), 1);
        assertEquals(summary4.notesQuantity(Note.$50), 4);

        /**
         * After withdrawal5 $70 (success)
         *  $20 x 0
         *  $50 x 3
         */
        Withdrawal withdrawal5 = atm.withdraw(70);
        assertTrue(withdrawal5.isSuccessful());
        assertEquals(withdrawal5.sumWithdrawed(), 70);
        assertEquals(withdrawal5.outstanding(), 0);
        Summary summary5 = atm.summary();
        assertEquals(summary5.notesQuantity(Note.$20), 0);
        assertEquals(summary5.notesQuantity(Note.$50), 3);


        /**
         * After withdrawal6 $20 (failed)
         *   $20 x 0
         *   $50 x 3
         */
        Withdrawal withdrawal6 = atm.withdraw(20);
        assertFalse(withdrawal6.isSuccessful());
        assertEquals(withdrawal6.sumWithdrawed(), 20);
        assertEquals(withdrawal6.outstanding(), 20);
        Summary summary6 = atm.summary();
        assertEquals(summary6.notesQuantity(Note.$20), 0);
        assertEquals(summary6.notesQuantity(Note.$50), 3);

        /**
         * After withdrawal7 $100 (success)
         *  $20 x 0
         *  $30 x 1
         */
        Withdrawal withdrawal7 = atm.withdraw(100);
        assertTrue(withdrawal7.isSuccessful());
        assertEquals(withdrawal7.sumWithdrawed(), 100);
        assertEquals(withdrawal7.outstanding(), 0);
        Summary summary7 = atm.summary();
        assertEquals(summary7.notesQuantity(Note.$20), 0);
        assertEquals(summary7.notesQuantity(Note.$50), 1);

        /**
         * After withdrawal8 $150 (fail)
         *  $20 x 0
         *  $50 x 1
         */
        Withdrawal withdrawal8 = atm.withdraw(150);
        assertFalse(withdrawal8.isSuccessful());
        assertEquals(withdrawal8.sumWithdrawed(), 150);
        assertEquals(withdrawal8.outstanding(), 100);
        Summary summary8 = atm.summary();
        assertEquals(summary8.notesQuantity(Note.$20), 0);
        assertEquals(summary8.notesQuantity(Note.$50), 1);

        /**
         * After withdrawal9 $50 (success)
         *   $20 x 0
         *   $50 x 0
         */
        Withdrawal withdrawal9 = atm.withdraw(50);
        assertTrue(withdrawal9.isSuccessful());
        assertEquals(withdrawal9.sumWithdrawed(), 50);
        assertEquals(withdrawal9.outstanding(), 0);
        Summary summary9 = atm.summary();
        assertEquals(summary9.notesQuantity(Note.$20), 0);
        assertEquals(summary9.notesQuantity(Note.$50), 0);


        /**
         * After withdrawal10 $150 (fail)
         *  $20 x 0
         *  $50 x 0
         */
        Withdrawal withdrawal10 = atm.withdraw(150);
        assertFalse(withdrawal10.isSuccessful());
        assertEquals(withdrawal10.sumWithdrawed(), 150);
        assertEquals(withdrawal10.outstanding(), 150);
        Summary summary10 = atm.summary();
        assertEquals(summary10.notesQuantity(Note.$20), 0);
        assertEquals(summary10.notesQuantity(Note.$50), 0);
    }
}
