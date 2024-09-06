package com.arya.mycustomsearchabledialog.customSearchDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arya.mycustomsearchabledialog.databinding.DialogFragmentCustomSearchBinding

class CustomSearchDialog private constructor(builder: Builder) : DialogFragment() {

    private lateinit var binding: DialogFragmentCustomSearchBinding
    private var fullDataList : Array<String>? = null
    private var dataList : Array<String>? = null
    private var onClick: (index : Int?) -> Unit = {}
    private var selectedIndex : Int = -1

    private var dataAdapter = CustomSearchDataListAdapter()

    class Builder{
        private var dataList : Array<String>? = null
        private var selectedIndex : Int = -1
        private var onClick: (index : Int?) -> Unit = {}

        fun setDataList(data : Array<String>, selectedIndex : Int?) = apply {
            this.dataList = data
            if (selectedIndex != null){
                this.selectedIndex = selectedIndex
            }
        }
        fun setOnClick(onClick : (index : Int?) -> Unit) = apply { this.onClick = onClick }

        fun getDataList() = dataList
        fun getSelectedIndex() = selectedIndex
        fun getOnClick() = onClick
        fun build() = CustomSearchDialog(this)
    }

    init {
       builder.getDataList()?.let {
           fullDataList = it
           dataList = it
           dataAdapter.dataList.addAll(it)
           if (builder.getSelectedIndex() != -1){
               builder.getSelectedIndex().let{index ->
                   dataAdapter.selectedData = fullDataList!![index]
                   selectedIndex = index
               }
           }
       }
        onClick = builder.getOnClick()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(1000, 1000)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentCustomSearchBinding.inflate(layoutInflater,container,false)
        binding.etSearch.doAfterTextChanged {
            if (it.toString().isNotEmpty()){
                searchData(keyword = binding.etSearch.text.toString())
            }else{
                setDataList(fullDataList)
            }
        }

        binding.btnClearSearch.setOnClickListener {
            clearSearch()
        }

        binding.rvBranch.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = dataAdapter.apply {
                onItemClickCallback = object : CustomSearchDataListAdapter.OnItemClickCallback{
                    override fun onClick(data: String) {
                        this@CustomSearchDialog.onClick(fullDataList?.indexOf(data))
                        this@CustomSearchDialog.dismiss()
                    }
                }
                scrollToPosition(selectedIndex)
            }
        }
        return binding.root
    }

    private fun searchData(keyword : String){
        val result = ArrayList<String>()
        fullDataList?.forEach{
            if (it.contains(keyword, ignoreCase = true) && !result.contains(it)){
                result.add(it)
                return@forEach
            }
        }
        setDataList(result.toTypedArray())
    }

    private fun setDataList(data : Array<String>?){
        dataAdapter.dataList.clear()
        if (data != null) {
            dataAdapter.dataList.addAll(data)
        }
        dataAdapter.notifyDataSetChanged()
    }

    private fun clearSearch(){
        binding.etSearch.text = null
        binding.etSearch.clearFocus()
        setDataList(fullDataList)
    }
}