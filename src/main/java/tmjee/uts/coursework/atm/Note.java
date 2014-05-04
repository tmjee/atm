package tmjee.uts.coursework.atm;

import java.util.*;

/**
 * @author tmjee
 */
public enum Note {
    $10() {
        @Override
        public int amount() {
            return 10;
        }
    },
    $20(){
        @Override
        public int amount() {
            return 20;
        }
    },
    $50(){
        @Override
        public int amount() {
            return 50;
        }
    };

    public abstract int amount();


    public static List<Iterator<Note>> permutations() {
        List<Iterator<Note>> permutationsList = new ArrayList<>();
        Note[] notes = Arrays.copyOf(Note.values(), Note.values().length);
        for (int a=0; a< notes.length; a++) {

            Note[] t = new Note[notes.length];
            for (int b=0; b<t.length;b++) {
                t[(b+1)%t.length] = notes[b];
            }
            notes = t;
            permutationsList.add(new PermutationIterator(t));
        }
        return permutationsList;
    }


    public static class PermutationIterator implements Iterator<Note> {

        private Note[] notes;
        private int count = 0;

        private PermutationIterator(Note[] notes) {
            this.notes = Arrays.copyOf(notes, notes.length);
        }

        @Override
        public boolean hasNext() {
            return (count < notes.length);
        }

        @Override
        public Note next() {
            if (hasNext()) {
                return notes[count++];
            }
            throw new NoSuchElementException("no such element");
        }
    }
}
