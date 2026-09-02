package com.minahil.fitness_tracker_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.badge.BadgeDrawable

class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.notificationListView)

        // Sample notification data (in a real app this would come from a database or server)
        val notifications = listOf(
            AppNotification("🎯", "Goal Reached!", "You hit 10,000 steps today. Great job!", "5m"),
            AppNotification("💧", "Hydration Reminder", "You haven't logged water in 3 hours.", "1h"),
            AppNotification("🔥", "Streak Alert", "You're on a 5-day workout streak!", "3h"),
            AppNotification("🏋️", "Workout Suggestion", "Try a 20-min HIIT session today.", "6h"),
            AppNotification("📊", "Weekly Report", "Your weekly fitness summary is ready.", "1d"),
            AppNotification("🎉", "New Badge", "You unlocked the 'Early Bird' badge.", "2d")
        )

        listView.adapter = NotificationAdapter(requireContext(), notifications)

        // NEW: Clear the badge when user views notifications
        clearBadge()
    }

    // NEW: Clear the badge when notifications are viewed
    private fun clearBadge() {
        try {
            val bottomNavigation: BottomNavigationView? = activity?.findViewById(R.id.bottomNavigation)
            bottomNavigation?.let {
                val badge = it.getBadge(R.id.nav_notifications)
                badge?.isVisible = false
                badge?.clearNumber()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Optional: Mark notifications as read when viewed
    override fun onResume() {
        super.onResume()
        // Clear badge when returning to this fragment
        clearBadge()
    }
}