package ca.ianp.avian.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import android.util.Log
import android.view.ViewGroup
import ca.ianp.avian.R
import ca.ianp.avian.data.Tweet
import ca.ianp.avian.util.Constants
import ca.ianp.avian.util.Utilities

import kotlinx.android.synthetic.activity_compose.*
import twitter4j.Status

import twitter4j.StatusUpdate
import twitter4j.Twitter
import java.util.*

/**
 * This class is used for composing new tweets and replying to other tweets.
 */
public class ComposeActivity : AppCompatActivity() {

    private var twitter: Twitter? = null
    private var tweetSender: SendTweet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
        setTitle(getString(R.string.compose_tweet))
        setUpCharCounter()

        // Set dialog size
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        twitter = Utilities.getTwitterInstance(this)

        val extras: Bundle? = getIntent().getExtras()
        var tweetId: Long = -1
        var tweetAuthorName: String? = null
        var tweetAuthorScreenName: String? = null

        // Get parent tweet details if replying to a tweet and prepopulate the reply box
        if (extras != null) {
            tweetId = extras.getLong(Constants.EXTRA_TWEET_ID)
            tweetAuthorName = extras.getString(Constants.EXTRA_TWEET_AUTHOR_NAME)
            tweetAuthorScreenName = extras.getString(Constants.EXTRA_TWEET_AUTHOR_SCREEN_NAME)

            // Prepopulate the parent tweet's author's screen name
            compose_tweet_box.setText("@" + tweetAuthorScreenName + " ")

            // Set the cursor to the end of the text
            compose_tweet_box.setSelection(compose_tweet_box.length())

            // Update the dialog title
            setTitle(getString(R.string.compose_tweet_reply) + " " + tweetAuthorName)
        }

        // Action to take when the user clicks the send tweet button
        send_tweet.setOnClickListener {
            val tweetContent = compose_tweet_box.getText().toString()

            // Only send tweets that actually have content
            if (tweetContent.trim().length() > 0) {
                tweetSender = SendTweet(tweetId, tweetAuthorScreenName, tweetContent)
                tweetSender!!.execute()
            }
        }
    }

    /**
     * Sets up the TextWatcher for the character counter.
     */
    fun setUpCharCounter() {
        val maxTweetLength = getResources().getInteger(R.integer.max_tweet_length)

        // Display the number of characters remaining as the user writes their tweet
        compose_tweet_box.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                chars_left_count.setText((maxTweetLength - s.length()).toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })

        // Initial characters remaining should be the max minus any text in the field
        chars_left_count.setText((maxTweetLength - chars_left_count.length()).toString())
    }

    /**
     * Sends a tweet in the background and then closes the activity.
     * TODO: Finish activity on success, show error on fail
     */
    private inner class SendTweet(id: Long, author: String?, content: String) : AsyncTask<Void, Void, String>() {

        var id: Long = -1
        var author: String? = null
        var content: String? = null

        init {
            this.id = id
            this.author = author
            this.content = content
        }

        override fun doInBackground(vararg params: Void): String {
            try {
                Log.d(Constants.TAG, "Sending tweet")

                if (id > -1 && author != null) {
                    // The tweet is in reply to another tweet
                    twitter!!.updateStatus(StatusUpdate(content).inReplyToStatusId(id))
                } else {
                    // The tweet is a new tweet
                    twitter!!.updateStatus(content)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        override fun onPostExecute(result: String) {
            finish()
        }
    }
}