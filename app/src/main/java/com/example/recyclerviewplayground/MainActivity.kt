package com.example.recyclerviewplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewplayground.databinding.ActivityMainBinding
import com.example.recyclerviewplayground.recycler_item_decoration.RecyclerViewItemDecoration

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
    }

    private fun setRecyclerView(){
        val adapter = PlayGroundRecyclerViewAdapter()
        binding.homeRv.adapter = adapter
        adapter.submitList(MockModel.DataList)

        binding.homeRv.setSpacingOfEditOrderAdapter()
    }

    private fun RecyclerView.setSpacingOfEditOrderAdapter() {
        RecyclerViewItemDecoration(
            resId = R.drawable.line_divider,
            context = this@MainActivity,
            size = resources.getDimension(R.dimen._30dp).toInt(),
            edgeEnabled = false
        ).apply {
            addItemDecoration(this)
        }
    }

}