/*
 * Copyright 2021 RethinkDNS and its authors
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

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.celzero.bravedns.automaton.DomainRulesManager
import com.celzero.bravedns.database.CustomDomainDAO
import com.celzero.bravedns.util.Constants.Companion.LIVEDATA_PAGE_SIZE

class CustomDomainViewModel(private val customDomainDAO: CustomDomainDAO) : ViewModel() {

    private var filteredList: MutableLiveData<String> = MutableLiveData()
    private var status: DomainRulesManager.DomainStatus = DomainRulesManager.DomainStatus.NONE

    init {
        filteredList.value = ""
    }

    val customDomainList = Transformations.switchMap(filteredList) { input ->
        when (status) {
            DomainRulesManager.DomainStatus.NONE -> {
                customDomainDAO.getAllDomainsLiveData("%$input%").toLiveData(
                    pageSize = LIVEDATA_PAGE_SIZE)
            }
            DomainRulesManager.DomainStatus.WHITELIST -> {
                customDomainDAO.getWhitelistedDomains("%$input%", status.id).toLiveData(
                    pageSize = LIVEDATA_PAGE_SIZE)
            }
            DomainRulesManager.DomainStatus.BLOCK -> {
                customDomainDAO.getBlockedDomains("%$input%", status.id).toLiveData(
                    LIVEDATA_PAGE_SIZE)
            }
        }
    }

    fun setFilter(filter: String, status: DomainRulesManager.DomainStatus) {
        this.status = status
        filteredList.value = filter
    }
}
