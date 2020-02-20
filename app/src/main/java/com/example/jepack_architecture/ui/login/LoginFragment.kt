package com.example.jepack_architecture.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.jepack_architecture.R
import com.example.jepack_architecture.base.BaseFragment
import com.example.jepack_architecture.data.repository.UserRepository
import com.example.jepack_architecture.databinding.LoginFragmentBinding
import kotlinx.android.synthetic.main.login_fragment.*
import org.jetbrains.anko.toast


class LoginFragment : BaseFragment<LoginFragmentBinding>() {

    private val viewModel: LoginViewModel by viewModels {
        LoginFactory(UserRepository())
    }

    override fun getLayoutId(): Int = R.layout.login_fragment

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding?.model = viewModel
        mBinding?.clickListener = clickListener

    }

    private val clickListener = View.OnClickListener { v ->
        when (v) {
            btnLogin -> {
                viewModel.login({
                    context?.toast("登录成功")
                }, { context?.toast(it) })
            }
        }
    }
}
