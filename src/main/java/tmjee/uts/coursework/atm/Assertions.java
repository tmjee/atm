package tmjee.uts.coursework.atm;

import static java.lang.String.format;

/**
 * @author tmjee
 */
public class Assertions {

    public static void assertArgsNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(format("argument %s cannot be null", o));
        }
    }
}
