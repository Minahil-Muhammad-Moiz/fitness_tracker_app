package com.minahil.fitness_tracker_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.badge.BadgeDrawable

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
                R.id.nav_notifications -> NotificationFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }
            loadFragment(selectedFragment)
            true
        }

        // 6. Add badge to Alerts tab
        setupAlertBadge(bottomNavigation)
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
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // NEW: Setup badge for Alerts tab
    private fun setupAlertBadge(bottomNavigation: BottomNavigationView) {
        try {
            // Get or create badge for the notifications tab
            val badge = bottomNavigation.getOrCreateBadge(R.id.nav_notifications)

            // Set badge number (showing number of notifications)
            badge.number = 6 // This matches the number of notifications in your list

            // Make badge visible
            badge.isVisible = true

            // Optional: Customize badge appearance
            // badge.backgroundColor = getColor(R.color.red) // Custom color
            // badge.badgeTextColor = getColor(R.color.white) // Text color

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Optional: Method to update badge count dynamically
    fun updateAlertBadge(count: Int) {
        try {
            val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
            val badge = bottomNavigation.getOrCreateBadge(R.id.nav_notifications)
            badge.number = count
            badge.isVisible = count > 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}