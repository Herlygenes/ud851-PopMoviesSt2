package br.com.ud851.popmoviesst1.utils;

/**
 * Created by Herlygenes Pinto on 03/03/2018.
 */

public class StateHolder {
    private static String sState;
    private static String sLastActivity;
    public static String getsState(){return sState;}
    public static void setsState(String state){StateHolder.sState = state;}
    public static String getsLastActivity(){return sLastActivity;}
    public static void setsLastActivity(String lastActivity){StateHolder.sLastActivity = lastActivity;}
}
