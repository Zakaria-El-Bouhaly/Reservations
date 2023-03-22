package com.example.scheduler

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduler.Models.Appointment
import com.example.scheduler.helpers.DateTimeHelper

class ListAdapter(private val ctx: HomeActivity, private val data: ArrayList<Appointment>) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textView_datetime: TextView = row.findViewById<TextView>(R.id.time_date)
        val textView_description: TextView = row.findViewById<TextView>(R.id.description)
        val textView_number: TextView = row.findViewById<TextView>(R.id.number)
        val editbtn = row.findViewById<Button>(R.id.editbtn)
        val deletebtn = row.findViewById<Button>(R.id.deletebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(layout)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val theAppointment = data.get(position)

        val fullDateTime =
            "${DateTimeHelper.getFormattedDate(theAppointment.date)}  ${
                DateTimeHelper.getHourMinute(
                    theAppointment.time
                )
            }"
        holder.textView_datetime.text = fullDateTime

        holder.textView_description.text = data.get(position).description
        holder.textView_number.text = data.get(position).num
        holder.editbtn.setOnClickListener {
            val EditDialog = AddDialog(true, theAppointment)
            EditDialog.show(ctx.supportFragmentManager, null)
        }
        holder.deletebtn.setOnClickListener {
            val DelDialog = ConfDialog(theAppointment.appId);
            DelDialog.show(ctx.supportFragmentManager, null)
        }


    }

    override fun getItemCount(): Int = data.size

}