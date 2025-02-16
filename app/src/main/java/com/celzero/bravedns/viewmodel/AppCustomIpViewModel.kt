/*
 * Copyright 2022 RethinkDNS and its authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.celzero.bravedns.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.celzero.bravedns.automaton.IpRulesManager.UID_EVERYBODY
import com.celzero.bravedns.database.CustomIpDao
import com.celzero.bravedns.util.Constants

class AppCustomIpViewModel(private val customIpDao: CustomIpDao) : ViewModel() {

    private var filteredList: MutableLiveData<String> = MutableLiveData()
    private var uid: Int = UID_EVERYBODY

    init {
        filteredList.value = ""
    }

    val customIpDetails = Transformations.switchMap(filteredList) { input ->
        if (input.isNullOrBlank()) {
            customIpDao.getAppWiseCustomIp(uid).toLiveData(pageSize = Constants.LIVEDATA_PAGE_SIZE)
        } else {
            customIpDao.getAppWiseCustomIp("%$input%", uid).toLiveData(Constants.LIVEDATA_PAGE_SIZE)
        }
    }

    fun appWiseIpRulesSize(uid: Int): LiveData<Int> {
        return customIpDao.getBlockedConnectionCountForUid(uid)
    }

    fun setFilter(filter: String) {
        filteredList.value = filter
    }

    fun setUid(i: Int) {
        this.uid = i
    }

}
