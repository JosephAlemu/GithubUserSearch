package githubprofile.joseph.com.githubprofile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import githubprofile.joseph.com.githubprofile.adapter.ReposAdapter;
import githubprofile.joseph.com.githubprofile.model.GitHubRepo;
import githubprofile.joseph.com.githubprofile.rest.ApiClient;
import githubprofile.joseph.com.githubprofile.rest.GitHubRepoEndPoint;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RepoActivity extends AppCompatActivity {


    String receivedUserName;
    TextView userNameTV;
    RecyclerView mRecyclerView;
    List<GitHubRepo> myDataSource = new ArrayList<>();
    RecyclerView.Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

       
        Bundle extras = getIntent().getExtras();
        receivedUserName = extras.getString("username");

        userNameTV = (TextView) findViewById(R.id.userNameTV);

        userNameTV.setText("User: " + receivedUserName);

        mRecyclerView= (RecyclerView) findViewById(R.id.repos_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new ReposAdapter(myDataSource, R.layout.list_item_repo,
                getApplicationContext());

        mRecyclerView.setAdapter(myAdapter);

        loadRepositories();

    }

    public void loadRepositories(){
        GitHubRepoEndPoint apiService =
                ApiClient.getClient().create(GitHubRepoEndPoint.class);

        Call<List<GitHubRepo>> call = apiService.getRepo(receivedUserName);

        call.enqueue(new Callback<List<GitHubRepo>>() {
                         @Override
                         public void onResponse(Response<List<GitHubRepo>> response, Retrofit retrofit) {
                             myDataSource.clear();
                             myDataSource.addAll(response.body());
                             myAdapter.notifyDataSetChanged();
                         }

                         @Override
                         public void onFailure(Throwable t) {
                             Log.e("Repos", t.toString());
                         }
                     } );


    }
}
