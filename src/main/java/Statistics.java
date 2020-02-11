import java.time.LocalTime;
import java.util.Locale;

public class Statistics {
    private boolean refusing;
    private LocalTime start;
    private LocalTime end;
    private Long successes;
    private Long fails;
    private Float minAvail;

    Statistics(Float minAvail) {
        this.minAvail = minAvail;
        refusing = false;
        successes = fails = 0L;
    }

    public void process(LocalTime time, boolean isFailure) {
        if (isFailure) {
            fails++;
        } else {
            successes++;
            if (refusing && calcAvail(1) >= minAvail) {
                refusing = false;
                System.out.println(this);
            }
        }

        if (refusing) {
            end = time;
        }
        if (calcAvail(0) < minAvail && !refusing) {
            refusing = true;
            end = start = time;
        }
    }

    public boolean isRefusing() {
        return refusing;
    }

    float calcAvail(int n) {
        return 100 * (float) (successes + n) / (successes + fails + n);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s\t%s\t%.2f",
                             start, end, calcAvail(0));
    }

}
