package com.example.project3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeightAdapter(
    private var weights: List<WeightEntry>,
    private val onEditClick: (WeightEntry) -> Unit,
    private val onDeleteClick: (WeightEntry) -> Unit
) : RecyclerView.Adapter<WeightAdapter.WeightViewHolder>() {

    class WeightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.textDate)
        val weightText: TextView = view.findViewById(R.id.textWeight)
        val editButton: ImageButton = view.findViewById(R.id.buttonEdit)
        val deleteButton: ImageButton = view.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weight, parent, false)
        return WeightViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        val weight = weights[position]
        holder.dateText.text = weight.date
        holder.weightText.text = String.format("%.1f lbs", weight.weight)

        holder.editButton.setOnClickListener { onEditClick(weight) }
        holder.deleteButton.setOnClickListener { onDeleteClick(weight) }
    }

    override fun getItemCount() = weights.size

    fun updateData(newWeights: List<WeightEntry>) {
        weights = newWeights
        notifyDataSetChanged()
    }
}

data class WeightEntry(
    val id: Long = 0,
    val userId: Long,
    val weight: Double,
    val date: String,
    val notes: String? = null
)