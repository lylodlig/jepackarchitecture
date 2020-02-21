package com.huania.eew_bid.base

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * 绑定 SwipeRefreshLayout 颜色，刷新状态，监听事件
 */
@BindingAdapter(
    value = ["bind:refreshColor", "bind:refreshState", "bind:refreshListener"],
    requireAll = false
)
fun bindRefreshColor(
    refreshLayout: SwipeRefreshLayout,
    color: Int?,
    refreshState: Boolean,
    listener: SwipeRefreshLayout.OnRefreshListener
) {
    if (color != null) {
        refreshLayout.setColorSchemeResources(color)
    }
    refreshLayout.isRefreshing = refreshState
    refreshLayout.setOnRefreshListener(listener)
}
