package com.example.jepack_architecture.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.jepack_architecture.R
import com.example.jepack_architecture.base.BaseFragment
import com.example.jepack_architecture.databinding.FragmentTab1Binding

/**
 * A simple [Fragment] subclass.
 */
class Tab1Fragment : BaseFragment<FragmentTab1Binding>() {
    override fun getLayoutId(): Int = R.layout.fragment_tab1

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding?.clickListener = onClickListener
    }

    private val onClickListener = View.OnClickListener {
        mNavController?.navigate(R.id.action_homeFragment_to_listFragment)
    }

    companion object {
        fun newInstance() = Tab1Fragment()
    }
}
