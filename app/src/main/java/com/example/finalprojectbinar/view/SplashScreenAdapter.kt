package com.example.finalprojectbinar.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.databinding.SplashItemBinding

class SplashScreenAdapter(private val splashScreen: List<SplashScreen>):
    RecyclerView.Adapter<SplashScreenAdapter.SplashScreenHolder>(){

    inner class SplashScreenHolder(binding: SplashItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val textDesk = binding.tvsplashDesk
        private val image = binding.ivsplashImage

        fun bind(splashScreen: SplashScreen){
            textDesk.text = splashScreen.desk
            image.setImageResource(splashScreen.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplashScreenHolder {
        val binding =   SplashItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SplashScreenHolder(binding)
    }

    override fun getItemCount(): Int = splashScreen.size

    override fun onBindViewHolder(holder: SplashScreenHolder, position: Int) {
        holder.bind(splashScreen[position])
    }
}