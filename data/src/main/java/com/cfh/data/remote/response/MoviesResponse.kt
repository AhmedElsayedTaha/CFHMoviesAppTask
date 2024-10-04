package com.cfh.data.remote.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MoviesResponse(
    @Expose
    @SerializedName("page")
    val page: Int?=null,
    @Expose
    @SerializedName("results")
    val moviesDtos: List<MoviesDto?>?=null,
    @Expose
    @SerializedName("total_pages")
    val totalPages: Int?=null,
    @Expose
    @SerializedName("total_results")
    val totalResults: Int?=null
)