/**
 * Time64.java
 *
 * A class to represent time in a variety of precisions with 64-bit integer
 *
 * @author Yarovitsin
 * @version 0.5.020201
 * @see <a href="github.com/Yarovitsin/Time64.java">Github repository</a>
 */
public class Time64 {
    private static final long UNIX_EPOCH_OFFSET = 946080000;
    long time;
    //subclass for precision
    byte precision;
    private static final int[] monthLengths = {31,28,31,30,31,30,31,31,30,31,30,31};
    //enum precision {s, ms, μs, ns}
    /**
     * @param precision String representation of precision
     * @return byte representation of precision
     */
    private byte precisionStringToByte(String precision){
        return switch (precision) {
            case "s", "seconds" -> 0;
            case "ms", "milliseconds" -> 1;
            case "us", "μs", "microseconds" -> 2;
            case "ns", "nanoseconds" -> 3;
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        };
    }

    /**
     * @param time Time in seconds since Jan 1 2000
     *
     */
    public Time64(long time) {
        this.time = time;
        this.precision = 0;
    }

    /**
     * @param time Time in specified precision since Jan 1 2000
     * @param precision String representation of precision
     */
    public Time64(long time, String precision) {
        this.time = time;
        this.precision = precisionStringToByte(precision);
    }

    /**
     * @param time Time in specified precision since Jan 1 2000
     * @param precision Byte representation of precision
     */
    public Time64(long time, byte precision) {
        this.time = time;
        this.precision = precision;
    }
    private long seconds() {
        switch (this.precision) {
            //return seconds since Jan 1 2000
            case 0 -> {
                return this.time;
            }
            case 1 -> {
                return this.time / 1000;
            }
            case 2 -> {
                return this.time / 1000000;
            }
            case 3 -> {
                return this.time / 1000000000;
            }
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        }
    }
    private long milliseconds() {
        switch (this.precision) {
            case 0 -> {
                return this.time * 1000;
            }
            case 1 -> {
                return this.time;
            }
            case 2 -> {
                return this.time / 1000;
            }
            case 3 -> {
                return this.time / 1000000;
            }
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        }
    }
    private long microseconds() {
        switch (this.precision) {
            case 0 -> {
                return this.time * 1000000;
            }
            case 1 -> {
                return this.time * 1000;
            }
            case 2 -> {
                return this.time;
            }
            case 3 -> {
                return this.time / 1000;
            }
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        }
    }
    private long nanoseconds() {
        switch (this.precision) {
            case 0 -> {
                return this.time * 1000000000;
            }
            case 1 -> {
                return this.time * 1000000;
            }
            case 2 -> {
                return this.time * 1000;
            }
            case 3 -> {
                return this.time;
            }
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        }
    }

    /**
     * Method that returns the time in the specified precision
     * @param precision String representation of precision
     * @return Time64 object with new precision
     * @throws IllegalArgumentException if precision is not one of: s, ms, μs, ns
     *
     */
    public Time64 changePrecision(String precision){
        switch (precisionStringToByte(precision)) {
            case 0 -> this.time = this.seconds();
            case 1 -> this.time = this.milliseconds();
            case 2 -> this.time = this.microseconds();
            case 3 -> this.time = this.nanoseconds();
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        }
        this.precision = precisionStringToByte(precision);
        return this;
    }

    /**
     * Method that returns the time in the specified precision
     * @param precision byte representation of precision
     * @return Time64 object with new precision
     * @throws IllegalArgumentException if precision is not one of: s, ms, μs, ns
     */
    public Time64 changePrecision(byte precision){
        switch (precision) {
            case 0 -> this.time = this.seconds();
            case 1 -> this.time = this.milliseconds();
            case 2 -> this.time = this.microseconds();
            case 3 -> this.time = this.nanoseconds();
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        }
        this.precision = precision;
        return this;
    }
    public Time64 fromUnixTime(long time) {
        return new Time64(time - UNIX_EPOCH_OFFSET);
    }
    public Time64 fromUnixTime(long time, String precision) {
        return new Time64(time - UNIX_EPOCH_OFFSET).changePrecision(precision);
    }
    public Time64 fromUnixTime(long time, byte precision) {
        return new Time64(time - UNIX_EPOCH_OFFSET).changePrecision(precision);
    }
    public Time64 fromUnixTime(long time, String precision, String inputPrecision) {
        return new Time64(time - UNIX_EPOCH_OFFSET, inputPrecision).changePrecision(precision);
    }
    public Time64 fromUnixTime(long time, byte precision, byte inputPrecision) {
        return new Time64(time - UNIX_EPOCH_OFFSET, inputPrecision).changePrecision(precision);
    }

    /**
     * @return Time in milliseconds since Jan 1 1970
     */
    public long toJavaTime() {
        return this.milliseconds() + UNIX_EPOCH_OFFSET;
    }

    /**
     * @return Time in seconds since Jan 1 1970
     */
    public long toUnixTime() {
        return this.seconds() + UNIX_EPOCH_OFFSET;
    }

    /**
     * @param precision String representation of precision
     * @return Time in specified precision since Jan 1 1970
     */
    public long toUnixTime(String precision) {
        switch (precisionStringToByte(precision)) {
            case 0 -> {
                return this.seconds() + UNIX_EPOCH_OFFSET;
            }
            case 1 -> {
                return this.milliseconds() + UNIX_EPOCH_OFFSET * 1000;
            }
            case 2 -> {
                return this.microseconds() + UNIX_EPOCH_OFFSET * 1000000;
            }
            case 3 -> {
                return this.nanoseconds() + UNIX_EPOCH_OFFSET * 1000000000;
            }
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        }
    }
    public long toUnixTime(byte precision) {
        switch (precision) {
            case 0 -> {
                return this.seconds() + UNIX_EPOCH_OFFSET;
            }
            case 1 -> {
                return this.milliseconds() + UNIX_EPOCH_OFFSET * 1000;
            }
            case 2 -> {
                return this.microseconds() + UNIX_EPOCH_OFFSET * 1000000;
            }
            case 3 -> {
                return this.nanoseconds() + UNIX_EPOCH_OFFSET * 1000000000;
            }
            default -> throw new IllegalArgumentException("Precision must be one of: s, ms, μs, ns");
        }
    }
    private boolean yearIsLeap(long year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    private int nextWeekday(int weekday) {
        //0 = Sunday, 1 = Monday, etc.
        if (weekday == 6) {
            return 0;
        } else {
            return weekday + 1;
        }
    }
    private long[] yearMonthDay() {
        long[] ymd = new long[3];
        int days = (int) (this.seconds() / 86400);
        int year = 2000;
        while (days > 365) {
            if (yearIsLeap(year)) {
                if (days > 366) {
                    days -= 366;
                    year++;
                } else {
                    break;
                }
            } else {
                days -= 365;
                year++;
            }
        }
        int month = 0;
        while (days > monthLengths[month]) {
            if (month == 1) {
                if (yearIsLeap(year)) {
                    if (days > 29) {
                        days -= 29;
                        month++;
                    } else {
                        break;
                    }
                } else {
                    days -= 28;
                    month++;
                }
            } else {
                days -= monthLengths[month];
                month++;
            }
        }
        ymd[0] = year;
        ymd[1] = month + 1;
        ymd[2] = days + 1;
        return ymd;
    }
    private int firstWeekdayOfYear() {
        //0 = Sunday, 1 = Monday, etc.
        int weekday = 6;
        int year = (int) yearMonthDay()[0];
        while (year > 2000) {
            if(yearIsLeap(year)) {
                weekday = nextWeekday(nextWeekday(weekday));
            } else {
                weekday = nextWeekday(weekday);
            }
            year--;
        }
        return weekday;
    }
    public int d() {
        return (int) yearMonthDay()[2];
    }
    public int H() {
        return (int) (this.seconds() % 86400 / 3600);
    }
    public int I() {
        int hour = (int) (this.seconds() % 86400 / 3600);
        if (hour == 0) {
            return 12;
        } else if (hour > 12) {
            return hour - 12;
        } else {
            return hour;
        }
    }
    public int j() {
        int months = (int) yearMonthDay()[1];
        int days = (int) yearMonthDay()[2];
        int i = 0;
        while (i < months - 1) {
            days += monthLengths[i];
            i++;
        }
        return days;
    }
    public int m() {
        return (int) yearMonthDay()[1];
    }
    public int M() {
        return (int) (this.seconds() % 3600 / 60);
    }
    public int S() {
        return (int) (this.seconds() % 60);
    }
    public int mS() {
        return (int) (this.milliseconds() % 1000);
    }
    public int uS() {
        return (int) (this.microseconds() % 1000);
    }
    public int μS() {
        return uS();
    }
    public int nS() {
        return (int) (this.nanoseconds() % 1000);
    }
    public int U() {
        //week number with Sunday as the first day of the week
        int dayOfFirstFullWeek = 7 - firstWeekdayOfYear();
        int days = j();
        int week = 0;
        while (days > dayOfFirstFullWeek) {
            days -= 7;
            week++;
        }
        return week;
    }
    public int w() {
        return firstWeekdayOfYear() + j() % 7;
    }
    public int W() {
        //week number with Monday as the first day of the week
        int dayOfFirstFullWeek = 8 - firstWeekdayOfYear();
        int days = j();
        int week = 0;
        while (days > dayOfFirstFullWeek) {
            days -= 7;
            week++;
        }
        return week;
    }
    public int y() {
        return (int) yearMonthDay()[0] % 100;
    }
    public int Y() {
        return (int) yearMonthDay()[0];
    }
}
