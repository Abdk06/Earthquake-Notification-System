
// Class for Watcher objects to be stored in the watcher list

public class Watcher {
    //Instance variables
    private double longitude;
    private double latitude;
    private String name;
    
    //Constructor
    public Watcher(double longitude, double latitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;

    }

    //Getter methods
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    
    public String getName() {
        return name;
    }
    
    // Display neccessary info, only name
    @Override
    public String toString() {
        return this.name;
    }
    
}
