package com.factor8.opUndoor.UI.Main.Feed.state

import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.Relationships.FeedCompanyToFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedFriendStoreToFeedStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedGroupStoreToFeedGroupStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedNetworkStoreAndFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Wrappers.FeedLoadFromCache

data class FeedViewState(
    //FeedFragment variables
    var accountProperties: AccountProperties? = null,
    var feedLoadFromCache: FeedLoadFromCache? = null,

    var storyList: StoryList? = StoryList()
){
    data class StoryList(
        var listType: Int = 0,
        var friendStory: FeedFriendStoreToFeedStoryPicture? = null,
        var groupStory: FeedGroupStoreToFeedGroupStoryPicture? = null,
        var companyStory: FeedCompanyToFeedCompanyStoryPicture? = null,
        var networkStory: FeedNetworkStoreAndFeedCompanyStoryPicture? = null
    )
}