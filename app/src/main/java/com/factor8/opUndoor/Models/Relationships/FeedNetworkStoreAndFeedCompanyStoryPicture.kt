package com.factor8.opUndoor.Models.Relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.factor8.opUndoor.Models.FeedCompanyStoryPicture
import com.factor8.opUndoor.Models.FeedNetworkStore

data class FeedNetworkStoreAndFeedCompanyStoryPicture(
    @Embedded val feedNetworkStore: FeedNetworkStore,
    @Relation(
        parentColumn = "feed_network_pk",
        entityColumn = "feed_network_pk"
    )
    val companyStoryPicture: List<FeedCompanyStoryPicture>
)