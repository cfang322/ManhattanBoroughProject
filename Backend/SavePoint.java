package Backend;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SavePoint extends Point {

    private static final String SAVE_DIRECTORY = "manhattan_points";
    private static final String FILE_EXTENSION = ".txt";
    private String filePath;
    private LocalDateTime savedAt;

    public SavePoint() { super(); }

    public SavePoint(double latitude, double longitude, String label) {
        super(latitude, longitude, label);
        this.savedAt = LocalDateTime.now();
    }

    /** Save this point to file */
    public boolean save() {
        try {
            Path directory = Paths.get(SAVE_DIRECTORY);
            if (!Files.exists(directory)) Files.createDirectories(directory);

            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "point_"
                + (getId() != null ? getId() : "new") + "_" + timestamp + FILE_EXTENSION;
            this.filePath = Paths.get(SAVE_DIRECTORY, fileName).toString();

            String content = "Point ID: " + getId() + "\n"
                + "Label: " + getLabel() + "\n"
                + "Latitude: " + getLatitude() + "\n"
                + "Longitude: " + getLongitude() + "\n"
                + "Saved at: " + savedAt + "\n";

            Files.writeString(Paths.get(filePath), content, StandardCharsets.UTF_8);
            System.out.println("Point saved to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving point: " + e.getMessage());
            return false;
        }
    }

    public String getFilePath() { return filePath; }
    public LocalDateTime getSavedAt() { return savedAt; }
}