package br.com.ud851.popmoviesst1.utils;

/**
 * Created by Herlygenes Pinto on 03/03/2018.
 */

public class StateHolder {
    private static String state;
    private static String lastActivity;
    public static String getState(){return state;}
    public static void setState(String state){StateHolder.state = state;}
    public static String getLastActivity(){return lastActivity;}
    public static void setLastActivity(String lastActivity){StateHolder.lastActivity = lastActivity;}
}
