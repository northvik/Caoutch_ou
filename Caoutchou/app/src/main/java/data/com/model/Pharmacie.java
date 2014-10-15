package data.com.model;

/**
 * Created by camillepire on 13/10/2014.
 */
public class Pharmacie implements java.io.Serializable{

    private String name;//rs
    private Double lat;//wgs84[0]
    private Double lng;//wgs84[1]
    private String telephone;
    private String adrComplete;// numvoie+' '+typvoie+' '+voie+', '+cp+' '+commune
    private Integer id;

    public Pharmacie(String name, Double lat, Double lng, String telephone, String adrComplete, Integer id) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.telephone = telephone;
        this.adrComplete = adrComplete;
        this.id = id;
    }

    public Pharmacie()
    {

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdrComplete() {
        return adrComplete;
    }

    public void setAdrComplete(String adrComplete) {
        this.adrComplete = adrComplete;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
