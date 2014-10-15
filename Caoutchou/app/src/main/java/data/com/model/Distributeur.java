package data.com.model;

public class Distributeur implements java.io.Serializable {

    private String name;//site
    private String acces;
    private Double lat;//geo_coordinates[0]
    private Double lng;//geo_coordinates[1]
    private String adrComplete;
    private String horaires;
    private Integer id;


    public Distributeur(String name, String acces, Double lat, Double lng, String adrComplete, String horaires, Integer id) {
        this.name = name;
        this.acces = acces;
        this.lat = lat;
        this.lng = lng;
        this.adrComplete = adrComplete;
        this.horaires = horaires;
        this.id = id;
    }

    public Distributeur(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcces() {
        return acces;
    }

    public void setAcces(String acces) {
        this.acces = acces;
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

    public String getAdrComplete() {
        return adrComplete;
    }

    public void setAdrComplete(String adrComplete) {
        this.adrComplete = adrComplete;
    }

    public String getHoraires() {
        return horaires;
    }

    public void setHoraires(String horaires) {
        if (horaires == "null")
        {
            this.horaires = "Indisponible";
        }
        else {
            this.horaires = horaires;
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
