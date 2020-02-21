package com.huania.eew_bid.ui.list

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.huania.eew_bid.base.ERROR_CODE_INIT
import com.huania.eew_bid.base.IOScope
import com.huania.eew_bid.base.safeLaunch
import com.huania.eew_bid.data.db.AppDataBase
import com.huania.eew_bid.data.db.HomeArticleDetail
import com.huania.eew_bid.data.repository.Repository
import com.huania.eew_bid.databinding.PagingItemTestBinding
import com.huania.eew_bid.net.NetworkState
import com.huania.eew_bid.utils.Logger
import kotlinx.coroutines.CoroutineScope

class ListFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}

class ListDataSourceFactory(
    private val repository: Repository

) : DataSource.Factory<Int, HomeArticleDetail>() {
    val sourceLiveData = MutableLiveData<TestDataSource>()
    override fun create(): DataSource<Int, HomeArticleDetail> {
        val source = TestDataSource(repository)
        sourceLiveData.postValue(source)
        return source
    }
}

class TestDataSource(
    private val repository: Repository
) : PageKeyedDataSource<Int, HomeArticleDetail>(),
    CoroutineScope by IOScope() {

    // keep a function reference for the retry event
    // 重试函数,无参返回值是Any
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, HomeArticleDetail>
    ) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, HomeArticleDetail>
    ) {
        safeLaunch({
            Logger.d("loadAfter ${params.requestedLoadSize}   ${params.key}    ${Looper.myLooper() != Looper.getMainLooper()}")
            retry = null
            val data = repository.loadHomeData(params.key)
            if (data.errorCode == 0) {
//                data.data?.datas?.let {
//                    AppDataBase.getInstance().homeDao().cacheHomeArticles(it)
//                }
                callback.onResult(arrayListOf<HomeArticleDetail>().apply {
                    addAll(data?.data?.datas)
                }, params.key + 1)
            } else {
                networkState.postValue(NetworkState.error(data.errorMsg))
            }
        }, {
            networkState.postValue(NetworkState.error(it.message))
        })
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, HomeArticleDetail>
    ) {
        safeLaunch({
            networkState.postValue(NetworkState.LOADING)
            initialLoad.postValue(NetworkState.LOADING)
            Logger.d("loadInitial  ${params.requestedLoadSize}   ${Looper.myLooper() != Looper.getMainLooper()}")
            val data = repository.loadHomeData(0)
            if (data.errorCode == 0) {
                AppDataBase.getInstance().homeDao().clearHomeCache()
                data.data?.datas?.let {
                    AppDataBase.getInstance().homeDao().cacheHomeArticles(it)
                }

                networkState.postValue(NetworkState.LOADED)
                initialLoad.postValue(NetworkState.LOADED)
                callback.onResult(arrayListOf<HomeArticleDetail>().apply {
                    addAll(data?.data?.datas)
                }, null, 1)
                retry = null
            } else {
                networkState.postValue(NetworkState.error(data.errorMsg, ERROR_CODE_INIT))
                retry = {
                    loadInitial(params, callback)
                }
            }
        }, {
            networkState.postValue(NetworkState.error(it.message, ERROR_CODE_INIT))
            retry = {
                loadInitial(params, callback)
            }
        })
    }
}


class TestAdapter constructor(val context: Context) :
    PagedListAdapter<HomeArticleDetail, TestAdapter.ViewHolder>(TestDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PagingItemTestBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent
                , false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoe = getItem(position)
        holder.apply {
            bind(shoe!!)
            itemView.tag = shoe
        }
    }


    class ViewHolder(private val binding: PagingItemTestBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeArticleDetail) {
            binding.apply {
                this.info = item
                executePendingBindings()
            }
        }
    }
}

class TestDiffCallback: DiffUtil.ItemCallback<HomeArticleDetail>() {
    //是否是同一对象
    override fun areItemsTheSame(oldItem: HomeArticleDetail, newItem: HomeArticleDetail): Boolean {
        return oldItem.id == newItem.id
    }
    // 内容是否一致，否则会重回
    override fun areContentsTheSame(oldItem: HomeArticleDetail, newItem: HomeArticleDetail): Boolean {
        return oldItem == newItem
    }
}
