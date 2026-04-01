package com.calendar.widget.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Retrofit service for fetching iCal calendar data from URLs.
 */
interface IcalService {
    
    @GET
    suspend fun fetchIcalCalendar(@Url url: String): Response<ResponseBody>
}
