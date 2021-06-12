package com.factor8.opUndoor.Models.Wrappers

import com.factor8.opUndoor.Models.*

data class FeedWrapper(
    val feedUserStore: FeedUserStore,
    val feedFriendStore: List<FeedFriendStore>,
    val feedStoryPicture: List<FeedStoryPicture>,
    val feedGroupStore: List<FeedGroupStore>,
    val feedGroupStoryPicture: List<FeedGroupStoryPicture>,
    val feedCompany: FeedCompany,
    val  feedCompanyStoryPicture: List<FeedCompanyStoryPicture>,
    val feedNetworkStore: List<FeedNetworkStore>,
    val feedNewsStore: List<FeedNewsStore>,
    val feedNewsStoryPicture: List<FeedNewsStoryPicture>
)
