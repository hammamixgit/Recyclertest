
package recorder.appss.cool.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Competition implements Parcelable {

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

    public Competition() {
    }

    @Override
    public String toString() {
        return getDbid()+"";
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordering);
        dest.writeInt(dbid);
        dest.writeString(name);
        dest.writeString(flagUrl);
    }
    public static final Parcelable.Creator<Competition> CREATOR = new Parcelable.Creator<Competition>() {
        public Competition createFromParcel(Parcel in) {
            return new Competition(in);
        }

        public Competition[] newArray(int size) {
            return new Competition[size];
        }
    };

    private Competition(Parcel in) {
        ordering = in.readInt();dbid= in.readInt();
        name = in.readString();flagUrl=in.readString();


    }
}
