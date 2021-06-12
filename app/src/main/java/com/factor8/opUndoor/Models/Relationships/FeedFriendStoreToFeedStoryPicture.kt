package com.factor8.opUndoor.Models.Relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.factor8.opUndoor.Models.FeedFriendStore
import com.factor8.opUndoor.Models.FeedStoryPicture

data class FeedFriendStoreToFeedStoryPicture(
    @Embedded val feedFriendStore: FeedFriendStore,
    @Relation(
        parentColumn = "feed_friend_store_pk",
        entityColumn = "feed_friend_store_pk"
    )
    val storyPictures: List<FeedStoryPicture>
)