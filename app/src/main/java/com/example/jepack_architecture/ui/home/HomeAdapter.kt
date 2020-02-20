package com.example.jepack_architecture.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabs: Map<Int, () -> Fragment> = mapOf(
        0 to { Tab1Fragment.newInstance() },
        1 to { Tab2Fragment.newInstance() },
        2 to { Tab3Fragment.newInstance() }
    )

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment =
        tabs[position]?.invoke() ?: throw IndexOutOfBoundsException()
}