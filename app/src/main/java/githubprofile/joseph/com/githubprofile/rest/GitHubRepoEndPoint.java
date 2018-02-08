package githubprofile.joseph.com.githubprofile.rest;

import java.util.List;

import githubprofile.joseph.com.githubprofile.model.GitHubRepo;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubRepoEndPoint {

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("user") String name);

}


