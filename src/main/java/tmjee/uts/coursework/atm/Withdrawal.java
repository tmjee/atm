package tmjee.uts.coursework.atm;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author tmjee
 */
public class Withdrawal {

    private Map<Note, BigInteger> retrieval;
    private int total, outstanding;
    private boolean success;

    Withdrawal(Map<Note, BigInteger> retrieval, int total, int outstanding, boolean success) {
        this.retrieval = new HashMap<>(retrieval);
        this.total = total;
        this.success = success;
        this.outstanding = outstanding;
    }

    public boolean isSuccessful() { return success; }
    public int sumWithdrawed() { return total; }
    public int outstanding() { return outstanding; }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("Attempting to retrieve %s \nRetrieval outome %s\nNotes ");
        if (!retrieval.isEmpty()) {
            for (Map.Entry<Note, BigInteger> n : retrieval.entrySet()) {
                msg.append("\n ").append(n.getKey()).append(" x ").append(n.getValue().intValue());
            }
        } else {
            msg.append("\n  -- none -- ");
        }
        msg.append("\nOutstanding %s");
        return format(msg.toString(), total, success ? "success" : "failed",
                //total - retrieval.entrySet().stream().mapToInt(e->(e.getKey().amount() * e.getValue().intValue())).sum());
                outstanding);
    }

    static class Builder {
        private Map<Note, BigInteger> retrieval = new HashMap<Note, BigInteger>();
        private boolean success = true;
        private int total = 0;
        private int outstanding = 0;
        Builder add(Note note) {
            retrieval.compute(note, (n,bi)->bi == null ? BigInteger.ONE : bi.add(BigInteger.ONE));
            return this;
        }
        Builder setSum(int total) {
            this.total = total;
            return this;
        }
        Builder setOutstanding(int outstanding) {
            this.outstanding = outstanding;
            return this;
        }
        Builder markAsFailed() {
            success = false;
            return this;
        }
        Withdrawal build() {
            return new Withdrawal(retrieval, total, outstanding, success);
        }
    }
}
