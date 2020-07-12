package com.toy.toynews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.window.apply {
            //decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            //statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }

        setSupportActionBar(main_toolbar)

        val host = nav_host_fragment as NavHostFragment
        val navController = host.navController

        var appBarConfiguration  =
            AppBarConfiguration(setOf(R.id.mainFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.mainFragment -> {
                    main_toolbar.visibility = View.GONE
                }
                R.id.webViewFragment -> {
                    main_toolbar.visibility = View.GONE
                }
                else->{
                    main_toolbar.visibility = View.VISIBLE
                    supportActionBar?.show()
                }
            }
        }
    }
}