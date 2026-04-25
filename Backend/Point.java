package Backend;

public class Point {

    private Long id;
    private double latitude;
    private double longitude;
    private String label;

    public Point() {}

    public Point(double latitude, double longitude, String label) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.label = label;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    @Override
    public String toString() {
        return "Point{id=" + id + ", lat=" + latitude
            + ", lon=" + longitude + ", label='" + label + "'}";
    }
}