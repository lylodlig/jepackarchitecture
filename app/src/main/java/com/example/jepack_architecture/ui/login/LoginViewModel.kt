package com.example.jepack_architecture.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jepack_architecture.base.safeLaunch
import com.example.jepack_architecture.data.entity.WanUserData
import com.example.jepack_architecture.data.repository.UserRepository

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
