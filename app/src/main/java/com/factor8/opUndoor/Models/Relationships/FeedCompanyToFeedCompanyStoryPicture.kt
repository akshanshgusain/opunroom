package com.factor8.opUndoor.Models.Relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.factor8.opUndoor.Models.FeedCompany
import com.factor8.opUndoor.Models.FeedCompanyStoryPicture

data class FeedCompanyToFeedCompanyStoryPicture(
    @Embedded val feedCompany: FeedCompany,
    @Relation(
        parentColumn = "feed_company_pk",
        entityColumn = "feed_company_pk"
    )
    val companyStories: List<FeedCompanyStoryPicture>
)