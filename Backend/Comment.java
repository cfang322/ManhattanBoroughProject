package Backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Comment {

    /** Return all comments sorted by star rating descending */
    public static List<Text> getSortedByRating(List<Text> comments) {
        List<Text> sorted = new ArrayList<>(comments);
        sorted.sort(Comparator.comparingInt(Text::getStarRating).reversed());
        return sorted;
    }

    /** Filter comments for a specific location */
    public static List<Text> getByLocation(List<Text> comments, String locationName) {
        List<Text> result = new ArrayList<>();
        for (Text c : comments) {
            if (locationName.equalsIgnoreCase(c.getLocationName())) {
                result.add(c);
            }
        }
        result.sort(Comparator.comparingInt(Text::getStarRating).reversed());
        return result;
    }

    /** Get average star rating for a location */
    public static double getAverageRating(List<Text> comments, String locationName) {
        List<Text> filtered = getByLocation(comments, locationName);
        if (filtered.isEmpty()) return 0.0;
        return filtered.stream()
            .mapToInt(Text::getStarRating)
            .average()
            .orElse(0.0);
    }

    /** Get top N rated comments overall */
    public static List<Text> getTopRated(List<Text> comments, int n) {
        return getSortedByRating(comments).stream()
            .limit(n)
            .toList();
    }
}