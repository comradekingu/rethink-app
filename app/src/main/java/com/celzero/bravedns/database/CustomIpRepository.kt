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
package com.celzero.bravedns.database

import androidx.lifecycle.LiveData


class CustomIpRepository(private val customIpDao: CustomIpDao) {

    suspend fun update(customIp: CustomIp) {
        customIpDao.update(customIp)
    }

    suspend fun clearFirewallRules(uid: Int) {
        customIpDao.clearIpRuleByUid(uid)
    }

    suspend fun insert(customIp: CustomIp) {
        customIpDao.insert(customIp)
    }

    suspend fun getIpRules(): List<CustomIp> {
        return customIpDao.getCustomIpRules()
    }

    suspend fun getCustomIpDetail(uid: Int, ipAddress: String): CustomIp? {
        return customIpDao.getCustomIpDetail(uid, ipAddress)
    }

    suspend fun deleteIPRulesForUID(uid: Int, ipAddress: String) {
        customIpDao.deleteIPRulesForUID(uid, ipAddress)
    }

    suspend fun deleteIpRule(customIp: CustomIp) {
        customIpDao.delete(customIp)
    }

    suspend fun deleteAllIPRulesUniversal() {
        customIpDao.deleteAllIPRulesUniversal()
    }

    suspend fun getBlockedConnectionsCount(): Int {
        return customIpDao.getBlockedConnectionsCount()
    }

    suspend fun getBlockedConnectionCountLiveData(): LiveData<Int> {
        return customIpDao.getBlockedConnectionCountLiveData()
    }
}
