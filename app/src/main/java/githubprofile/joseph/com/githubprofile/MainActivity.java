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

         etSearch =(EditText)findViewById(R.id.etSearch);
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


    public void onClickSearch(View view)
    {
        newString = etSearch.getText().toString();
        final GitHubUserEndPoints apiService =
                ApiClient.getClient().create(GitHubUserEndPoints.class);

        Call<GitHubUser> call = apiService.getUser(newString);

        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Response<GitHubUser> response, Retrofit retrofit) {
                ImageDownloader task = new ImageDownloader();

                try {
                    myImage = task.execute(response.body().getAvatar()).get();

                } catch (Exception e) {

                    e.printStackTrace();

                }

                avatarImg.setImageBitmap(myImage);
                avatarImg.getLayoutParams().height=220;
                avatarImg.getLayoutParams().width=220;

                if(response.body().getName() == null){
                    userNameTV.setText("No name provided");
                } else {
                    userNameTV.setText("Username: " + response.body().getName());
                }

                followersTV.setText("Followers: " + response.body().getFollowers());
                followingTV.setText("Following: " + response.body().getFollowing());
                logIn.setText("LogIn: " + response.body().getLogin());

                if(response.body().getEmail() == null){
                    email.setText("No email provided");
                } else{
                    email.setText("Email: " + response.body().getEmail());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Failed!" + t.toString());
            }
        });

    }
    public void loadOwnRepos(View view) {

        Intent intent = new Intent(MainActivity.this,RepoActivity.class);
        intent.putExtra("username", newString);
        startActivity(intent);
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            return null;
        }
    }




}
