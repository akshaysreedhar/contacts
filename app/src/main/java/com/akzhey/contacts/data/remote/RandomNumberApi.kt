package com.akzhey.contacts.data.remote

import retrofit2.http.GET

interface RandomNumberApi {
    @GET("Phone/Generate?CountryCode=IN&Quantity=1")
    suspend fun getPhoneNumber(): List<String>
}