package tmjee.uts.coursework.atm;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author tmjee
 */
public class NoteTest {

    @Test
    public void test_permutations() throws Exception {
        List<Iterator<Note>> permutationsList = Note.permutations();

        assertEquals(permutationsList.size(), 3);
        assertEquals(iteratorAsString(permutationsList.get(0)), "$50$10$20");
        assertEquals(iteratorAsString(permutationsList.get(1)), "$20$50$10");
        assertEquals(iteratorAsString(permutationsList.get(2)), "$10$20$50");
    }


    private <E> String iteratorAsString(Iterator<E> e) {
        StringBuilder b = new StringBuilder();
        while(e.hasNext()) {
            b.append(e.next().toString());
        }
        return b.toString();
    }
}
