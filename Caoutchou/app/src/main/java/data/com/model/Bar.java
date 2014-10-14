package data.com.model;

/**
 * Created by camillepire on 14/10/2014.
 */
public class Bar {
    private String name;
    private Double lat;
    private Double lng;
    private String adresse_complete;//address+', '+cp+' '+city

    public Bar(String name, Double lat, Double lng, String adresse_complete) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.adresse_complete = adresse_complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAdresse_complete() {
        return adresse_complete;
    }

    public void setAdresse_complete(String adresse_complete) {
        this.adresse_complete = adresse_complete;
    }
}
