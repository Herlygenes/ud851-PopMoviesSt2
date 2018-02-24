package br.com.ud851.popmoviesst1.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.ud851.popmoviesst1.data.vos.TrailerVO;

/**
 * Created by Herlygenes Pinto on 20/02/2018.
 */

public class YoutubeUtils {
    public final static String YOUTUBE = "YouTube";
    public final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public final static String YOUTUBE_APP_URL = "vnd.youtube:";

    public static URL buildYoutubeUrl(TrailerVO trailer){
        URL url = null;
        if(YOUTUBE.equals(trailer.getSite())){
            Uri builtUri = Uri.parse(YOUTUBE_BASE_URL + trailer.getKey()).buildUpon().build();
            try {
                url = new URL(builtUri.toString());
                Log.i("YOUTUBE_URL", url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    public static void watchYoutubeVideo(Context context, String videoId){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_URL + videoId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_BASE_URL + videoId));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }    }
}
