package ca.ianp.avian.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import ca.ianp.avian.R

import kotlinx.android.synthetic.activity_compose.*

/**
 * This class is used for composing new tweets and replying to other tweets.
 */
public class ComposeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
        setTitle(getString(R.string.compose_tweet))

        // Set dialog size
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

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

    override fun onClick(v: View) {
        throw UnsupportedOperationException()
    }
}