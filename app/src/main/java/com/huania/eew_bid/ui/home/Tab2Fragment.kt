package com.huania.eew_bid.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.huania.eew_bid.R
import kotlinx.android.synthetic.main.fragment_tab2.*

/**
 * A simple [Fragment] subclass.
 */
class Tab2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycle.addObserver(MapLifeCycleObserver(mapView))
    }

    companion object {
        fun newInstance() = Tab2Fragment()
    }
}
