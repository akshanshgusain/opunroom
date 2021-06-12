package com.factor8.opUndoor.Models.Wrappers

import com.factor8.opUndoor.Models.Relationships.*

data class FeedLoadFromCache(
    val userAndUserStories: List<FeedUserStoreToFeedStoryPicture>,
    val friendsAndFriendStories: List<FeedFriendStoreToFeedStoryPicture>,
    val groupsAndGroupStories: List<FeedGroupStoreToFeedGroupStoryPicture>,
    val companyAndCompanyStories: List<FeedCompanyToFeedCompanyStoryPicture>,
    val networkAndNetworkStories: List<FeedNetworkStoreAndFeedCompanyStoryPicture>,
    val newsAndNewsStories: List<FeedNewsStoreToFeedNewsStoryPicture>
)