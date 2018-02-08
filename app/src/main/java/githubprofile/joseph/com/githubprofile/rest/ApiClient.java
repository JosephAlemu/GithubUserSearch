package githubprofile.joseph.com.githubprofile.rest;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Admin on 2/8/2018.
 */


public class ApiClient {

    public static final String BASE_URL = "https://api.github.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}