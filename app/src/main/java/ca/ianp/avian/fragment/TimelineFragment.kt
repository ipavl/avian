package ca.ianp.avian.fragment

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.ianp.avian.R


/**
 * This fragment is used to display the user's timeline.
 */
public class TimelineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_timeline, container, false)
    }
}
