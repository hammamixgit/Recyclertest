
package recorder.appss.cool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outcome {

    @SerializedName("winner")
    @Expose
    private String winner;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("afterExtraTime")
    @Expose
    private Boolean afterExtraTime;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAfterExtraTime() {
        return afterExtraTime;
    }

    public void setAfterExtraTime(Boolean afterExtraTime) {
        this.afterExtraTime = afterExtraTime;
    }

}
