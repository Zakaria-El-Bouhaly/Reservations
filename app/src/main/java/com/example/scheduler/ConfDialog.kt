package com.example.scheduler


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example1.projectapp.viewModels.AppointmentViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch


class ConfDialog(val AppId: String) : DialogFragment() {


    lateinit var appointmentViewModel: AppointmentViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = MaterialAlertDialogBuilder(it)

            appointmentViewModel = ViewModelProvider(
                this,
                defaultViewModelProviderFactory
            ).get(AppointmentViewModel::class.java)

            builder.setMessage("delete appointement")
                .setPositiveButton("Delete",
                    DialogInterface.OnClickListener { dialog, id ->
                        lifecycleScope.launch {
                            val result=appointmentViewModel.deleteAppoint(AppId).single()
                            if (result) {
                                Toast.makeText(it, "Deleted", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(it, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}