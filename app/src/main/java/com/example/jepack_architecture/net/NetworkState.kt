/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jepack_architecture.net

import com.example.jepack_architecture.base.ERROR_CODE_NORM

enum class State {
    RUNNING, SUCCESS, FAILED
}

data class NetworkState(
    val state: State,
    val msg: String? = null,
    val code: Int? = null
) {
    companion object {
        val LOADED = NetworkState(State.SUCCESS)
        val LOADING = NetworkState(State.RUNNING)
        fun error(msg: String?, code: Int = ERROR_CODE_NORM) = NetworkState(State.FAILED, msg ?: "unknown error", code)
    }
}