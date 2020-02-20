package com.example.jepack_architecture.ui.activity

import android.os.Bundle
import com.example.jepack_architecture.R
import com.example.jepack_architecture.base.BaseActivity
import com.example.jepack_architecture.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun getLayoutId(): Int=R.layout.activity_home

    override fun initActivity(savedInstanceState: Bundle?) {
    }

}
