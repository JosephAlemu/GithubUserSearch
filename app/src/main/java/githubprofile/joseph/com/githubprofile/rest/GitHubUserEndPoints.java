package githubprofile.joseph.com.githubprofile.rest;

import githubprofile.joseph.com.githubprofile.model.GitHubUser;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubUserEndPoints {

    @GET("/users/{user}")
    Call<GitHubUser> getUser(@Path("user") String user);
}



