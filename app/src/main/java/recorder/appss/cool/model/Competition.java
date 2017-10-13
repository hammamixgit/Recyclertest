
package recorder.appss.cool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competition {

    @SerializedName("ordering")
    @Expose
    private Integer ordering;
    @SerializedName("dbid")
    @Expose
    private Integer dbid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("flagUrl")
    @Expose
    private String flagUrl;

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Integer getDbid() {
        return dbid;
    }

    public void setDbid(Integer dbid) {
        this.dbid = dbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

}
