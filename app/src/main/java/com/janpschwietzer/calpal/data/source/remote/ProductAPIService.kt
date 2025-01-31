package com.janpschwietzer.calpal.data.source.remote

import com.janpschwietzer.calpal.data.model.OpenFoodFactsResponseModel
import com.janpschwietzer.calpal.data.model.OpenFoodFactsSearchResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("api/v0/product/{barcode}.json")
    suspend fun getProduct(@Path("barcode") barcode: Long): OpenFoodFactsResponseModel?

    @Deprecated(
        message = "Die suche über OpenFoodFacts ist zu langsam. Daher sollte dies nicht genutzt werden!",
        level = DeprecationLevel.ERROR
    )
    @GET("cgi/search.pl?search_simple=1&json=1")
    suspend fun searchProducts(
        @Query("search_terms") query: String,
        @Query("page_size") pageSize: Int = 10,
        @Query("page") page: Int = 1
    ): OpenFoodFactsSearchResponseModel?
}
