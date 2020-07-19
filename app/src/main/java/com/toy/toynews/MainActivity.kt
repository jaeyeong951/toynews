package com.toy.toynews

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
//            Configuration.UI_MODE_NIGHT_YES ->
//
//            Configuration.UI_MODE_NIGHT_NO ->

        }

        this.window.apply {
            //decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            //statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }

        setSupportActionBar(main_toolbar)

        val host = nav_host_fragment as NavHostFragment
        val navController = host.navController

        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(
                fm: FragmentManager,
                f: Fragment,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentCreated(fm, f, savedInstanceState)
                Log.e("ON_CREATED", f.toString())
            }

            override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                super.onFragmentAttached(fm, f, context)
                Log.e("ON_ATTACHED", f.toString())
            }

            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                Log.e("ON_VIEW_CREATED", f.toString())
            }

            override fun onFragmentActivityCreated(
                fm: FragmentManager,
                f: Fragment,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentActivityCreated(fm, f, savedInstanceState)
                Log.e("ON_ACTIVITY_CREATED", f.toString())
            }

            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                super.onFragmentStarted(fm, f)
                Log.e("ON_STARTED", f.toString())
            }

            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                super.onFragmentResumed(fm, f)
                Log.e("ON_RESUMED", f.toString())
            }

            override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                super.onFragmentPaused(fm, f)
                Log.e("ON_PAUSED", f.toString())
            }

            override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                super.onFragmentStopped(fm, f)
                Log.e("ON_STOPPED", f.toString())
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentViewDestroyed(fm, f)
                Log.e("ON_VIEW_DESTROYED", f.toString())
            }

            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentDestroyed(fm, f)
                Log.e("ON_DESTROYED", f.toString())
            }

            override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                super.onFragmentDetached(fm, f)
                Log.e("ON_DETACHED", f.toString())
            }


        }, true)

        var appBarConfiguration  =
            AppBarConfiguration(setOf(R.id.mainFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.mainFragment -> {
                    main_toolbar.visibility = View.GONE
                }
                R.id.webViewFragment -> {
                    main_toolbar.visibility = View.VISIBLE
                    main_toolbar.main_title.text = ""
                }
                else->{
                    main_toolbar.visibility = View.VISIBLE
                    supportActionBar?.show()
                }
            }
        }
    }
}