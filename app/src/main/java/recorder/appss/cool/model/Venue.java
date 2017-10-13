
package recorder.appss.cool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("dbid")
    @Expose
    private Integer dbid;
    @SerializedName("capacity")
    @Expose
    private Integer capacity;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("geolocation")
    @Expose
    private Geolocation geolocation;

    public Integer getDbid() {
        return dbid;
    }

    public void setDbid(Integer dbid) {
        this.dbid = dbid;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

}
