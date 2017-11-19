package recorder.appss.cool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Round extends GenericModel {


    @SerializedName("hasLeagueTable")
    @Expose
    private Boolean hasLeagueTable;

    public Boolean getHasLeagueTable() {
        return hasLeagueTable;
    }

    public void setHasLeagueTable(Boolean hasLeagueTable) {
        this.hasLeagueTable = hasLeagueTable;
    }
}