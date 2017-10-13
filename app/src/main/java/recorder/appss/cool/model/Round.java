
package recorder.appss.cool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Round {

    @SerializedName("dbid")
    @Expose
    private Integer dbid;
    @SerializedName("hasLeagueTable")
    @Expose
    private Boolean hasLeagueTable;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getDbid() {
        return dbid;
    }

    public void setDbid(Integer dbid) {
        this.dbid = dbid;
    }

    public Boolean getHasLeagueTable() {
        return hasLeagueTable;
    }

    public void setHasLeagueTable(Boolean hasLeagueTable) {
        this.hasLeagueTable = hasLeagueTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
