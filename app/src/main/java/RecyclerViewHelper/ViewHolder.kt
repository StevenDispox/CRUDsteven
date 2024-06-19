package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import steven.flores.stevenpalacios.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.txtTicketCard)
    val imgEdit: ImageView = view.findViewById(R.id.imgEdit)
    val imgDelete: ImageView = view.findViewById(R.id.imgDelete)
}