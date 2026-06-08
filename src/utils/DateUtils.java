package utils;

import java.time.Instant;
import java.util.Date;

public class DateUtils {
    public static String converterParaString(Date date) {
        if(date == null) return null;
        return date.toInstant().toString();
    }

    public static Date converterDeString(String string) {
        if(string == null) return null;
        return Date.from(Instant.parse(string));
    }
}
