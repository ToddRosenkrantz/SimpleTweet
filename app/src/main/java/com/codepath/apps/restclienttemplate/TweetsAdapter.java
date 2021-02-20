package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    Context context;
    List<Tweet> tweets;

    // Must pass in context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // for each row, we need to 'inflate' the layout, ie. create the screen elements needed.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent,false);
        return new ViewHolder(view);
    }

    // then we need to Bind or populate those screen elements with the desired data based on position in the Array
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get data from array & populate each row
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }



    // Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvName;
        TextView tvScreenName;
        TextView tvRecency;
        ImageView ivMedia;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvName = itemView.findViewById(R.id.tvName);
            tvRecency = itemView.findViewById(R.id.tvRecency);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            container = itemView.findViewById(R.id.rvContainer);
        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvRecency.setText(TimeFormatter.getTimeDifference(tweet.createdAt));
//            tvRecency.setText(tweet.tweet_id_str);
            tvScreenName.setText("@" + tweet.user.screenName);
            tvName.setText(tweet.user.name);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
//            if(tweet.media_url != ""){
//                Glide.with(context).load(tweet.media_url).into(ivMedia);
//                Log.i("TimelineActivity", tweet.media_url);
//            }
            tvBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "Clicked on tweet: " + tweet.tweet_id_str, Toast.LENGTH_LONG).show();
                    int position = getAdapterPosition();
                    // validate position
                    if (position != RecyclerView.NO_POSITION) {
                        // get the movie at the position, DOESN'T work if class is static
//                        Movie movie = movies.get(position);
                        // create intent for the new activity
                        Intent i = new Intent(context, DetailActivity.class);
                        i.putExtra("tweet_id", tweet.tweet_id_str);
//                        i.putExtra("tweet_id", "1362159893910720515");
                        // show the activity
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        ActivityOptionsCompat options = ActivityOptionsCompat.
//                                makeSceneTransitionAnimation(this, ivPoster, "poster");
//                        startActivity(intent, options.toBundle());
//                        context.startActivity(i,options.toBundle());
                            context.startActivity(i);
                        } else {
//                        startActivity(intent);
                            context.startActivity(i);
                        }
                    }
                }
            });
        }
    }
    // Clean all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }
    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }
}
