package uhk.kikm.navigationuhk.utils.localization;

/**
 * Datova trida urcena pro lokalizacni body pouzite v LocalizationService
 * Dominik Matoulek
 */
public class LocalizationServicePoint {
    private int x;
    private int y;

    private float latitude;
    private float longitude;

    public LocalizationServicePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public LocalizationServicePoint(int x, int y, float latitude, float longitude) {
        this.x = x;
        this.y = y;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
