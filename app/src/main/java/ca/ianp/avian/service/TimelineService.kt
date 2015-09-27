package ca.ianp.avian.service

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.IBinder
import android.os.Parcel
import android.preference.PreferenceManager
import android.text.format.DateUtils
import android.util.Log
import ca.ianp.avian.data.Tweet
import ca.ianp.avian.util.Constants
import ca.ianp.avian.util.Utilities
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder
import java.util.ArrayList

public class TimelineService() : Service() {
    private var prefs: SharedPreferences? = null

    private var updater: FetchTweets? = null
    private var twitter: Twitter? = null

    override fun onCreate() {
        super.onCreate()

        twitter = Utilities.getTwitterInstance(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        updater = FetchTweets()
        updater!!.execute()

        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null;
    }

    private inner class FetchTweets : AsyncTask<String, String, ArrayList<Tweet>>() {

        override fun doInBackground(vararg params: String): ArrayList<Tweet> {
            val tweets: ArrayList<Tweet> = ArrayList<Tweet>()

            try {
                Log.d(Constants.TAG, "Fetching tweets")
                // Get the user's home timeline
                val statuses: List<Status> = twitter!!.getHomeTimeline()

                Log.d(Constants.TAG, "Tweets: " + statuses.size())

                // Convert the Status objects to Tweet objects and add them to the ArrayList
                for (status: Status in statuses) {
                    val parcel: Parcel = Parcel.obtain()

                    parcel.writeLong(status.getId())
                    parcel.writeString(status.getUser().getName())
                    parcel.writeString(status.getUser().getScreenName())
                    parcel.writeString(status.getUser().getProfileImageURLHttps())
                    parcel.writeString(DateUtils.getRelativeTimeSpanString(
                            status.getCreatedAt().getTime(),
                            System.currentTimeMillis(),
                            DateUtils.SECOND_IN_MILLIS).toString()
                    )
                    parcel.writeString(status.getText())
                    parcel.setDataPosition(0)

                    tweets.add(Tweet(parcel))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return tweets
        }

        override fun onPostExecute(tweets: ArrayList<Tweet>) {
            if (tweets.size() > 0) {
                val intent: Intent = Intent(Constants.INTENT_UPDATE_TIMELINE)
                intent.putParcelableArrayListExtra(Constants.INTENT_TWEETS_LIST, tweets)
                Log.d(Constants.TAG, "Broadcasting tweets")
                sendBroadcast(intent)
            } else {
                // TODO: Handle no result case
            }
        }
    }
}