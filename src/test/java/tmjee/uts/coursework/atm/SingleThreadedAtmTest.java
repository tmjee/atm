package tmjee.uts.coursework.atm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tmjee
 */
public class SingleThreadedAtmTest extends AbstractAtmTest {

    @Override
    protected void preBuildOfAtmBuilder(Atm.Builder builder) {
        builder.singleThreadCapable();
    }
}
