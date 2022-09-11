package Movies;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
    private final String name;
    private final Date date;
    private final Object[] hours;

    public Movie(String name, Date date, Object[] hours) {
        this.name = name;
        this.date = date;
        this.hours = hours;
    }


    @Override
    public String toString() {
        String result = name + " " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n";
        for (Object line : hours) {
            result += line + "\n";
        }
        return result;
    }

}
