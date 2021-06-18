package com.factor8.opUndoor.UI.Main.Feed.state

import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.Relationships.FeedCompanyToFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedFriendStoreToFeedStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedGroupStoreToFeedGroupStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedNetworkStoreAndFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Wrappers.FeedLoadFromCache
import com.factor8.opUndoor.UI.Main.Feed.CreateGroupFragment

data class FeedViewState(
    //FeedFragment variables
    var accountProperties: AccountProperties? = null,
    var feedLoadFromCache: FeedLoadFromCache? = null,

    var storyList: StoryList? = StoryList(),
    var createGroupFields: CreateGroupFields? = CreateGroupFields()

) {
    data class StoryList(
        var listType: Int = 0,
        var friendStory: FeedFriendStoreToFeedStoryPicture? = null,
        var groupStory: FeedGroupStoreToFeedGroupStoryPicture? = null,
        var companyStory: FeedCompanyToFeedCompanyStoryPicture? = null,
        var networkStory: FeedNetworkStoreAndFeedCompanyStoryPicture? = null
    )

    data class CreateGroupFields(
        var group_title: String? = null,
        var groupParticipants: List<CreateGroupFragment.GroupParticipants>? = null
    ) {
        class CreateGroupFieldsError {
            companion object {
                fun mustFillTitle(): String {
                    return "You can't create a group without a Name"
                }

                fun mustHaveMembers(): String {
                    return "You must add members"
                }

                fun none(): String {
                    return "None"
                }
            }
        }

        fun isValidGroup(): String {
            if (group_title.isNullOrEmpty() || group_title.isNullOrEmpty()){
                return CreateGroupFieldsError.mustFillTitle()
            }
            if(groupParticipants.isNullOrEmpty()){
                return CreateGroupFieldsError.mustHaveMembers()
            }
            return CreateGroupFieldsError.none()
        }

        override fun toString(): String {
            return "Create Group: ${group_title}, # of members: ${groupParticipants?.size}"
        }
    }
}