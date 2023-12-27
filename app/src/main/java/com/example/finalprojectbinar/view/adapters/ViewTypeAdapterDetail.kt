import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.databinding.LayoutChapterBinding
import com.example.finalprojectbinar.databinding.LayoutModuleBinding
import com.example.finalprojectbinar.model.ClassModule
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.DataCourses
import com.example.finalprojectbinar.model.Module

class ViewTypeAdapterDetail(
    private val items: List<Any>,
    private var clickListener: ((String) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CHAPTER -> {
                val binding = LayoutChapterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ChapterViewHolder(binding)
            }
            VIEW_TYPE_MODULE -> {
                val binding = LayoutModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ModuleViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ClassModule -> VIEW_TYPE_CHAPTER
            is Module -> VIEW_TYPE_MODULE
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_CHAPTER -> {
                val headerHolder = holder as ChapterViewHolder
                headerHolder.bind(items[position] as ClassModule)
            }
            VIEW_TYPE_MODULE -> {
                val moduleHolder = holder as ModuleViewHolder
                moduleHolder.bind(items[position] as Module)
            }
        }
    }

    inner class ChapterViewHolder(private var binding: LayoutChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(classModule: ClassModule) {
            binding.tvChapter.text = classModule.chapter
            binding.tvDurasi.text = "${classModule.estimation} Menit"
        }
    }

    inner class ModuleViewHolder(private var binding: LayoutModuleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clModule.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val clickedItem = items[position]
                    if (clickedItem is Module) {
                        clickListener?.invoke(clickedItem.chapterModuleUuid)
                    }
                }
            }
        }

        fun bind(moduleList: Module) {
            binding.moduleName.text = moduleList.title
        }
    }

    companion object {
        private const val VIEW_TYPE_CHAPTER = 0
        private const val VIEW_TYPE_MODULE = 1
    }
}
