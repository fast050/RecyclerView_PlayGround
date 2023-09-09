package com.example.recyclerviewplayground

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewplayground.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.abs
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private var archiveColor: Int = 0
    private var deleteColor: Int = 0
    private var width: Int = 0
    private var height: Int = 0
    private var archiveIcon: Drawable? = null
    private var deleteIcon: Drawable? = null
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val displayMetrics: DisplayMetrics = resources.displayMetrics
         height = (displayMetrics.heightPixels / displayMetrics.density).toInt().dp
         width = (displayMetrics.widthPixels / displayMetrics.density).toInt().dp

        //drawables without theme attributes
         deleteIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_outline_delete_24 , null)
         archiveIcon =ResourcesCompat.getDrawable(resources , R.drawable.ic_outline_archive_24 , null)

         deleteColor = ResourcesCompat.getColor(resources ,android.R.color.holo_red_light , null)
         archiveColor = ResourcesCompat.getColor(resources ,android.R.color.holo_green_light , null)

        setRecyclerView()
    }

    private fun setRecyclerView(){
        val adapter = PlayGroundRecyclerViewAdapter()
        binding.homeRv.adapter = adapter
        adapter.submitList(MockModel.list)

        swipeHelper(adapter)
    }

    private fun swipeHelper(adapter: PlayGroundRecyclerViewAdapter){
       val swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
           override fun onMove(
               recyclerView: RecyclerView,
               viewHolder: RecyclerView.ViewHolder,
               target: RecyclerView.ViewHolder
           ): Boolean {
               return true
           }

           override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition
               MockModel.list.removeAt(position)
               adapter.notifyItemChanged(position)

               Snackbar.make(
                   findViewById(binding.homeRv.id),
                   if (direction == ItemTouchHelper.RIGHT) "Deleted" else "Archived",
                   Snackbar.LENGTH_SHORT
               ).show()
           }

           override fun onChildDraw(
               canvas: Canvas,
               recyclerView: RecyclerView,
               viewHolder: RecyclerView.ViewHolder,
               dX: Float,
               dY: Float,
               actionState: Int,
               isCurrentlyActive: Boolean
           ) {

               //1. Background color based upon direction swiped
               when {
                   abs(dX) < width / 3 -> canvas.drawColor(Color.GRAY)
                   dX > width / 3 -> canvas.drawColor(deleteColor)
                   else -> canvas.drawColor(archiveColor)
               }

               //2. Printing the icons
               val textMargin = resources.getDimension(R.dimen.text_margin)
                   .roundToInt()
               deleteIcon?.bounds = Rect(
                   textMargin,
                   viewHolder.itemView.top + textMargin + 8.dp,
                   textMargin + deleteIcon!!.intrinsicWidth,
                   viewHolder.itemView.top + deleteIcon!!.intrinsicHeight
                           + textMargin + 8.dp
               )
               archiveIcon?.bounds = Rect(
                   width - textMargin - archiveIcon!!.intrinsicWidth,
                   viewHolder.itemView.top + textMargin + 8.dp,
                   width - textMargin,
                   viewHolder.itemView.top + archiveIcon!!.intrinsicHeight
                           + textMargin + 8.dp
               )

               //3. Drawing icon based upon direction swiped
               if (dX > 0) deleteIcon?.draw(canvas) else archiveIcon?.draw(canvas)


               super.onChildDraw(
                   canvas,
                   recyclerView,
                   viewHolder,
                   dX,
                   dY,
                   actionState,
                   isCurrentlyActive
               )
           }
       })

        swipeHelper.attachToRecyclerView(binding.homeRv)
    }


    private val Int.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), resources.displayMetrics
        ).roundToInt()

}