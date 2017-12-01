package julia.utils;

import java.util.Calendar;

public final class DigitTimeRandom {

    public static int randomMinute(int offset) {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.MINUTE);
        return hour + offset;
    }

    private DigitTimeRandom() {
    }

}
