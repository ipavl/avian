package ca.ianp.avian.fragment

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.ianp.avian.R
import ca.ianp.avian.adapter.TimelineAdapter
import ca.ianp.avian.data.Tweet

import kotlinx.android.synthetic.fragment_timeline.*
import kotlinx.android.synthetic.fragment_timeline.view.*
import java.util.*

/**
 * This fragment is used to display the user's timeline.
 */
public class TimelineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_timeline, container, false)

        val tweets: ArrayList<Tweet> = ArrayList<Tweet>();

        // TODO: Replace with actual data
        for (i in 0..2000) {
            // Instead of displaying the raw date, convert it to a relative time, e.g. "2 hours ago"
            val createdAtRelative = DateUtils.getRelativeTimeSpanString(
                    System.currentTimeMillis() - i * 60 * 360,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS
            )

            tweets.add(Tweet(i.toLong(), "DummyUser" + i, "",
                    createdAtRelative.toString(),
                    "Lorem ipsum dolor sit amet, eum ad ridens, laoreet delicatissimi. Tale malorum" +
                            "mel ad, pri ridens corrumpit cu? In choro http://example.com!"));
        }

        val adapter: TimelineAdapter = TimelineAdapter(tweets)
        view.recycler_view.setAdapter(adapter)
        view.recycler_view.setHasFixedSize(true)
        view.recycler_view.setLayoutManager(LinearLayoutManager(container?.getContext()))

        return view
    }
}
