package com.example.recyclerviewplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewplayground.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
    }

    fun setRecyclerView(){
        val adapter = PlayGroundRecyclerViewAdapter()
        binding.homeRv.adapter = adapter
        adapter.submitList(MockModel.DataList)
    }

}