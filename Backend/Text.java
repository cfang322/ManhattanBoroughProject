package Backend;

import java.time.LocalDateTime;

public class Text {

    // Properties
    private Long id;
    private String content;
    private double latitude;
    private double longitude;
    private String locationName;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int starRating;  // 1-5 stars

    // Constructors
    public Text() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.starRating = 0;
    }

    public Text(String content, double latitude, double longitude) {
        this();
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Text(String content, double latitude, double longitude, String userName) {
        this(content, latitude, longitude);
        this.userName = userName;
    }

    public Text(String content, double latitude, double longitude, String userName, int starRating) {
        this(content, latitude, longitude, userName);
        this.setStarRating(starRating);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public int getStarRating() { return starRating; }
    public void setStarRating(int starRating) {
        if (starRating >= 0 && starRating <= 5) {
            this.starRating = starRating;
        } else {
            throw new IllegalArgumentException("Star rating must be between 0 and 5");
        }
    }

    // Business Methods
    public boolean isValid() {
        return content != null && !content.trim().isEmpty();
    }

    /** Manhattan bounds: lat 40.700–40.882, lon -74.020 to -73.907 */
    public boolean isInManhattan() {
        return latitude >= 40.700 && latitude <= 40.882
            && longitude >= -74.020 && longitude <= -73.907;
    }

    public String getPreview() {
        if (content == null) return "";
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
        this.updatedAt = LocalDateTime.now();
    }

    public String getFormattedLocation() {
        if (locationName != null && !locationName.isEmpty()) {
            return locationName;
        }
        return String.format("%.4f, %.4f", latitude, longitude);
    }

    @Override
    public String toString() {
        return "Text{id=" + id + ", content='" + getPreview() + "', location="
            + getFormattedLocation() + ", user='" + userName + "', rating=" + starRating + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return id != null && id.equals(text.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}