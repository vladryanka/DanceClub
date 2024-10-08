package com.example.danceclub.API

import com.example.danceclub.database.account.Account
import com.example.danceclub.database.section.Section
import retrofit2.http.GET

interface ApiService {
    @GET("GET секций")
    suspend fun loadSectionsResponse(): List<Section>

    @GET("GET аккаунтов")
    suspend fun loadAccountsResponse(): List<Account>

    /*добавить пост запросы для записи*/

}