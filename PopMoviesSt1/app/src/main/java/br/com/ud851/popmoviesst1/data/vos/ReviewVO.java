package br.com.ud851.popmoviesst1.data.vos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Herlygenes Pinto on 03/03/2018.
 */

public class ReviewVO implements Parcelable {
    public static String ID = "id";
    public static String AUTHOR = "author";
    public static String CONTENT = "content";
    public static String URL = "url";

    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewVO() {
        this.id = "";
        this.author = "";
        this.content = "";
        this.url = "";
    }

    public ReviewVO(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    protected ReviewVO(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<ReviewVO> CREATOR = new Creator<ReviewVO>() {
        @Override
        public ReviewVO createFromParcel(Parcel in) {
            return new ReviewVO(in);
        }

        @Override
        public ReviewVO[] newArray(int size) {
            return new ReviewVO[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }
}
