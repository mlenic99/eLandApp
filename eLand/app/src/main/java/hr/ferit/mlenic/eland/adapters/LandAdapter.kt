package hr.ferit.mlenic.eland.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.mlenic.eland.R
import hr.ferit.mlenic.eland.listeners.OnLandDeletedListener
import hr.ferit.mlenic.eland.listeners.OnLandSelectedListener
import hr.ferit.mlenic.eland.model.Land

class LandAdapter (
    val context: Context,
    private val landClickSelect: OnLandSelectedListener,
    private val landClickDelete: OnLandDeletedListener,
): RecyclerView.Adapter<LandViewHolder>() {

    private val allLands: MutableList<Land> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_land, parent, false)
        return LandViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LandViewHolder, position: Int) {
        holder.refNum.text = (allLands[position].refNum).toString()
        holder.landName.text = allLands[position].landName
        holder.landDate.text = allLands[position].timeStamp
        holder.landDelete.setOnClickListener { landClickDelete.onLandDeletedListener(allLands[position]) }
        holder.itemView.setOnClickListener { landClickSelect.onLandSelected(allLands[position]) }
    }

    override fun getItemCount(): Int {
        return allLands.size
    }

    fun updateList(newList: List<Land>){
        allLands.clear()
        allLands.addAll(newList)
        notifyDataSetChanged()
    }
}