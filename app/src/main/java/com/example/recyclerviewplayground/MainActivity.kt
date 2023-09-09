package com.example.recyclerviewplayground

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewplayground.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private var dragHelper : ItemTouchHelper ? = null
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setRecyclerView()
    }

    private fun setRecyclerView(){
        val adapter = PlayGroundRecyclerViewAdapter(this)
        binding.homeRv.adapter = adapter
        adapter.submitList(MockModel.list)

        dragHelper(adapter)
    }

    private fun dragHelper(adapter: PlayGroundRecyclerViewAdapter){
        dragHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
           override fun onMove(
               recyclerView: RecyclerView,
               viewHolder: RecyclerView.ViewHolder,
               target: RecyclerView.ViewHolder
           ): Boolean {
               viewHolder.itemView.elevation = 16F

               val from = viewHolder.adapterPosition
               val to = target.adapterPosition

               Collections.swap(MockModel.list , from , to)
               adapter.notifyItemMoved(from , to)
               return true
           }

           override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit
       })

        dragHelper?.attachToRecyclerView(binding.homeRv)
    }

    fun startDragging(holder: RecyclerView.ViewHolder) {
        dragHelper?.startDrag(holder)
    }

}