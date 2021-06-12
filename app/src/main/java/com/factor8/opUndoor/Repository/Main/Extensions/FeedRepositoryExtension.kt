package com.factor8.opUndoor.Repository.Main.Extensions

import android.util.Log
import com.factor8.opUndoor.API.Main.Responses.*
import com.factor8.opUndoor.Models.*
import com.factor8.opUndoor.Repository.Main.FeedRepository

fun FeedRepository.createFeedUserStore(currentUser: FeedPropertiesSelf): FeedUserStore {
    return FeedUserStore(
        feed_user_store_pk = currentUser.id.toInt(),
        f_name = currentUser.f_name,
        l_name = currentUser.l_name,
        username = currentUser.username,
        email = currentUser.email,
        network = currentUser.network,
        picture = currentUser.picture,
        cover_picture = currentUser.coverpic,
        profession = currentUser.profession,
        work_experience = currentUser.experience,
        current_company = currentUser.current_company
    )
}

fun FeedRepository.createFeedFriendStore(
    feedUserStore: FeedUserStore,
    friendList: List<FeedPropertiesFriend>
): List<FeedFriendStore> {

    val feedFriendStore: ArrayList<FeedFriendStore> = ArrayList()
    for (friend in friendList) {
        feedFriendStore.add(
            FeedFriendStore(
                feed_friend_store_pk = friend.id.toInt(),
                feed_user_store_pk = feedUserStore.feed_user_store_pk,
                user_id = friend.userid.toInt(),
                friend_id = friend.friendid.toInt(),
                f_name = friend.f_name,
                l_name = friend.l_name,
                username = friend.username,
                email = friend.email,
                network = friend.network,
                profession = friend.profession,
                profile_picture = friend.picture,
                cover_picture = friend.coverpic,
                work_experience = friend.experience,
                current_company = friend.current_company
            )
        )
    }
    return feedFriendStore
}

fun FeedRepository.createFeedStoryPictureFriend(
    friendList: List<FeedPropertiesFriend>
): List<FeedStoryPicture> {
    val feedStoryPicture: ArrayList<FeedStoryPicture> = ArrayList()

    for (friend in friendList) {
        //Stories of this user
        for (story in friend.storypicture) {
            feedStoryPicture.add(
                FeedStoryPicture(
                    feed_story_picture_pk = story.id.toInt(),
                    feed_friend_store_pk = friend.id.toInt(),
                    user_id = story.userid.toInt(),
                    story_picture = story.storypicture,
                    duration = story.duaration,
                    type = story.type.toInt(),
                    time_stamp = story.created_at,
                    feed_user_store_pk = null
                )
            )
        }
    }
    return feedStoryPicture
}

fun FeedRepository.createFeedStoryPictureUser(user: FeedPropertiesSelf): List<FeedStoryPicture> {
    val userStories: ArrayList<FeedStoryPicture> = ArrayList()

    for (story in user.storypicture) {
        userStories.add(
            FeedStoryPicture(
                feed_story_picture_pk = story.id.toInt(),
                feed_user_store_pk = user.id.toInt(),
                user_id = story.userid.toInt(),
                story_picture = story.storypicture,
                duration = story.duaration,
                type = story.type.toInt(),
                time_stamp = story.created_at,
                feed_friend_store_pk = null

            )
        )
    }
    return userStories
}

fun FeedRepository.createFeedGroupStore(
    user: FeedUserStore,
    groups: List<FeedPropertiesGroup>
): List<FeedGroupStore> {
    val groupList: ArrayList<FeedGroupStore> = ArrayList()
    for (group in groups) {
        groupList.add(
            FeedGroupStore(
                feed_group_store_pk = group.id.toInt(),
                feed_user_store_pk = user.feed_user_store_pk,
                group_admin_id = group.groupadminid.toInt(),
                group_title = group.grouptitle,
                group_user_ids = group.groupuserid,
                number_of_members = group.no_of_member.toInt(),
                admin_profile_picture = group.adminpicture,
                admin_name = group.adminname
            )
        )
    }
    return groupList
}

fun FeedRepository.createFeedGroupStoryPicture(
    groups: List<FeedPropertiesGroup>
): List<FeedGroupStoryPicture> {
    val groupStories: ArrayList<FeedGroupStoryPicture> = ArrayList()
    for (group in groups) {
        for (story in group.grouppictures) {
            groupStories.add(
                FeedGroupStoryPicture(
                    feed_group_story_picture = story.id.toInt(),
                    feed_group_store_pk = group.id.toInt(),
                    group_id = story.groupid.toInt(),
                    user_id = story.userid.toInt(),
                    story_picture = story.groupstory,
                    duration = story.duaration,
                    type = story.type,
                    created_at = story.created_at
                )
            )
        }
    }
    return groupStories
}

fun FeedRepository.createFeedCompany(
    user: FeedUserStore,
    company: FeedPropertiesCompany
): FeedCompany {
    return FeedCompany(
        feed_company_pk = company.id.toInt(),
        company_name = company.name,
        network_name = company.network,
        display_picture = company.profile_picture,
        cover_picture = company.display_picture,
        website = company.website,
        created_at = company.created_at,
        feed_user_store_pk = user.feed_user_store_pk
    )
}

fun FeedRepository.createFeedCompanyStoryPictureC(
    company: FeedPropertiesCompany
): List<FeedCompanyStoryPicture> {
    val stories: ArrayList<FeedCompanyStoryPicture> = ArrayList()
    for (story in company.storypicture) {
        stories.add(
            FeedCompanyStoryPicture(
                feed_company_story_picture = story.id.toInt(),
                feed_company_pk = company.id.toInt(),
                feed_network_pk = null,
                network_id = story.networkid.toInt(),
                user_id = story.userid.toInt(),
                story_picture = story.storypicture,
                duration = story.duaration,
                created_at = story.created_at,
                type = story.type,
                status = story.status
            )
        )
    }
    return stories
}

fun FeedRepository.createFeedNetworkStore(
    user: FeedUserStore,
    networks: List<FeedPropertiesNetwork>
): List<FeedNetworkStore> {
    val feedNetworkStoreList: ArrayList<FeedNetworkStore> = ArrayList()
    for (network in networks) {
        feedNetworkStoreList.add(
            FeedNetworkStore(
                feed_network_pk = network.id.toInt(),
                feed_user_store_pk = user.feed_user_store_pk,
                name = network.name,
                network = network.network,
                profile_picture = network.profile_picture,
                cover_picture = network.display_picture,
                website = network.website,
                created_at = network.created_at
            )
        )
    }

    return feedNetworkStoreList
}

fun FeedRepository.createFeedCompanyStoryPicture(
    networks: List<FeedPropertiesNetwork>
): List<FeedCompanyStoryPicture> {
    val feedNetworkStoryPictures: ArrayList<FeedCompanyStoryPicture> = ArrayList()
    for (network in networks) {
        for (story in network.storypicture) {
            feedNetworkStoryPictures.add(
                FeedCompanyStoryPicture(
                    feed_company_story_picture = story.id.toInt(),
                    feed_company_pk = null,
                    feed_network_pk = network.id.toInt(),
                    network_id = story.networkid.toInt(),
                    user_id = story.userid.toInt(),
                    story_picture = story.storypicture,
                    duration = story.duaration,
                    created_at = story.created_at,
                    type = story.type,
                    status = story.status
                )
            )
        }

    }
    return feedNetworkStoryPictures
}

fun FeedRepository.createNewsFeedStore(
    news: FeedPropertiesNews
): List<FeedNewsStore> {
    val feedNewsStore: ArrayList<FeedNewsStore> = ArrayList()
    feedNewsStore.add(FeedNewsStore(1, "business"))
    feedNewsStore.add(FeedNewsStore(2,"economictimes"))
    feedNewsStore.add(FeedNewsStore(3,"science"))
    feedNewsStore.add(FeedNewsStore(4,"technology"))
    feedNewsStore.add(FeedNewsStore(5,"timesofindia"))
    return feedNewsStore
}

fun FeedRepository.createFeedNewsStoryPicture(
    news: FeedPropertiesNews
): List<FeedNewsStoryPicture>{

    for(item in news.business){
        Log.d("debug", "createFeedNewsStoryPicture: Comapny: ${item.company}, " +
                "Title: ${item.title}," +
                "Description: ${item.description}," +
                "GUID: ${item.guid}," +
                "IMage : ${item.id},${item.image}")
    }


    val feedNewsStoryPictures: ArrayList<FeedNewsStoryPicture> = ArrayList()

    for( story in news.business){
        feedNewsStoryPictures.add(
            FeedNewsStoryPicture(
                feed_news_story_picture_pk = story.id.toInt(),
                feed_news_store_pk = 1,
                title = story.title,
                description = story.description,
                published_date = story.pubDate,
                guid = story.guid,
                image = story.image,
                company = story.company
            )
        )
    }

    for( story in news.economictimes){
        feedNewsStoryPictures.add(
            FeedNewsStoryPicture(
                feed_news_story_picture_pk = story.id.toInt(),
                feed_news_store_pk = 2,
                title = story.title,
                description = story.description,
                published_date = story.pubDate,
                guid = story.guid,
                image = story.image,
                company = story.company
            )
        )
    }

    for( story in news.science){
        feedNewsStoryPictures.add(
            FeedNewsStoryPicture(
                feed_news_story_picture_pk = story.id.toInt(),
                feed_news_store_pk = 3,
                title = story.title,
                description = story.description,
                published_date = story.pubDate,
                guid = story.guid,
                image = story.image,
                company = story.company
            )
        )
    }

    for( story in news.technology){
        feedNewsStoryPictures.add(
            FeedNewsStoryPicture(
                feed_news_story_picture_pk = story.id.toInt(),
                feed_news_store_pk = 4,
                title = story.title,
                description = story.description,
                published_date = story.pubDate,
                guid = story.guid,
                image = story.image,
                company = story.company
            )
        )
    }

    for( story in news.timesofindia){
        feedNewsStoryPictures.add(
            FeedNewsStoryPicture(
                feed_news_story_picture_pk = story.id.toInt(),
                feed_news_store_pk = 5,
                title = story.title,
                description = story.description,
                published_date = story.pubDate,
                guid = story.guid,
                image = story.image,
                company = story.company
            )
        )
    }
    return feedNewsStoryPictures
}