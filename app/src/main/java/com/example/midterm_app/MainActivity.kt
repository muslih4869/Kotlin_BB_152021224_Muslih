package com.example.midterm_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(fHome())

        var nav = findViewById<BottomNavigationView>(R.id.bottomnav)
        nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_menu ->{
                    loadFragment(fHome())
                    true
                }
                R.id.map_menu ->{
                    loadFragment(MapsFragment())
                    true
                }
                R.id.prof_menu ->{
                    loadFragment(fProfile())
                    true
                }
                R.id.hist_menu ->{
                    loadFragment(fHistory())
                    true
                }
                else -> {false
                }
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.f_container,fragment)
        transaction.commit()
    }
}