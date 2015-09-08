package ca.ianp.avian.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

import ca.ianp.avian.R
import ca.ianp.avian.fragment.MainActivityFragment

import kotlinx.android.synthetic.activity_main.*

/**
 * The main activity class of the application. Contains a toolbar, a fragments container,
 * and a [Drawer][com.mikepenz.materialdrawer.Drawer].
 */
public class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = toolbar as Toolbar
        setSupportActionBar(toolbar)

        val drawer = setUpNavigationDrawer(toolbar)
        drawer.setSelection(DRAWER_TIMELINE) // set default item
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

    /**
     * Sets up the navigation drawer and its items.
     *
     * @param toolbar The toolbar element to attach the navigation drawer to
     * @return The created drawer object
     */
    private fun setUpNavigationDrawer(toolbar: Toolbar): Drawer {
        return DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
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
                                fragment = MainActivityFragment()
                                toolbar.setTitle(getString(R.string.timeline))
                            }
                        }

                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()

                        return false
                    }
                }).build()
    }

    companion object {
        private val DRAWER_TIMELINE = 0
    }
}
