package ca.ianp.avian.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import ca.ianp.avian.R

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
    }

    override fun onClick(v: View) {
        throw UnsupportedOperationException()
    }
}