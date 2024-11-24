import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.model.Categories
import com.example.wallet.model.Month

class CategoryAdapter(private val categoryList: List<Categories>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.iv_categories)
        val uangTextView: TextView = itemView.findViewById(R.id.tvuang)
        val waktuTextView: TextView = itemView.findViewById(R.id.tvwaktu)
        val kegiatanTextView: TextView = itemView.findViewById(R.id.tvkegiatan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.imageView.setImageResource(category.imageResId)
        holder.uangTextView.text = category.uang
        holder.waktuTextView.text = category.waktu
        holder.kegiatanTextView.text = category.kegiatan
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}
