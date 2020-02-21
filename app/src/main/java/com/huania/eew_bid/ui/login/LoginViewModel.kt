package com.huania.eew_bid.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huania.eew_bid.base.safeLaunch
import com.huania.eew_bid.data.entity.WanUserData
import com.huania.eew_bid.data.repository.UserRepository

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    var username = ""
    var password = ""

    fun login(success: (WanUserData?) -> Unit, fail: (String) -> Unit) {
        viewModelScope.safeLaunch({
            repository.login(username, password).let {
                if (it.body()?.errorCode == 0) {
                    success(it.body()?.data)
                } else {
                    fail(it.body()?.errorMsg ?: "登录失败")
                }
            }
        }, { fail("登录失败") })
    }

}
