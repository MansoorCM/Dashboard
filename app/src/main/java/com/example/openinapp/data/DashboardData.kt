package com.example.openinapp.data

import com.squareup.moshi.Json

data class DashboardData(
    @Json(name = "recent_links") val recentLinks: List<Link>,
    @Json(name = "top_links") val topLinks: List<Link>,
    @Json(name = "favourite_links") val favouriteLinks: List<Link>,
    @Json(name = "overall_url_chart") val overallUrlChart: Map<String, Int>
)
