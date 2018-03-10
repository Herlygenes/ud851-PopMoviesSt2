package br.com.ud851.popmoviesst1.services.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Herlygenes Pinto on 09/03/2018.
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    private static Retrofit getClient(String baseUrl){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

