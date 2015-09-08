package ca.ianp.avian.util

import ca.ianp.avian.BuildConfig

/**
 * Application-wide constants.
 */
public object Constants {
    public val TAG: String = "Avian"
    public val URI_SCHEME: String = "avian:///"

    public val TWITTER_KEY: String = BuildConfig.TWITTER_CONSUMER_KEY
    public val TWITTER_SECRET: String = BuildConfig.TWITTER_CONSUMER_SECRET

    public val PREFS_USER_ID: String = "user_id"
    public val PREFS_USER_TOKEN: String = "user_token"
    public val PREFS_USER_SECRET: String = "user_secret"
    public val PREFS_USER_SCREEN_NAME: String = "user_screen_name"

    public val EXTRA_USER_SCREEN_NAME: String = "user_screen_name"

    public val INTENT_OAUTH_RESULT: Int = 0x100;
}
