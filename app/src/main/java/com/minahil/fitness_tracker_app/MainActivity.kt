package com.minahil.fitness_tracker_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Session check: Redirect to LoginActivity if user is logged out
        session = SessionManager(this)
        if (!session.isLoggedIn()) {
            goToLogin()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 2. Handle System Window Insets for edge-to-edge layout matching your root XML id
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. Initialize Bottom Navigation
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)

        // 4. Load the HomeFragment automatically when app opens
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // 5. Handle tab selection using your exact project files
        bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_notifications -> NotificationFragment() // Changed to match your file name
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }
            loadFragment(selectedFragment)
            true
        }
    }

    // Helper utility to replace the FrameLayout container with your active fragment screen
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    // Logout handling method called from your ProfileFragment logout button
    fun logout() {
        session.logout()
        goToLogin()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java) // Uses your exact LoginActivity file
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
