package com.example.scheduler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduler.Models.Appointment

class ListAdapter(val ctx: HomeActivity, val data: ArrayList<Appointment>) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textView_datetime = row.findViewById<TextView>(R.id.time_date)
        val textView_description = row.findViewById<TextView>(R.id.description)
        val textView_number = row.findViewById<TextView>(R.id.number)
        val editbtn = row.findViewById<ImageView>(R.id.editbtn)
        val deletebtn = row.findViewById<ImageView>(R.id.deletebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val theAppointment = data.get(position)

        holder.textView_datetime.text = "${theAppointment.date}  ${theAppointment.time}"
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