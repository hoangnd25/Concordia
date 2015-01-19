package swindroid.suntime.ui;

public class Location
{
    private String latitude;
    private String longitude;
    private String tZone;

    public Location(String latitude, String longitude, String tZone){
        this.latitude = latitude;
        this.longitude = longitude;
        this.tZone = tZone;
    }

    public String toString()
    {
        return latitude + ", " + longitude+ ", " + tZone;
    }

}
