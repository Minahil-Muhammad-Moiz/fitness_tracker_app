package com.minahil.fitness_tracker_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private var glassesLogged = 0
    private var workoutMinutes = 28
    private var sleepHours = 0.0 // New variable for sleep

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val greetingText: TextView = view.findViewById(R.id.greetingText)
        val waterValue: TextView = view.findViewById(R.id.waterValue)
        val workoutValue: TextView = view.findViewById(R.id.workoutValue)
        val sleepValue: TextView = view.findViewById(R.id.sleepValue) // Add this
        val logWaterButton: Button = view.findViewById(R.id.logWaterButton)
        val startWorkoutButton: Button = view.findViewById(R.id.startWorkoutButton)
        val logSleepButton: Button = view.findViewById(R.id.logSleepButton) // Add this

        // Personalize greeting using the logged-in user's name from SessionManager
        val session = SessionManager(requireContext())
        val firstName = session.getUserName().split(" ").firstOrNull() ?: "Athlete"
        greetingText.text = "Hi, $firstName! 👋"

        // Set initial sleep value
        sleepValue.text = "0.0 hrs"

        logWaterButton.setOnClickListener {
            glassesLogged++
            val liters = 0.9 + (glassesLogged * 0.25)
            waterValue.text = String.format("%.1fL", liters)
            Toast.makeText(requireContext(), "Glass logged! 💧", Toast.LENGTH_SHORT).show()
        }

        startWorkoutButton.setOnClickListener {
            workoutMinutes += 5
            workoutValue.text = "$workoutMinutes min"
            Toast.makeText(requireContext(), "+5 minutes added to today's workout!", Toast.LENGTH_SHORT).show()
        }

        // NEW: Log Sleep button click listener
        logSleepButton.setOnClickListener {
            sleepHours += 0.5
            sleepValue.text = String.format("%.1f hrs", sleepHours)
            Toast.makeText(requireContext(), "+0.5 hours of sleep logged! 😴", Toast.LENGTH_SHORT).show()
        }
    }
}