package br.com.ud851.popmoviesst1.data.vos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Herlygenes on 17/02/2018.
 */

public class TrailerVO implements Parcelable {
    public static String ID = "id";
    public static String ISO_639_1 = "iso_639_1";
    public static String ISO_3166_1 = "iso_3166_1";
    public static String KEY = "key";
    public static String NAME = "name";
    public static String SITE = "site";
    public static String SIZE = "size";
    public static String TYPE = "type";

    private String id;
    private String iso6391;
    private String iso31661;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;

    public TrailerVO(String id, String iso6391, String iso31661, String key, String name, String site, String size, String type) {
        this.id = id;
        this.iso6391 = iso6391;
        this.iso31661 = iso31661;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public TrailerVO() {
        this.id = "";
        this.iso6391 = "";
        this.iso31661 = "";
        this.key = "";
        this.name = "";
        this.site = "";
        this.size = "";
        this.type = "";
    }

    protected TrailerVO(Parcel in) {
        id = in.readString();
        iso6391 = in.readString();
        iso31661 = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readString();
        type = in.readString();
    }

    public static final Creator<TrailerVO> CREATOR = new Creator<TrailerVO>() {
        @Override
        public TrailerVO createFromParcel(Parcel in) {
            return new TrailerVO(in);
        }

        @Override
        public TrailerVO[] newArray(int size) {
            return new TrailerVO[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(iso6391);
        parcel.writeString(iso31661);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(size);
        parcel.writeString(type);
    }
}
