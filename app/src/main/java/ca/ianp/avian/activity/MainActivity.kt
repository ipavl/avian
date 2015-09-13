package ca.ianp.avian.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import ca.ianp.avian.R
import ca.ianp.avian.fragment.TimelineFragment
import ca.ianp.avian.util.Constants

import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

/**
 * The main activity class of the application. Contains a toolbar, a fragments container,
 * and a [Drawer][com.mikepenz.materialdrawer.Drawer].
 */
public class MainActivity : AppCompatActivity() {

    private var prefs: SharedPreferences? = null
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Note: Kotlin does not currently support finding included layouts by itself
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (prefs?.getString(Constants.PREFS_USER_TOKEN, null) == null) {
            // The user has not logged in/authorized the app before
            startActivityForResult(Intent(this, javaClass<OAuthActivity>()),
                    Constants.INTENT_OAUTH_RESULT)
        } else {
            configureDrawer(toolbar!!)
            loadTimeline()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml
        when (item!!.getItemId()) {
            R.id.action_settings -> return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: kotlin.Int, resultCode: kotlin.Int, data: Intent?) {
        super<AppCompatActivity>.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            // On result from OAuthActivity
            Constants.INTENT_OAUTH_RESULT -> {
                var screenName: String? = data?.getExtras()?.
                        getString(Constants.EXTRA_USER_SCREEN_NAME) ?: return
                var toastText = getString(R.string.logged_in_as) + " @" + screenName

                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()

                // Configure the drawer and load the timeline
                configureDrawer(toolbar!!)
                loadTimeline();
            }
        }
    }

    private fun loadTimeline() {

    }

    /**
     * Creates and configures the [Drawer][com.mikepenz.materialdrawer.Drawer].
     */
    private fun configureDrawer(toolbar: Toolbar) {
        val accountHeader = setUpAccountHeader()
        val drawer = setUpNavigationDrawer(toolbar, accountHeader)
        drawer.setSelection(DRAWER_TIMELINE) // set default item
    }

    /**
     * Sets up the account header for the [Drawer][com.mikepenz.materialdrawer.Drawer]. This method
     * should be called before [setUpNavigationDrawer].
     */
    private fun setUpAccountHeader(): AccountHeader {
        return AccountHeaderBuilder()
                .withActivity(this)
                //.withHeaderBackground(R.drawable.account_header)  // TODO
                .addProfiles(
                        ProfileDrawerItem()
                                .withEmail("@" + prefs?.getString(Constants.EXTRA_USER_SCREEN_NAME, null))
                )
                .build()
    }

    /**
     * Sets up the navigation drawer and its items. This method should be called after
     * [setUpAccountHeader].
     *
     * @param toolbar The toolbar element to attach the navigation drawer to
     * @return The created drawer object
     */
    private fun setUpNavigationDrawer(toolbar: Toolbar, accountHeader: AccountHeader): Drawer {
        return DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        PrimaryDrawerItem()
                                .withName(R.string.timeline)
                                .withIdentifier(DRAWER_TIMELINE))
                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>?): Boolean {
                        val fragmentManager = getSupportFragmentManager()
                        var fragment: Fragment? = null

                        when (position) {
                            DRAWER_TIMELINE -> {
                                fragment = TimelineFragment()
                                toolbar.setTitle(getString(R.string.timeline))
                            }
                        }

                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()

                        return false
                    }
                })
                .build()
    }

    companion object {
        private val DRAWER_TIMELINE = 1
    }
}
