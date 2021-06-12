package com.factor8.opUndoor.Models.Relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.factor8.opUndoor.Models.FeedFriendStore
import com.factor8.opUndoor.Models.FeedUserStore

data class FeedUserStoreToFeedFriendStore(
    @Embedded val feedUserStore: FeedUserStore,
    @Relation(
        parentColumn = "feed_user_store_pk",
        entityColumn = "feed_user_store_pk"
    )
    val friends: List<FeedFriendStore>
)