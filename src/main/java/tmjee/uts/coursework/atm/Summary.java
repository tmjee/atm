package tmjee.uts.coursework.atm;

import java.math.BigInteger;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author tmjee
 */
public class Summary {
    private Map<Note, BigInteger> notesQuantities;

    Summary(Map<Note, BigInteger> notesQuantities){
        this.notesQuantities = notesQuantities;
    }

    public int notesQuantity(Note note) {
        return notesQuantities.getOrDefault(note, BigInteger.ZERO).intValue();
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("Notes currently in atm");
        for(Map.Entry<Note, BigInteger> e : notesQuantities.entrySet()) {
            msg.append(format("%n %s x %s", e.getKey(), e.getValue()));
        }
        return msg.toString();
    }
}
