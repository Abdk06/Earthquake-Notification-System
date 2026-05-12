
// Class for Earthquake objects to be stored in the earthquake list

public class Earthquake {
    // Instance variables
    private int id;
    private int time;
    private String place;
    private double longitude;
    private double latitude;
    private double depth;
    private double magnitude;
    
    // Argumented constructor
    public Earthquake(int id, int time, String place, double longitude, double latitude, double depth, double magnitude) {
        this.id = id;
        this.time = time;
        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;
        this.depth = depth;
        this.magnitude = magnitude;
    }
    
    // Getter method, returns id of earthquake
    public int getId() {
        return id;
    }
    
    // Getter method, returns time of earthquake
    public int getTime() {
        return time;
    }
    
    // Getter method, returns place of earthquake
    public String getPlace() {
        return place;
    }
    
    // Getter method, returns longitude of earthquake
    public double getLongitude() {
        return longitude;
    }

    // Getter method, returns latitude of earthquake
    public double getLatitude() {
        return latitude;
    }

    // Getter method, returns depth of earthquake
    public double getDepth() {
        return depth;
    }

    // Getter method, magnitude id of earthquake
    public double getMagnitude() {
        return magnitude;
    }
    
    // Returns the neccesary information about this earthquake
    @Override
    public String toString() {
        return "Earthquake " + this.place;
    }

    
}
