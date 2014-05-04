package tmjee.uts.coursework.atm;

import java.math.BigInteger;
import java.util.*;

import static tmjee.uts.coursework.atm.Assertions.*;

/**
 * @author tmjee
 */
public class Atm {

    public static class Builder {
        private Map<Note, BigInteger> notesQuantities = new HashMap<>();
        private boolean multiThreadCapable = false;

        public static class NotesBuilder {
            private Builder parentBuilder;
            private Note note;

            private NotesBuilder(Builder parentBuilder, Note note) {
                this.parentBuilder = parentBuilder;
                this.note = note;
            }

            public Builder withQuantityOf(int quantity) {
                parentBuilder.notesQuantities.compute(note,
                        (n, bi) ->  bi == null ? BigInteger.valueOf(quantity) : bi.add(BigInteger.valueOf(quantity)));
                return parentBuilder;
            }
        }

        public NotesBuilder addNotes(Note note) {
            assertArgsNotNull(note);
            notesQuantities.putIfAbsent(note, BigInteger.valueOf(0));
            return new NotesBuilder(this, note);
        }

        public Builder multiThreadCapable() {
            multiThreadCapable = true;
            return this;
        }

        public Builder singleThreadCapable() {
            multiThreadCapable = false;
            return this;
        }

        public Atm build() {
            return new Atm(
                    multiThreadCapable ?
                            Atomicity.newMultiThreadedAtomicity(notesQuantities) :
                            Atomicity.newSingleThreadedAtomicity(notesQuantities));
        }
    }


    private Atomicity atomicity;

    private Atm(Atomicity atomicity) {
        this.atomicity = atomicity;
    }

    public Withdrawal withdraw(int sum) {
        return atomicity.withdraw(sum);
    }

    public Summary summary() {
        return atomicity.summary();
    }
}
