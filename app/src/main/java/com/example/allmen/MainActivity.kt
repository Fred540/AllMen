package com.example.allmen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.allmen.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bn_main)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.home_menu -> fragment = HomeFragment()
            R.id.article_menu -> fragment = ArticleFragment()
            R.id.history_menu -> fragment = HistoryFragment()
            R.id.settings_menu -> fragment = SettingsFragment()
        }
        return loadFragment(fragment!!)    }

}