package com.example.marvelapp.presentation

import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.security.AccessControlContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfigurattion: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container)
                as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavMain.setupWithNavController(navController)

        appBarConfigurattion = AppBarConfiguration(setOf(R.id.charactersFragment,
            R.id.favoritesFragment,R.id.aboutFragment))

        binding.toolbarApp.setupWithNavController(navController, appBarConfigurattion)

        navController.addOnDestinationChangedListener {_,destination,_->
            val isTopLevelDestination = appBarConfigurattion.topLevelDestinations.contains(destination.id)
            if (!isTopLevelDestination){
                binding.toolbarApp.setCollapseIcon(R.drawable.ic_back)
            }
        }
    }
}