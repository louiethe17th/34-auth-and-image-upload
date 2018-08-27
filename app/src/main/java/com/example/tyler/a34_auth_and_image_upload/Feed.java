package com.example.tyler.a34_auth_and_image_upload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Feed extends AppCompatActivity {

    @BindView(R.id.feed) public RecyclerView recyclerView;
    public LinearLayoutManager linearLayoutManager;
    public PostAdapter postAdapter;

    private List<Post> mPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ButterKnife.bind(this);

        mPosts = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        postAdapter = new PostAdapter();
        postAdapter.setmPosts(mPosts);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postAdapter);
    }

    @OnClick(R.id.post)
    public void post() {
        Log.d("POST", "posting");

        Intent intent = new Intent(this, Upload.class);
        startActivity(intent);
    }
}
