package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public long id;
    public String body;
    public String createdAt;
    public String mediaURL;
    public User user;

    // empty constructor needed by the Parceler library
    public Tweet() {}

    //Given the JSON object representing the tweet we want to turn it into a Java tweet object
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        if(jsonObject.has("full_text")) {
            tweet.body = jsonObject.getString("full_text");
        } else {
            tweet.body = jsonObject.getString("text");
        }
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        // adds image into the Java tweet if there is one in the JSON
        if(jsonObject.getJSONObject("entities").has("media")) {
            tweet.mediaURL = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            Log.i("Tweet", tweet.mediaURL);
        } else{
            tweet.mediaURL = "null";
        }
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
