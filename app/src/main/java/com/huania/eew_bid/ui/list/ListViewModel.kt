package com.huania.eew_bid.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import com.huania.eew_bid.base.safeLaunch
import com.huania.eew_bid.data.entity.WanUserData
import com.huania.eew_bid.data.repository.Repository
import com.huania.eew_bid.data.repository.UserRepository
import com.huania.eew_bid.net.NetworkState

class ListViewModel(repository: Repository) : ViewModel() {
    var netState: LiveData<NetworkState>? = null
    val result = ListDataSourceFactory(repository).apply {
        netState = sourceLiveData.switchMap {
            it.networkState
        }
    }.toLiveData(
        pageSize = 20
    )

    fun refresh() {
        result.value?.dataSource?.invalidate()
    }

}
