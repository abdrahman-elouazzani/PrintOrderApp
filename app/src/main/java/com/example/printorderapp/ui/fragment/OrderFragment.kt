package com.example.printorderapp.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.printorderapp.databinding.FragmentOrderBinding
import com.example.printorderapp.ui.viewmodel.NotificationViewModel

class OrderFragment: Fragment() {

    private lateinit var viewModel: NotificationViewModel
    private var _binding: FragmentOrderBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[NotificationViewModel::class.java]

        _binding?.btnOrder?.setOnClickListener {
            checkPermissionAndSendNotification()
        }
    }

    private fun checkPermissionAndSendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if the permission is already granted
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, send the notification
                viewModel.sendOrderNotification()
            } else {
                // Request the permission
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // No permission required for Android versions below 13
            viewModel.sendOrderNotification()
        }
    }

    // Register the permission launcher
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, send the notification
            viewModel.sendOrderNotification()
        } else {
            // Handle permission denied case (show a message, etc.)
            Toast.makeText(requireContext(), "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}