package data.com.model;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Distributeur {

    private String name;//site
    private String acces;
    private Double lat;//geo_coordinates[0]
    private Double lng;//geo_coordinates[1]
    private String adresse_complete;
    private String horaires_normal;


    public Distributeur(String name, String acces, Double lat, Double lng, String adresse_complete, String horaires_normal) {
        this.name = name;
        this.acces = acces;
        this.lat = lat;
        this.lng = lng;
        this.adresse_complete = adresse_complete;
        this.horaires_normal = horaires_normal;
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

    public String getAdresse_complete() {
        return adresse_complete;
    }

    public void setAdresse_complete(String adresse_complete) {
        this.adresse_complete = adresse_complete;
    }

    public String getHoraires_normal() {
        return horaires_normal;
    }

    public void setHoraires_normal(String horaires_normal) {
        this.horaires_normal = horaires_normal;
    }
}
