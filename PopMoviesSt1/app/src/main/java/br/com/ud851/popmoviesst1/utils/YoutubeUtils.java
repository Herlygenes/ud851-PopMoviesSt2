package br.com.ud851.popmoviesst1.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Herlygenes Pinto on 20/02/2018.
 */

public class YoutubeUtils {
    public final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public final static String YOUTUBE_APP_URL = "vnd.youtube:";

    public static void watchYoutubeVideo(Context context, String videoId){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_URL + videoId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_BASE_URL + videoId));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
