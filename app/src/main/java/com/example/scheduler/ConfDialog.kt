package com.example.scheduler


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ConfDialog(val AppId: String) : DialogFragment() {


    lateinit var database: FirebaseDatabase
    lateinit var dbref: DatabaseReference
    lateinit var myauth: FirebaseAuth


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        database = FirebaseDatabase.getInstance()
        myauth = FirebaseAuth.getInstance()
        dbref = database.getReference("appointments")

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("delete appointement")
                .setPositiveButton("Delete",
                    DialogInterface.OnClickListener { dialog, id ->
                        dbref.child(AppId).removeValue();
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