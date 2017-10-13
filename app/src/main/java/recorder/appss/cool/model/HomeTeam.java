
package recorder.appss.cool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeTeam {

    @SerializedName("shirtUrl")
    @Expose
    private String shirtUrl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dbid")
    @Expose
    private Integer dbid;
    @SerializedName("isNational")
    @Expose
    private Boolean isNational;
    @SerializedName("shortName")
    @Expose
    private String shortName;
    @SerializedName("shortCode")
    @Expose
    private String shortCode;

    public String getShirtUrl() {
        return shirtUrl;
    }

    public void setShirtUrl(String shirtUrl) {
        this.shirtUrl = shirtUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDbid() {
        return dbid;
    }

    public void setDbid(Integer dbid) {
        this.dbid = dbid;
    }

    public Boolean getIsNational() {
        return isNational;
    }

    public void setIsNational(Boolean isNational) {
        this.isNational = isNational;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

}
