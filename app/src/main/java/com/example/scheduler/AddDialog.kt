package com.example.scheduler

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.scheduler.Models.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddDialog(val edit: Boolean, val EditAppoint: Appointment?) : DialogFragment() {
    lateinit var date: EditText
    lateinit var time: EditText
    lateinit var descr: EditText
    lateinit var phone: EditText
    lateinit var database: FirebaseDatabase
    lateinit var dbref: DatabaseReference
    lateinit var myauth: FirebaseAuth

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myact ->
            val builder = AlertDialog.Builder(myact)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val dialogView: View = inflater.inflate(R.layout.add_dialog, null)

            date = dialogView.findViewById(R.id.editTextDate)
            time = dialogView.findViewById(R.id.editTextTime)
            descr = dialogView.findViewById(R.id.desc_field)
            phone = dialogView.findViewById(R.id.editTextNumber)


            var title_text = "Add"


            database = FirebaseDatabase.getInstance()
            myauth = FirebaseAuth.getInstance()
            dbref = database.getReference("appointments")



            if (edit) {
                val title = dialogView.findViewById<TextView>(R.id.title)
                date.setText(EditAppoint?.date)
                time.setText(EditAppoint?.time)
                descr.setText(EditAppoint?.description)
                phone.setText(EditAppoint?.num)
                title_text = "Edit"
                title.text = title_text

            }


            builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton(title_text,
                    DialogInterface.OnClickListener { dialog, id ->
                        if (edit) {
                            val newApp = Appointment(
                                EditAppoint?.appId!!, myauth.currentUser?.uid,
                                date.text.toString(),
                                time.text.toString(),
                                descr.text.toString(),
                                phone.text.toString()
                            )
                            val row = dbref.child(EditAppoint?.appId!!).setValue(newApp)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        myact,
                                        "Data Updated successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                        } else {
                            val appId = dbref.push().key
                            val App = Appointment(
                                appId!!,
                                myauth.currentUser?.uid,
                                date.text.toString(),
                                time.text.toString(),
                                descr.text.toString(),
                                phone.text.toString()
                            )

                            dbref.child(appId!!).setValue(App)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        myact,
                                        "Data inserted successfully",
                                        Toast.LENGTH_LONG
                                    ).show()

//                                    date.text.clear()
//                                    time.text.clear()
//                                    phone.text.clear()
//                                    descr.text.clear()


                                }.addOnFailureListener { err ->
                                    Toast.makeText(
                                        myact,
                                        "Error ${err.message}",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }

                        }
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}