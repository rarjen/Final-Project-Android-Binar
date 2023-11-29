package com.example.finalprojectbinar.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabLayoutKursusPopulerAdapter(fragment:Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =3;
    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}