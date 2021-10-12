package com.example.googlenews.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.googlenews.Fragment.FavouriteFragment
import com.example.googlenews.Fragment.HeadlinesFragment
import com.example.googlenews.Fragment.HomePageFragment
import com.example.googlenews.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav= findViewById(R.id.bottom_navigation)
        val navController=findNavController(R.id.fragment)
        bottomNav.setupWithNavController(navController)

        val appBarConfig= AppBarConfiguration(setOf(R.id.homePageFragment,R.id.headlinesFragment,R.id.favouriteFragment))
        setupActionBarWithNavController(navController,appBarConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.logout -> {
                Firebase.auth.signOut()
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
//    private val navListener =
//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            var selectedFragment: Fragment? = null
//            when (item.itemId) {
//                R.id.home -> selectedFragment = HomePageFragment()
//                R.id.headlines -> selectedFragment = HeadlinesFragment()
//                R.id.favourite -> selectedFragment = FavouriteFragment()
//            }
//
//            supportFragmentManager.beginTransaction().replace(
//                R.id.fragment_container,
//                selectedFragment!!
//            ).commit()
//            true
//        }
