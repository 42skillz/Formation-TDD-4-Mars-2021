import org.approvaltests.Approvals;
import org.junit.Test;


public class FooBarQixWriterShould {
    @Test
    public void write_from_1_to_10_when_call_FooBarQix_from_1_to_10() {
        Approvals.verify(new FooBarQixWriter().Write(1, 10));
    }
    @Test
    public void write_from_11_to_20_when_call_FooBarQix_from_11_to_20() {
        Approvals.verify(new FooBarQixWriter().Write(11, 20));
    }

    @Test
    public void write_from_21_to_30_when_call_FooBarQix_from_21_to_30() {
        Approvals.verify(new FooBarQixWriter().Write(21, 30));
    }

    @Test
    public void write_from_31_to_40_when_call_FooBarQix_from_31_to_40() {
        Approvals.verify(new FooBarQixWriter().Write(31, 40));
    }

    @Test
    public void write_from_41_to_50_when_call_FooBarQix_from_41_to_50() {
        Approvals.verify(new FooBarQixWriter().Write(41, 50));
    }

    @Test
    public void write_from_51_to_60_when_call_FooBarQix_from_51_to_60() {
        Approvals.verify(new FooBarQixWriter().Write(51, 60));
    }

    @Test
    public void write_from_61_to_70_when_call_FooBarQix_from_61_to_70() {
        Approvals.verify(new FooBarQixWriter().Write(61, 70));
    }

    @Test
    public void write_from_71_to_80_when_call_FooBarQix_from_71_to_80() {
        Approvals.verify(new FooBarQixWriter().Write(71, 80));
    }

    @Test
    public void write_from_81_to_90_when_call_FooBarQix_from_81_to_90() {
        Approvals.verify(new FooBarQixWriter().Write(81, 90));
    }

    @Test
    public void write_from_91_to_100_when_call_FooBarQix_from_91_to_100() {
        Approvals.verify(new FooBarQixWriter().Write(91, 100));
    }

    @Test
    public void write_from_1_to_100_when_call_FooBarQix_from_1_to_100() {
        Approvals.verify(new FooBarQixWriter().Write(1, 100));
    }
}
