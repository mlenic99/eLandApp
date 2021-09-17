package hr.ferit.mlenic.eland.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.mlenic.eland.R

class LandViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val refNum: TextView = itemView.findViewById(R.id.tvLandID)
    val landName: TextView = itemView.findViewById(R.id.tvLandName)
    val landDate: TextView = itemView.findViewById(R.id.tvDateStamp)
    val landDelete: ImageButton = itemView.findViewById(R.id.ibDelete)
}