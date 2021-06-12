package com.factor8.opUndoor.Models.Relationships

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Relation
import com.factor8.opUndoor.Models.FeedStoryPicture
import com.factor8.opUndoor.Models.FeedUserStore

data class FeedUserStoreToFeedStoryPicture(
    @Embedded val feedUserStore: FeedUserStore,
    @Relation(
        parentColumn = "feed_user_store_pk",
        entityColumn = "feed_user_store_pk"
    )
    val storyPictures: List<FeedStoryPicture>
)