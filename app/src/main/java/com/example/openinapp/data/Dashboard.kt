package com.example.openinapp.data

import com.squareup.moshi.Json

data class Dashboard(
    val status: Boolean,
    val statusCode: Int,
    val message: String,
    @Json(name = "support_whatsapp_number") val supportWhatsAppNumber: String,
    @Json(name = "extra_income") val extraIncome: Double = 0.0,
    @Json(name = "total_links") val totalLinks: Int = 0,
    @Json(name = "total_clicks") val totalClicks: Int = 0,
    @Json(name = "today_clicks") val todayClicks: Int = 0,
    @Json(name = "top_source") val topSource: String = "",
    @Json(name = "top_location") val topLocation: String = "",
    val startTime: String = "",
    @Json(name = "links_created_today") val linksCreatedToday: Int = 0,
    @Json(name = "applied_campaign") val appliedCampaign: Int = 0,
    val data: DashboardData
)
