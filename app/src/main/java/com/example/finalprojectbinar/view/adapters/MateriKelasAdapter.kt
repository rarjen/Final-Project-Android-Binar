package com.example.finalprojectbinar.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.ItemChapterKelasBinding
import com.example.finalprojectbinar.databinding.ItemMateriKelasBinding
import com.example.finalprojectbinar.model.ClassModule
import com.example.finalprojectbinar.model.SilabusCourse

class MateriKelasAdapter(private val modules: List<ClassModule>?): RecyclerView.Adapter<MateriKelasAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChapterKelasBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (modules?.size != null) modules?.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder).bind(modules?.get(position)!!)
    }

    inner class ViewHolder(private var binding: ItemChapterKelasBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(classModule: ClassModule){
            binding.tvChapterTitle.text=classModule.chapter
            binding.tvDurasiChapter.text = "${classModule.estimation.toString()} Menit"
            val listModule = mutableListOf<SilabusCourse>()
            classModule.module.forEachIndexed { index, module ->
                listModule.add(SilabusCourse((index+1).toString(),module.title,R.drawable.undone_play))
            }
            listModule.forEach {
                val inflater = LayoutInflater.from(binding.llListSilabusCourse.context)
                val itemSilabusCourseBinding = ItemMateriKelasBinding.inflate(LayoutInflater.from(binding.llListSilabusCourse.context))
                itemSilabusCourseBinding.ivStatusMateri.setImageResource(it.statusImageId)
                itemSilabusCourseBinding.tvMateriTitle.text=it.title
                itemSilabusCourseBinding.tvMateriNumber.text=it.number

                binding.llListSilabusCourse.addView(itemSilabusCourseBinding.root)
            }

        }
    }
}