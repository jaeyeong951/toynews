package com.toy.toynews

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.iid.FirebaseInstanceId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val messageReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e("LOG", "THIS IS BACKGROUND?!")
        }
    }

    override fun onStart() {
        super.onStart()

        if (checkGooglePlayServices()) {
            //DO NOTHING
        } else {
            //You won't be able to send notifications to this device
            Log.e("LOG", "Device doesn't have google play services")
        }
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task->
            if(task.isSuccessful){
                task.result?.let{
                    Log.d("test", it.token)
                }
            }
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, IntentFilter("DATA"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
            Configuration.UI_MODE_NIGHT_YES ->{
                window.navigationBarColor = baseContext.getColor(R.color.background)
                window.statusBarColor = baseContext.getColor(R.color.background)
            }
            Configuration.UI_MODE_NIGHT_NO ->{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.decorView.systemUiVisibility = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                }
                else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.navigationBarColor = baseContext.getColor(R.color.transparent)
            }
        }

        this.window.apply {
            //decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            //statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }

        val host = nav_host_fragment as NavHostFragment
        val navController = host.navController

        setSupportActionBar(main_toolbar)

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

        val appBarConfiguration  =
            AppBarConfiguration(setOf(R.id.mainFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.mainFragment -> {
                    main_toolbar.visibility = View.GONE
                }
                R.id.webViewFragment -> {
                    main_toolbar.visibility = View.GONE
                    main_toolbar.main_title.text = ""
                    main_toolbar.setNavigationOnClickListener {
                        onBackPressed()
                    }
                    //main_toolbar.setNavigationIcon(R.drawable.ic_application)
                }
                else->{
                    main_toolbar.visibility = View.VISIBLE
                    supportActionBar?.show()
                }
            }
        }
    }

    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Log.e("LOG", "Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Log.e("LOG", "Google play services updated")
            true
        }
    }
}