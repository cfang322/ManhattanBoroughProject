package Backend;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TextSave extends Text {

    private static final String DEFAULT_SAVE_DIRECTORY = "manhattan_comments";
    private static final String FILE_EXTENSION = ".txt";
    private String filePath;

    // Constructors
    public TextSave() { super(); }

    public TextSave(String content, double latitude, double longitude) {
        super(content, latitude, longitude);
    }

    public TextSave(String content, double latitude, double longitude, String userName) {
        super(content, latitude, longitude, userName);
    }

    public TextSave(String content, double latitude, double longitude, String userName, int starRating) {
        super(content, latitude, longitude, userName, starRating);
    }

    /** Save with auto-generated filename */
    public boolean save() {
        try {
            Path directory = Paths.get(DEFAULT_SAVE_DIRECTORY);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            String timestamp = getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "comment_"
                + (getId() != null ? getId() : "new") + "_" + timestamp + FILE_EXTENSION;
            this.filePath = Paths.get(DEFAULT_SAVE_DIRECTORY, fileName).toString();
            return saveToFile(filePath);
        } catch (IOException e) {
            System.err.println("Error saving comment: " + e.getMessage());
            return false;
        }
    }

    /** Save to a specific path */
    public boolean saveToFile(String path) throws IOException {
        this.filePath = path;
        Path file = Paths.get(path);
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        Files.writeString(file, formatForSave(), StandardCharsets.UTF_8);
        System.out.println("Comment saved to: " + path);
        return true;
    }

    /** Append to an existing file */
    public boolean appendToFile(String path) throws IOException {
        Path file = Paths.get(path);
        if (!Files.exists(file)) return saveToFile(path);
        Files.writeString(file, "\n" + formatForSave(),
            StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        System.out.println("Comment appended to: " + path);
        return true;
    }

    /** Save all comments to one file */
    public static boolean saveAll(List<TextSave> comments, String fileName) {
        try {
            Path directory = Paths.get(DEFAULT_SAVE_DIRECTORY);
            if (!Files.exists(directory)) Files.createDirectories(directory);
            String filePath = Paths.get(DEFAULT_SAVE_DIRECTORY, fileName).toString();
            Files.writeString(Paths.get(filePath), "", StandardCharsets.UTF_8);
            for (TextSave comment : comments) {
                comment.appendToFile(filePath);
            }
            System.out.println("Saved " + comments.size() + " comments to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving all comments: " + e.getMessage());
            return false;
        }
    }

    private String formatForSave() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("Comment ID: ").append(getId() != null ? getId() : "N/A").append("\n");
        sb.append("User: ").append(getUserName() != null ? getUserName() : "Anonymous").append("\n");
        sb.append("Location: ").append(getFormattedLocation()).append("\n");
        sb.append("Coordinates: ").append(getLatitude()).append(", ").append(getLongitude()).append("\n");
        sb.append("Rating: ").append(getStarRating()).append("/5\n");
        sb.append("Created: ")
            .append(getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .append("\n");
        sb.append("----------------------------------------\n");
        sb.append("Comment:\n").append(getContent()).append("\n");
        sb.append("========================================\n");
        return sb.toString();
    }

    public boolean deleteFile() {
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("No file path set");
            return false;
        }
        try {
            Path file = Paths.get(filePath);
            if (Files.exists(file)) {
                Files.delete(file);
                System.out.println("File deleted: " + filePath);
                return true;
            } else {
                System.err.println("File not found: " + filePath);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
            return false;
        }
    }

    public String getFilePath() { return filePath; }

    @Override
    public String toString() {
        return "TextSave{id=" + getId() + ", content='" + getPreview()
            + "', location=" + getFormattedLocation()
            + ", user='" + getUserName() + "', rating=" + getStarRating()
            + ", filePath='" + filePath + "'}";
    }
}