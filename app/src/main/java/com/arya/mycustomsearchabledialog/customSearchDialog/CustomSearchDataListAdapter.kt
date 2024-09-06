package com.arya.mycustomsearchabledialog.customSearchDialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arya.mycustomsearchabledialog.R
import com.arya.mycustomsearchabledialog.databinding.ItemSelectionBinding

class CustomSearchDataListAdapter : RecyclerView.Adapter<CustomSearchDataListAdapter.ViewHolder>() {

    var dataList : ArrayList<String> = ArrayList()

    var selectedData : String? = null

    lateinit var onItemClickCallback: OnItemClickCallback

    inner class ViewHolder (val binding: ItemSelectionBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(dataList[position]){
                if (selectedData.equals(dataList[position])){
                    binding.btnName.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green))
                }else{
                    binding.btnName.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.transparent))
                }
                binding.btnName.text = this
                binding.btnName.setOnClickListener {
                    onItemClickCallback.onClick(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClickCallback {
        fun onClick(data: String)
    }
}