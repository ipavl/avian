package ca.ianp.avian.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import ca.ianp.avian.R
import ca.ianp.avian.activity.ComposeActivity
import ca.ianp.avian.data.Tweet
import ca.ianp.avian.util.Constants

import kotlinx.android.synthetic.feed_tweet.*
import kotlinx.android.synthetic.feed_tweet.view.*

public class TimelineAdapter(val tweets: List<Tweet>) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    private var context: Context? = null
    private var view: View? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        context = parent!!.getContext()
        val inflater: LayoutInflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.feed_tweet, parent, false)

        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        val tweet: Tweet = tweets.get(position)

        // TODO: Author avatar
        viewHolder!!.tweetAuthor.setText(tweet.authorName)
        viewHolder.tweetTime.setText(tweet.createdAt)
        viewHolder.tweetBody.setText(tweet.content)

        // Handle reply button clicks by sending the tweet ID and author to the ComposeActivity
        view!!.reply.setOnClickListener {
            val composeIntent: Intent = Intent(context, javaClass<ComposeActivity>())

            composeIntent.putExtra(Constants.EXTRA_TWEET_ID, tweet.id)
            composeIntent.putExtra(Constants.EXTRA_TWEET_AUTHOR, tweet.authorScreenName)

            context!!.startActivity(composeIntent)
        }
    }

    override fun getItemCount(): Int {
        return tweets.size()
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var tweetAuthor: TextView
        //public var tweetAuthorImage: ImageView
        public var tweetTime: TextView
        public var tweetBody: TextView

        init {
            tweetAuthor = itemView.tweet_author
            //tweetAuthorImage = itemView.tweet_author_image
            tweetTime = itemView.tweet_time
            tweetBody = itemView.tweet_body
        }


    }
}