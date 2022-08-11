package com.mondialrelay.chucknorrisapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import com.mondialrelay.chucknorrisapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.textView.text = "Bonjour"

        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment


    }
}












