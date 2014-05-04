package tmjee.uts.coursework.atm;

/**
 * Created by tmjee on 5/4/14.
 */
public class MultiThreadedAtmTest extends AbstractAtmTest {

    @Override
    protected void preBuildOfAtmBuilder(Atm.Builder builder) {
        builder.multiThreadCapable();
    }
}
