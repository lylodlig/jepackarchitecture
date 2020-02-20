package com.example.jepack_architecture.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2

import com.example.jepack_architecture.R
import com.example.jepack_architecture.base.BaseFragment
import com.example.jepack_architecture.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = HomeAdapter(this)
        viewPager.isSaveEnabled = false
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navView.selectedItemId = when (position) {
                    0 -> R.id.navigation_home
                    1 -> R.id.navigation_notice
                    else -> R.id.navigation_upload
                }
            }
        })

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    viewPager.setCurrentItem(0, true)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_notice -> {
                    viewPager.setCurrentItem(1, true)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_upload -> {
                    viewPager.setCurrentItem(2, true)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }


}
