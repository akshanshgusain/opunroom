package com.factor8.opUndoor.Persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.factor8.opUndoor.Models.*
import com.factor8.opUndoor.Models.Relationships.*

@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedUserStore(user: FeedUserStore): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedStoryPicture(storyPicture: FeedStoryPicture): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedFriendStore(friend: FeedFriendStore): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedGroupStore(group: FeedGroupStore): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedGroupStoryPicture(groupStoryPicture: FeedGroupStoryPicture): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedCompany(company: FeedCompany): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedCompanyStoryPicture(companyStoryPicture: FeedCompanyStoryPicture): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedNetwork(network: FeedNetworkStore): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedNewStore(news: FeedNewsStore): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedNewStoryPicture(newsStoryPicture: FeedNewsStoryPicture): Long

    @Query("SELECT * FROM feed_user_store WHERE feed_user_store_pk = :id")
    fun getFeedUserStore(id: Int): LiveData<FeedUserStore>

    @Query("SELECT * FROM feed_friend_store WHERE feed_friend_store_pk = :id")
    fun getFeedFriendStore(id: Int): LiveData<FeedFriendStore>

//    @Query("SELECT * FROM feed_story_picture WHERE feed_story_picture_pk = :id")
//    fun getFeedStoryPictureUser(id: Int): List<LiveData<FeedStoryPicture>>

    @Transaction
    @Query("SELECT * FROM feed_user_store WHERE feed_user_store_pk= :id")
    suspend fun getFeedUserStoreToFeedStoryPicture(id: Int):
            List<FeedUserStoreToFeedStoryPicture>//list of pictures by user

    @Transaction
    @Query("SELECT * FROM feed_user_store WHERE feed_user_store_pk= :id")
    suspend fun getFeedUserStoreToFeedFriendStore(id: Int):
            List<FeedUserStoreToFeedFriendStore> // list of friends of a user

    @Transaction
    @Query("SELECT * FROM feed_friend_store WHERE feed_friend_store_pk= :id")
    suspend fun getFeedFriendStoreToFeedStoryPicture(id: Int):
            List<FeedFriendStoreToFeedStoryPicture> // list of pictures by friend

    @Transaction
    @Query("SELECT * FROM feed_friend_store")
    suspend fun getFeedFriendStoreToFeedStoryPicture():
            List<FeedFriendStoreToFeedStoryPicture> // list of friends and their res. stories

    @Transaction
    @Query("SELECT * FROM feed_group_store")
    suspend fun getFeedGroupStoreToFeedGroupStoryPicture():
            List<FeedGroupStoreToFeedGroupStoryPicture> // list of all the groups and their res. stories

    @Transaction
    @Query("SELECT * FROM feed_company")
    suspend fun getFeedCompanyToCompanyStoryPicture():
            List<FeedCompanyToFeedCompanyStoryPicture> //list of all the companies adn their res. stories

    @Transaction
    @Query("SELECT * FROM feed_network")
    suspend fun getFeedNetworkStoreToCompanyStoryPicture():
            List<FeedNetworkStoreAndFeedCompanyStoryPicture>

    @Transaction
    @Query("SELECT * FROM feed_news_store")
    suspend fun getFeedNewsStoreToNewsStoryPicture():
            List<FeedNewsStoreToFeedNewsStoryPicture>
}