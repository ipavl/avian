package ca.ianp.avian.data

/**
 * Represents an individual tweet.
 */
data class Tweet(
        val id: Long,
        val author: String,
        val avatar: String,
        val createdAt: String,
        val content: String
)
