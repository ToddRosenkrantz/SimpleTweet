package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {
//    public long id;
//    public String body;
//    public String createdAt;
//    public long userId;
//    public User user;
//    public Boolean media_flag;
//    public String media_url;

    @Query("SELECT " +
            "Tweet.id AS tweet_id, " +
            "Tweet.body AS tweet_body, " +
            "Tweet.createdAt AS tweet_createdAt, " +
            "Tweet.media_flag AS tweet_media_flag, " +
            "Tweet.media_url AS tweet_media_url," +
            "Tweet.userID AS tweet_userId," +
            "Tweet.tweet_id_str AS tweet_tweet_id_str," +
            "User.*" +
            " FROM Tweet INNER JOIN User ON Tweet.userId = User.id ORDER BY Tweet.createdAt DESC LIMIT 5")
    List<TweetWithUser> recentItems();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);
}
