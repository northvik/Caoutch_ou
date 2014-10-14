package data.com.model;

/**
 * Created by camillepire on 13/10/2014.
 */
public class Pharmacie {

    private String name;//rs
    private Double lat;//wgs84[0]
    private Double lng;//wgs84[1]
    private Integer telephone;
    private String adresse_complete;// numvoie+' '+typvoie+' '+voie+', '+cp+' '+commune

    public Pharmacie(String name, Double lat, Double lng, Integer telephone, String adresse_complete) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.telephone = telephone;
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

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getAdresse_complete() {
        return adresse_complete;
    }

    public void setAdresse_complete(String adresse_complete) {
        this.adresse_complete = adresse_complete;
    }
}
