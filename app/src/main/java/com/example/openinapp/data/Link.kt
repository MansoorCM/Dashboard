package com.example.openinapp.data

import com.squareup.moshi.Json

data class Link(
    @Json(name = "url_id") val urlId: Int = 0,
    @Json(name = "web_link") val webLink: String = "",
    @Json(name = "smart_link") val smartLink: String = "",
    val title: String = "",
    @Json(name = "total_clicks") val totalClicks: Int = 0,
    @Json(name = "original_image") val originalImage: String = "",
    val thumbnail: String? = null,
    @Json(name = "times_ago") val timesAgo: String = "",
    @Json(name = "created_at") val createdAt: String = "",
    @Json(name = "domain_id") val domainId: String = "",
    @Json(name = "url_prefix") val urlPrefix: String? = null,
    @Json(name = "url_suffix") val urlSuffix: String = "",
    val app: String = "",
    @Json(name = "is_favourite") val isFavourite: Boolean = false
)
