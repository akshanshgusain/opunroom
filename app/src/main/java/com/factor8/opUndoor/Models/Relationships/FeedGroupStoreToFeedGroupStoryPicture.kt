package com.factor8.opUndoor.Models.Relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.factor8.opUndoor.Models.FeedGroupStore
import com.factor8.opUndoor.Models.FeedGroupStoryPicture

data class FeedGroupStoreToFeedGroupStoryPicture(
    @Embedded val feedGroupStore: FeedGroupStore,
    @Relation(
        parentColumn = "feed_group_store_pk",
        entityColumn = "feed_group_store_pk"
    )
    val groupStoryPictures: List<FeedGroupStoryPicture>
)
