package sample;

public class Time {
    private int hour;
    private int minutes;

    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public int compareTo(Time another) {
        if(this.hour > another.hour)
            return 1;
        else if (this.hour < another.hour)
            return -1;
        else {
            if (this.minutes > another.minutes)
                return 1;
            else if (this.minutes < another.minutes)
                return -1;
            else return 0;
        }
    }

    @Override
    public String toString() {
        if(minutes < 10)
            return hour + ":0" + minutes;
        else return hour + ":" + minutes;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }
}
