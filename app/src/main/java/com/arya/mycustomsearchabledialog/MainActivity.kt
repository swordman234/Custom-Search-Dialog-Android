package com.arya.mycustomsearchabledialog

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arya.mycustomsearchabledialog.customSearchDialog.CustomSearchDialog
import com.arya.mycustomsearchabledialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val dummyData = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")

    private var selectedData = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnName.setOnClickListener {
            searchableDialog()
        }

    }

    private fun searchableDialog() {
        val builder = CustomSearchDialog.Builder()
            .setDataList(dummyData.toTypedArray(), selectedData)
            .setOnClick { which ->
                if (which != null){
                    selectedData = which
                    binding.btnName.setText(dummyData[which])
                }
            }
            .build()
        builder.show(supportFragmentManager, null)
    }
}
