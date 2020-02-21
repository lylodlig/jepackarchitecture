package com.huania.eew_bid.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.huania.eew_bid.R
import com.huania.eew_bid.base.BaseFragment
import com.huania.eew_bid.data.repository.UserRepository
import com.huania.eew_bid.databinding.LoginFragmentBinding
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
