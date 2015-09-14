package ca.ianp.avian.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Parcel
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.ianp.avian.R
import ca.ianp.avian.adapter.TimelineAdapter
import ca.ianp.avian.data.Tweet
import ca.ianp.avian.service.TimelineService
import ca.ianp.avian.util.Constants

import kotlinx.android.synthetic.fragment_timeline.*
import kotlinx.android.synthetic.fragment_timeline.view.*
import java.util.*

/**
 * This fragment is used to display the user's timeline.
 */
public class TimelineFragment : Fragment() {

    private var receiver: BroadcastReceiver? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_timeline, container, false)

        val tweets: ArrayList<Tweet>? = ArrayList<Tweet>()
        val parcel: Parcel = Parcel.obtain()

        parcel.writeLong(-1)
        parcel.writeString("")
        parcel.writeString("")
        parcel.writeString("")
        parcel.writeString("")
        parcel.writeString("")
        parcel.setDataPosition(0)

        tweets!!.add(Tweet(parcel))

        view.recycler_view.setAdapter(TimelineAdapter(tweets))
        view.recycler_view.setHasFixedSize(true)
        view.recycler_view.setLayoutManager(LinearLayoutManager(container?.getContext()))

        receiver = TimelineReceiver()
        getActivity().registerReceiver(receiver, IntentFilter(Constants.INTENT_UPDATE_TIMELINE))
        getActivity().startService(Intent(getActivity(), javaClass<TimelineService>()))
        Log.d(Constants.TAG, "Starting service")
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        try {
            Log.d(Constants.TAG, "Stopping service")
            getActivity().stopService(Intent(getActivity(), javaClass<TimelineService>()))
            getActivity().unregisterReceiver(receiver)
        } catch (e: Exception) {
            e.getMessage()
        }
    }

    private inner class TimelineReceiver() : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val tweets: ArrayList<Tweet> = intent.getParcelableArrayListExtra(
                    Constants.INTENT_TWEETS_LIST)
            Log.d(Constants.TAG, "Received tweets")
            recycler_view.setAdapter(TimelineAdapter(tweets))
        }
    }
}
