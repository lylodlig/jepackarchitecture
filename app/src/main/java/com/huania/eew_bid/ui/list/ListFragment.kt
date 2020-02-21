package com.huania.eew_bid.ui.list


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.huania.eew_bid.R
import com.huania.eew_bid.base.BaseFragment
import com.huania.eew_bid.data.repository.Repository
import com.huania.eew_bid.databinding.FragmentListBinding
import com.huania.eew_bid.net.State
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.toast

class ListFragment : BaseFragment<FragmentListBinding>() {

    private val model: ListViewModel by viewModels { ListFactory(Repository()) }
    private var adapter: TestAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_list

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding?.let {
            it.refreshListener = SwipeRefreshLayout.OnRefreshListener {
                model.refresh()
            }
        }

        list.adapter = adapter
        swipe_refresh.setOnRefreshListener {
            model.refresh()
        }
    }


    override fun actionsOnViewInflate() {
        adapter = TestAdapter(context!!)
        model.result.observe(this, Observer {
            adapter?.submitList(it)
        })
        model.netState?.observe(this, Observer {
            when (it.state) {
                State.RUNNING -> {
                    inject(true)
                }
                State.SUCCESS -> {
                    inject()
                }
                State.FAILED -> {
                    inject()
                    context?.toast("${it.msg}")
                }
            }
        })
    }

    private fun inject(refresh: Boolean = false) {
        mBinding?.let {
            it.refreshState = refresh
        }
    }
}
