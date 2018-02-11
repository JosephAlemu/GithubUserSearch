package githubprofile.joseph.com.githubprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import githubprofile.joseph.com.githubprofile.model.GitHubUser;
import githubprofile.joseph.com.githubprofile.rest.ApiClient;
import githubprofile.joseph.com.githubprofile.rest.GitHubUserEndPoints;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    EditText etSearch;

    ImageView avatarImg;
    TextView userNameTV;
    TextView followersTV;
    TextView followingTV;
    TextView logIn;
    TextView email;
    Button ownedrepos;
    Button ownedReposBtn;

    Bundle extras;
    String newString;

    Bitmap myImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        avatarImg = (ImageView) findViewById(R.id.avatar);
        userNameTV = (TextView) findViewById(R.id.username);
        followersTV = (TextView) findViewById(R.id.followers);
        followingTV = (TextView) findViewById(R.id.following);
        logIn = (TextView) findViewById(R.id.logIn);
        email = (TextView) findViewById(R.id.email);
        ownedrepos = (Button) findViewById(R.id.ownedReposBtn);
        ownedReposBtn = (Button) findViewById(R.id.ownedReposBtn);

        etSearch = (EditText) findViewById(R.id.etSearch);
    }

//    public void onClickSearch(View view) {
//
//        Intent i = new Intent(this, UserActivity.class);
//        i.putExtra("username", etSearch.getText().toString());
//        startActivity(i);
//        loadData();
//
//
//    }


    public void onClickSearch(View view) {
        newString = etSearch.getText().toString();
        final GitHubUserEndPoints apiService =
                ApiClient.getClient().create(GitHubUserEndPoints.class);

        Call<GitHubUser> call = apiService.getUser(newString);

        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Response<GitHubUser> response, Retrofit retrofit) {


                String currentUrl = response.body().getAvatar();


                Glide.with(getApplicationContext())
                        .load(currentUrl)
                        .into(avatarImg);

                if (response.body().getName() == null) {
                    userNameTV.setText("No name provided");
                } else {
                    userNameTV.setText("Username: " + response.body().getName());
                }

                followersTV.setText("Followers: " + response.body().getFollowers());
                followingTV.setText("Following: " + response.body().getFollowing());
                logIn.setText("LogIn: " + response.body().getLogin());

                if (response.body().getEmail() == null) {
                    email.setText("No email provided");
                } else {
                    email.setText("Email: " + response.body().getEmail());
                }
                ownedReposBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Failed!" + t.toString());
            }
        });

    }

    public void loadOwnRepos(View view) {

        Intent intent = new Intent(MainActivity.this, RepoActivity.class);
        intent.putExtra("username", newString);
        startActivity(intent);
    }


}


