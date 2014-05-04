package tmjee.uts.coursework.atm;

import java.math.BigInteger;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.StampedLock;

import static tmjee.uts.coursework.atm.Assertions.*;

/**
 * @author tmjee
 */
abstract class Atomicity {

    /**
     * For internal use only, not to be visible outside {@link Atomicity}
     * class itself, function as a convenient class that could make a
     * copy of itself.
     *
     * @author tmjee
     */
    private static class NotesKeeper {

        private Map<Note, BigInteger> notesQuantities;
        private NotesKeeper(){}

        static NotesKeeper newCopy(Map<Note, BigInteger> notesQuantities) {
            assertArgsNotNull(notesQuantities);
            NotesKeeper notesKeeper = new NotesKeeper();
            notesKeeper.notesQuantities = new HashMap<>(notesQuantities);
            return notesKeeper;
        }

        NotesKeeper copyMyself() {
            return NotesKeeper.newCopy(this.notesQuantities);
        }
    }

    Summary internalSummary() {
        return new Summary(get().copyMyself().notesQuantities);
    }

    Withdrawal internalWithdraw(int sum) {
        if (sum <= 0) {
            throw new IllegalArgumentException("sum must be > 0");
        }

        Withdrawal.Builder builder = null;
        List<Iterator<Note>> notesPermutationsList =  Note.permutations();
        for(Iterator<Note> currentNotesPermutation : notesPermutationsList) {
            int efSum = sum;
            NotesKeeper copyOfNotesKeeper = get().copyMyself();
            builder = new Withdrawal.Builder();
            builder.setSum(sum);

            NEXT_NOTE:
            while (currentNotesPermutation.hasNext()) {
                Note currentNote = currentNotesPermutation.next();
                while (efSum > 0) {
                    BigInteger quantities = copyOfNotesKeeper.notesQuantities.get(currentNote);
                    if (quantities == null) {
                        continue NEXT_NOTE;
                    }
                    while (true) {
                        if (efSum >= currentNote.amount() && quantities.compareTo(BigInteger.ZERO) == 1) {
                            efSum = efSum - currentNote.amount();
                            quantities = quantities.subtract(BigInteger.ONE);
                            copyOfNotesKeeper.notesQuantities.put(currentNote, quantities);
                            builder.add(currentNote);
                        } else {
                            continue NEXT_NOTE;
                        }
                    }
                }
            }
            builder.setOutstanding(efSum);
            if (efSum == 0) {
                set(copyOfNotesKeeper);
                return builder.build();
            }
            else {
                builder.markAsFailed();
            }
        }
        return builder.build();
    }

    abstract void set(NotesKeeper notesKeeper);
    abstract NotesKeeper get();
    abstract Withdrawal withdraw(int sum);
    abstract Summary summary();


    static Atomicity newSingleThreadedAtomicity(final Map<Note, BigInteger> n) {
        return new Atomicity(){
            private NotesKeeper notesKeeper = NotesKeeper.newCopy(n);

            @Override
            void set(NotesKeeper notesKeeper) {
                this.notesKeeper = notesKeeper;
            }

            @Override
            NotesKeeper get() {
                return notesKeeper;
            }

            @Override
            Withdrawal withdraw(int sum) {
                return internalWithdraw(sum);
            }

            @Override
            Summary summary() {
                return internalSummary();
            }
        };
    }

    static Atomicity newMultiThreadedAtomicity(final Map<Note, BigInteger> n) {
        return new Atomicity(){
            private AtomicReference<NotesKeeper> notesKeeperRef = new AtomicReference<>(NotesKeeper.newCopy(n));
            private StampedLock lock = new StampedLock();

            @Override
            void set(NotesKeeper notesKeeper) {
                notesKeeperRef.set(notesKeeper);
            }

            @Override
            NotesKeeper get() {
                return notesKeeperRef.get();
            }

            @Override
            Withdrawal withdraw(int sum) {
                long stamp = lock.writeLock();
                try {
                   return internalWithdraw(sum);
                }
                finally {
                    lock.unlockWrite(stamp);
                }
            }

            @Override
            Summary summary() {
                long stamp = lock.tryOptimisticRead();
                Summary summary = internalSummary();
                if (!lock.validate(stamp)) {
                    stamp = lock.readLock();
                    try {
                        summary = internalSummary();
                    } finally {
                        lock.unlockRead(stamp);
                    }
                }
                return summary;
            }
        };
    }
}
