package com.factor8.opUndoor.Models.Relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.factor8.opUndoor.Models.FeedNewsStore
import com.factor8.opUndoor.Models.FeedNewsStoryPicture

data class FeedNewsStoreToFeedNewsStoryPicture(
    @Embedded val feedNewsStore: FeedNewsStore,
    @Relation(
        parentColumn = "feed_news_store_pk",
        entityColumn = "feed_news_store_pk"
    )
    val newsStoryPicture: List<FeedNewsStoryPicture>
)
