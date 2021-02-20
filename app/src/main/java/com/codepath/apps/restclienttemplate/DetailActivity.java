package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweetv2;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";
    public static final int API = 2;
    Context context = this.getBaseContext();
    TwitterClient client;
    TextView tvTweetDetail;
    TextView tvLikesCount;
    TextView tvReplyCount;
    TextView tvRetweetCount;

    Tweetv2 tweet = new Tweetv2();
    //    List<Tweetv2> tweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTweetDetail = findViewById(R.id.tvTweetDetail);
        tvReplyCount = findViewById(R.id.tvReplyCount);
        tvRetweetCount = findViewById(R.id.tvRetweetCount);
        tvLikesCount = findViewById(R.id.tvLikeCount);

        tvTweetDetail.setText("Tweet Text goes here...");
        String tweetId = this.getIntent().getStringExtra("tweet_id");
        client = SweeterApp.getRestClient(this);
//        tweets = new ArrayList<>();
        Tweetv2 tweet = new Tweetv2();
        populateTweetv2(tweetId);
        Log.i(TAG, "Tweet Text: " + tweet.text);


        // ?tweet.fields=public_metrics
        //
//    Tweet → tweet.fields
//    User → user.fields
//    Media → media.fields
//    Poll → poll.fields
//    Place → place.fields    expansions=attachments.media_keys

//        curl --request GET
//            --url 'https://api.twitter.com/2/tweets?ids=1260294888811347969&tweet.fields=attachments,author_id,created_at,public_metrics,source&expansions=attachments.media_keys'
//            --header 'Authorization: Bearer $BEARER_TOKEN'


    }
    public void populateTweetv2(String tweet_id){
        client.getTweetv2(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"onSuccess!"+ json.toString());
                JSONObject jsonObject = json.jsonObject;
                try {
                    Tweetv2 temptweet = Tweetv2.fromJson(jsonObject);
                    Log.i(TAG, "Text now: " + temptweet.text);

                    tweet.text = temptweet.text;
                    tweet.id = temptweet.id;
                    tweet.replies = temptweet.replies;
                    tweet.retweets = temptweet.retweets;
                    tweet.likes = temptweet.likes;

                    tvTweetDetail.setText(tweet.text);
                    tvReplyCount.setText(String.valueOf(tweet.replies));
                    tvRetweetCount.setText(String.valueOf(tweet.retweets));
                    tvLikesCount.setText(String.valueOf(tweet.likes));
                } catch (JSONException e) {
                    Log.e(TAG,"JSON Exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG,"onFailure! " + response, throwable);
            }
        },tweet_id);
    }
}