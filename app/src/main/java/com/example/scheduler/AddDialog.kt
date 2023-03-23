package com.example.scheduler

import android.app.Dialog
import android.content.DialogInterface
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.scheduler.Models.Appointment
import com.example.scheduler.databinding.AddDialogBinding
import com.example.scheduler.helpers.DateTimeHelper
import com.example1.projectapp.viewModels.AppointmentViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch


class AddDialog(private val lifecycle: LifecycleCoroutineScope, private val edit: Boolean, private val EditAppoint: Appointment?) :
    DialogFragment() {
    private lateinit var date: EditText
    private lateinit var time: EditText
    private lateinit var descr: EditText
    private lateinit var phone: EditText

    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var binding: AddDialogBinding
    private var selectedDate: Long = 0
    private var selectedTime: Long = 0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myact ->
            val builder = MaterialAlertDialogBuilder(myact)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            selectedDate = MaterialDatePicker.todayInUtcMilliseconds()

            binding = AddDialogBinding.inflate(inflater)


            date = binding.editTextDate


            date.setOnClickListener {
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(selectedDate)
                        .build()

                datePicker.show(childFragmentManager, "date")
                datePicker.addOnPositiveButtonClickListener {
                    selectedDate = datePicker.selection ?: 0
                    date.setText(datePicker.headerText)
                }
            }

            time = binding.editTextTime
            time.setOnClickListener {
                val timepicker = MaterialTimePicker
                    .Builder()
                    .setTitleText("Select a time").setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(DateTimeHelper.getHour(selectedTime).toInt())
                    .setMinute(DateTimeHelper.getMinute(selectedTime).toInt())
                    .build()

                timepicker.show(childFragmentManager, "tag")
                timepicker.addOnPositiveButtonClickListener {

                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, timepicker.hour)
                    calendar.set(Calendar.MINUTE, timepicker.minute)
                    selectedTime = calendar.timeInMillis
                    val hourMin = DateTimeHelper.getHourMinute(selectedTime)
                    time.setText(hourMin)
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

                selectedDate = EditAppoint?.date ?: 0
                selectedTime = EditAppoint?.time ?: 0
                title_text = "Edit"
                title.text = title_text

                date.setText(DateTimeHelper.getFormattedDate(selectedDate))
                time.setText(DateTimeHelper.getHourMinute(selectedTime))
                descr.setText(EditAppoint?.description)
                phone.setText(EditAppoint?.num)
            }


            builder.setView(binding.root)
                // Add action buttons
                .setPositiveButton(
                    title_text,
                    DialogInterface.OnClickListener { dialog, id ->
                        val app = Appointment(
                            "",
                            "",
                            selectedDate,
                            selectedTime,
                            descr.text.toString(),
                            phone.text.toString()
                        )

                        if (edit) {
                            app.appId = EditAppoint?.appId!!
                            lifecycle.launch {
                                var result = appointmentViewModel.upcreateAppoint(app).singleOrNull()

                                Log.i("FLOOOOOOW", "idkman")
                                if (result == true) {
                                    Toast.makeText(myact, "Edited", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(myact, "Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            app.appId = ""
                            lifecycle.launch {
                                val result = appointmentViewModel.upcreateAppoint(app).single()
                                Log.i("NEEEEEEW", result.toString())
                                if (result) {
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
                        }
                    }).setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}