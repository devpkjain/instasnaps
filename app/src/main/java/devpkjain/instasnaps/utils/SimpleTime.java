package devpkjain.instasnaps.utils;


public class SimpleTime {

    public static String formattedDuration(String timestamp) {
        Long currentTimestamp = System.currentTimeMillis() / 1000L;
        Long seconds = currentTimestamp - Long.parseLong(timestamp);
        Long minutes = seconds / 60;
        Long hours = minutes / 60;
        Long days = hours / 24;
        Long weeks = days / 7;
        Long months = weeks / 4;
        Long years = months / 12;

        if (years > 0) {
            return years + "y";
        } else if (months > 0) {
            return months + "m";
        } else if (weeks > 0) {
            return weeks + "w";
        } else if (days > 0) {
            return days + "d";
        } else if (hours > 0) {
            return hours + "h";
        } else if (minutes > 0) {
            return minutes + "m";
        } else {
            return seconds + "s";
        }
    }

}
