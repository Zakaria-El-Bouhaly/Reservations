package com.example.scheduler

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.scheduler.Models.Appointment
import com.example.scheduler.databinding.AddDialogBinding
import com.example1.projectapp.viewModels.AppointmentViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat


class AddDialog(private val edit: Boolean, private val EditAppoint: Appointment?) : DialogFragment() {
    private lateinit var date: EditText
    private lateinit var time: EditText
    private lateinit var descr: EditText
    private lateinit var phone: EditText

    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var binding: AddDialogBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myact ->
            val builder = MaterialAlertDialogBuilder(myact)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            binding = AddDialogBinding.inflate(inflater)


            date = binding.editTextDate


            date.setOnClickListener {
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build()

                datePicker.show(childFragmentManager, "date")
                datePicker.addOnPositiveButtonClickListener {
                    Log.i("TIIIIIIIIIIIIIIIIIIIIIIME", datePicker.selection.toString())
                    date.setText(datePicker.headerText)
                }
            }

            time = binding.editTextTime
            time.setOnClickListener {
                val timepicker = MaterialTimePicker
                    .Builder()
                    .setTitleText("Select a time").setTimeFormat(TimeFormat.CLOCK_24H)
                    .build()

                timepicker.show(childFragmentManager, "tag")
                timepicker.addOnPositiveButtonClickListener {
                    time.setText("${timepicker.hour}:${timepicker.minute}")
                }

            }

            descr = binding.descField
            phone = binding.editTextNumber

            var title_text = "Add"

            appointmentViewModel = ViewModelProvider(
                this,
                defaultViewModelProviderFactory
            )[AppointmentViewModel::class.java]

            if (edit) {
                val title = binding.title
                date.setText(EditAppoint?.date)
                time.setText(EditAppoint?.time)
                descr.setText(EditAppoint?.description)
                phone.setText(EditAppoint?.num)
                title_text = "Edit"
                title.text = title_text

            }


            builder.setView(binding.root)
                // Add action buttons
                .setPositiveButton(
                    title_text,
                    DialogInterface.OnClickListener { dialog, id ->

                        val app = Appointment(
                            "",
                            "",
                            date.text.toString(),
                            time.text.toString(),
                            descr.text.toString(),
                            phone.text.toString()
                        )

                        if (edit) {
                            app.appId = EditAppoint?.appId!!
                            if (appointmentViewModel.upcreateAppoint(app)) {
                                Toast.makeText(myact, "Edited", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(myact, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            app.appId = ""
                            if (appointmentViewModel.upcreateAppoint(app)) {
                                Toast.makeText(
                                    myact,
                                    "Data inserted successfully",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {
                                Toast.makeText(
                                    myact, "Failed", Toast.LENGTH_LONG
                                ).show()
                            }

                        }
                    }).setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}