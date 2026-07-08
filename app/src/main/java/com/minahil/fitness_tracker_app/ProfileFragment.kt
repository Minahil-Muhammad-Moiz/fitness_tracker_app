package com.minahil.fitness_tracker_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val avatarInitial: TextView = view.findViewById(R.id.avatarInitial)
        val profileName: TextView = view.findViewById(R.id.profileName)
        val profileEmail: TextView = view.findViewById(R.id.profileEmail)
        val detailName: TextView = view.findViewById(R.id.detailName)
        val detailEmail: TextView = view.findViewById(R.id.detailEmail)
        val editProfileButton: Button = view.findViewById(R.id.editProfileButton)
        val logoutButton: Button = view.findViewById(R.id.logoutButton)

        // Pull the current user from SessionManager (saved during login)
        val session = SessionManager(requireContext())
        val name = session.getUserName()
        val email = session.getUserEmail()

        profileName.text = name
        profileEmail.text = email
        detailName.text = name
        detailEmail.text = email
        avatarInitial.text = if (name.isNotEmpty()) name.first().uppercase() else "A"

        editProfileButton.setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile coming soon!", Toast.LENGTH_SHORT).show()
        }

        // NAVIGATION: Profile -> Login (via MainActivity's logout())
        // Find your logoutButton click listener and update it to this:
        logoutButton.setOnClickListener {
            // 1. Create the dialog builder
            val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())

            // 2. Set the Title and Message the user will see
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to log out of your fitness tracker?")

            // 3. Define the "Yes" action button
            builder.setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss() // Close the pop-up box
                // Run your existing logout logic here safely:
                (requireActivity() as MainActivity).logout()
            }

            // 4. Define the "Cancel" action button
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // Just close the box, do nothing else
            }

            // 5. Build the dialog and show it dynamically on screen
            val alertDialog = builder.create()
            alertDialog.show()
        }

    }
}
