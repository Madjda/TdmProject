package com.example.tdm_project.sharedPreferences

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tdm_project.R

open class CustomBaseActivity : AppCompatActivity() {

    private lateinit var currentTheme: String
    private lateinit var sharedPref: PreferencesProvider

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferencesProvider(context = this)

        currentTheme = sharedPref.load()

        setAppTheme(currentTheme)
    }

    override fun onResume() {
        super.onResume()
        val theme = sharedPref.load()
        if(currentTheme != theme)
            recreate()
    }

    private fun setAppTheme(currentTheme: String) {
        when (currentTheme) {
            "light" -> setTheme(R.style.AppTheme)
            "dark" -> setTheme(R.style.DarkAppTheme)
        }
    }
}