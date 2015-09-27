package ca.ianp.avian.util

import android.content.Context
import android.preference.PreferenceManager
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

/**
 * This class contains application-wide utility functions.
 */
public object Utilities {

    /**
     * Returns a configured Twitter object.
     */
    public fun getTwitterInstance(context: Context): Twitter {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val config: Configuration =  ConfigurationBuilder()
                .setOAuthConsumerKey(Constants.TWITTER_KEY)
                .setOAuthConsumerSecret(Constants.TWITTER_SECRET)
                .setOAuthAccessToken(prefs!!.getString(Constants.PREFS_USER_TOKEN, null))
                .setOAuthAccessTokenSecret(prefs.getString(Constants.PREFS_USER_SECRET, null))
                .build()

        return TwitterFactory(config).getInstance()
    }
}
