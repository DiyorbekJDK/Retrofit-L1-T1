package com.example.retrofit_l1_t1.model

data class UserResponse(
    val `data`: List<Data>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)