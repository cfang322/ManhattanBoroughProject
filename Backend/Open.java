package Backend;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class Open {

    /** Read entire file and return as a Text object */
    public static Text readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String content = Files.readString(path, StandardCharsets.UTF_8);
        return new Text(content, filePath);
    }

    /** Read file as list of lines */
    public static List<String> readLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    /** Read file content as plain string */
    public static String readAsString(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    /** Check if file exists */
    public static boolean exists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}