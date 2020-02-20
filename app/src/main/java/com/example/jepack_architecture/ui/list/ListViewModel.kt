package com.example.jepack_architecture.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import com.example.jepack_architecture.base.safeLaunch
import com.example.jepack_architecture.data.entity.WanUserData
import com.example.jepack_architecture.data.repository.Repository
import com.example.jepack_architecture.data.repository.UserRepository
import com.example.jepack_architecture.net.NetworkState

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
