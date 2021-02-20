package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweetv2 {

    public String id = "";
    public int retweets =0;
    public int replies = 0;
    public int likes = 0;
    public int quotes = 0;
    public String text = "";

    public static Tweetv2 fromJson(JSONObject jsonObject) throws JSONException{
        Tweetv2 tweet = new Tweetv2();
        JSONObject data = jsonObject.getJSONObject("data");
        tweet.text = data.getString("text");
        tweet.id = data.getString("id");
        if (data.has("public_metrics")){
            JSONObject metrics = data.getJSONObject("public_metrics");
            tweet.retweets = metrics.getInt("retweet_count");
            tweet.likes = metrics.getInt("like_count");
            tweet.quotes = metrics.getInt("quote_count");
            tweet.replies = metrics.getInt("reply_count");
        }
        if(jsonObject.has("entities")){
            JSONObject entities = data.getJSONObject("entities");
            if (entities.has("urls")){
                JSONArray urls = entities.getJSONArray("urls");
            }
        }
        Log.i("Tweetv2 text:", tweet.text);

        return tweet;
    }
    public static List<Tweetv2> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweetv2> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
