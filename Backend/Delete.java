package Backend;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Delete {

    /** Delete a saved TextSave file by its file path */
    public static boolean deleteFile(String filePath) {
        try {
            Path file = Paths.get(filePath);
            if (Files.exists(file)) {
                Files.delete(file);
                System.out.println("Deleted: " + filePath);
                return true;
            } else {
                System.err.println("File not found: " + filePath);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Error deleting: " + e.getMessage());
            return false;
        }
    }

    /** Remove a comment from a list by ID */
    public static boolean deleteFromList(List<Text> comments, Long id) {
        return comments.removeIf(c -> id.equals(c.getId()));
    }

    /** Remove a point from a list by ID */
    public static boolean deletePointFromList(List<Point> points, Long id) {
        return points.removeIf(p -> id.equals(p.getId()));
    }

    /** Delete all files in a directory */
    public static int deleteAll(String directoryPath) {
        int count = 0;
        try {
            Path dir = Paths.get(directoryPath);
            if (!Files.exists(dir)) return 0;
            try (var stream = Files.list(dir)) {
                List<Path> files = stream.toList();
                for (Path f : files) {
                    Files.delete(f);
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error deleting all: " + e.getMessage());
        }
        return count;
    }
}