package recorder.appss.cool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeTeam extends GenericModel {

    @SerializedName("shirtUrl")
    @Expose
    private String shirtUrl;

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
