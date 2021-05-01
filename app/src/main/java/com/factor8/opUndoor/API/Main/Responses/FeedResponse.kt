package com.factor8.opUndoor.API.Main.Responses

data class FeedResponse(
    val company: List<Company>,
    val friends: List<Friend>,
    val groups: List<Group>,
    val network: List<Network>,
    val news: News,
    val self: Self,
    val status: Int,
    val userstory: List<Any>
) {
    data class Company(
        val created_at: Any,
        val display_picture: String,
        val id: String,
        val name: String,
        val network: String,
        val profile_picture: String,
        val storypicture: List<Any>,
        val updated_at: Any,
        val website: String
    )

    data class Friend(
        val coverpic: String,
        val created_at: String,
        val current_company: String,
        val email: String,
        val experience: String,
        val f_name: String,
        val friendid: String,
        val id: String,
        val is_verified: String,
        val l_name: String,
        val network: String,
        val password: String,
        val picture: String,
        val privacy: String,
        val profession: String,
        val remember_token: Any,
        val storypicture: List<Any>,
        val updated_at: String,
        val userid: String,
        val username: String
    )

    data class Group(
        val adminname: String,
        val adminpicture: String,
        val created_at: String,
        val groupadminid: String,
        val grouppictures: List<Any>,
        val grouptitle: String,
        val groupuserid: String,
        val id: String,
        val no_of_member: Int,
        val updated_at: String
    )

    data class Network(
        val created_at: Any,
        val display_picture: String,
        val id: String,
        val name: String,
        val network: String,
        val profile_picture: String,
        val storypicture: List<Any>,
        val updated_at: Any,
        val website: String
    )

    data class News(
        val business: List<Busines>,
        val economictimes: List<Economictime>,
        val science: List<Science>,
        val technology: List<Technology>,
        val timesofindia: List<Timesofindia>
    ) {
        data class Busines(
            val company: String,
            val description: String,
            val guid: String,
            val id: String,
            val image: String,
            val pubDate: String,
            val title: String
        )

        data class Economictime(
            val company: String,
            val description: String,
            val guid: String,
            val id: String,
            val image: String,
            val pubDate: String,
            val title: String
        )

        data class Science(
            val company: String,
            val description: String,
            val guid: String,
            val id: String,
            val image: String,
            val pubDate: String,
            val title: String
        )

        data class Technology(
            val company: String,
            val description: Any,
            val guid: String,
            val id: String,
            val image: Any,
            val pubDate: String,
            val title: String
        )

        data class Timesofindia(
            val company: String,
            val description: String,
            val guid: String,
            val id: String,
            val image: String,
            val pubDate: String,
            val title: String
        )
    }

    data class Self(
        val coverpic: String,
        val created_at: String,
        val current_company: String,
        val email: String,
        val experience: String,
        val f_name: String,
        val id: String,
        val is_verified: String,
        val l_name: String,
        val network: String,
        val password: String,
        val picture: String,
        val privacy: String,
        val profession: String,
        val remember_token: Any,
        val storypicture: List<Any>,
        val updated_at: String,
        val username: String
    )
}