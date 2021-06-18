package com.factor8.opUndoor.Repository.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.factor8.opUndoor.API.Main.OpUndoorMainService
import com.factor8.opUndoor.API.Main.Responses.FeedCreateGroupResponse
import com.factor8.opUndoor.API.Main.Responses.FeedResponse
import com.factor8.opUndoor.Models.*
import com.factor8.opUndoor.Models.Relationships.*
import com.factor8.opUndoor.Models.Wrappers.FeedLoadFromCache
import com.factor8.opUndoor.Models.Wrappers.FeedWrapper
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Persistence.FeedDao
import com.factor8.opUndoor.Repository.JobManager
import com.factor8.opUndoor.Repository.Main.Extensions.*
import com.factor8.opUndoor.Repository.NetworkBoundResource
import com.factor8.opUndoor.Session.SessionManager
import com.factor8.opUndoor.UI.Auth.State.AuthViewState
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Main.Account.state.AccountViewState
import com.factor8.opUndoor.UI.Main.Feed.CreateGroupFragment
import com.factor8.opUndoor.UI.Main.Feed.CreateGroupFragment.*
import com.factor8.opUndoor.UI.Main.Feed.state.FeedViewState
import com.factor8.opUndoor.UI.Response
import com.factor8.opUndoor.UI.ResponseType
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.Util.ApiSuccessResponse
import com.factor8.opUndoor.Util.GenericApiResponse
import com.factor8.opUndoor.Util.SuccessHandling.Companion.SUCCESS_GROUP_CREATED
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class FeedRepository
constructor(
    var service: OpUndoorMainService,
    var sessionManager: SessionManager,
    val accountPropertiesDao: AccountPropertiesDao,
    val feedDao: FeedDao
) : JobManager("FeedRepository") {

    private val TAG: String = "AppDebug"
    private var repositoryJob: Job? = null

    fun getFeed(
        authToken: AuthToken
    ): LiveData<DataState<FeedViewState>> {
        return object : NetworkBoundResource<FeedResponse, FeedWrapper, FeedViewState>(
            sessionManager.isConnectedToTheInternet(),
            true,
            false,
            true
        ) {
            // If the network is down, view cache and return
            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main) {

                    //finishing by viewing db cache
                    result.addSource(loadFromCache()) { FeedViewState ->

                        onCompleteJob(DataState.data(FeedViewState, null))
                    }
                }
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<FeedResponse>) {
                val feed = response.body
                val feedUserStore = createFeedUserStore(feed.self) // obj of user
                val feedFriendStore = createFeedFriendStore(feedUserStore, feed.friends) // list
                val feedStoryPicture: ArrayList<FeedStoryPicture> = ArrayList()
                feedStoryPicture.addAll(createFeedStoryPictureFriend(feed.friends))//list if friend-story pictures
                feedStoryPicture.addAll(createFeedStoryPictureUser(feed.self)) //list of user-stories
                val feedGroupStore =
                    createFeedGroupStore(feedUserStore, feed.groups) //list of groups
                val feedGroupStoryPicture =
                    createFeedGroupStoryPicture(feed.groups) // list of groups-stories
                val feedCompany = createFeedCompany(feedUserStore, feed.company[0]) //obj on company
                val feedCompanyStoryPicture: ArrayList<FeedCompanyStoryPicture> = ArrayList()
                val pics = createFeedCompanyStoryPictureC(feed.company[0])
                if (pics.size != 0) {
                    feedCompanyStoryPicture.addAll(pics) //List of company stories
                }
                val feedNetworkStore =
                    createFeedNetworkStore(feedUserStore, feed.network) // List of all networks
                feedCompanyStoryPicture.addAll(createFeedCompanyStoryPicture(feed.network)) //list of all network-stories
                val feedNewStore = createNewsFeedStore(feed.news)// obj news store
                val feedNewsStoryPicture =
                    createFeedNewsStoryPicture(feed.news) //list of new Story picture
                val feedWrapper = FeedWrapper(
                    feedUserStore = feedUserStore,
                    feedFriendStore = feedFriendStore,
                    feedStoryPicture = feedStoryPicture,
                    feedGroupStore = feedGroupStore,
                    feedGroupStoryPicture = feedGroupStoryPicture,
                    feedCompany = feedCompany,
                    feedCompanyStoryPicture = feedCompanyStoryPicture,
                    feedNetworkStore = feedNetworkStore,
                    feedNewsStore = feedNewStore,
                    feedNewsStoryPicture = feedNewsStoryPicture
                )

                updateLocalDb(feedWrapper)
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericApiResponse<FeedResponse>> {
                return service.getFeed(
                    userId = authToken.id.toString()
                )
            }

            override fun loadFromCache(): LiveData<FeedViewState> {
                lateinit var userAndUserStories: List<FeedUserStoreToFeedStoryPicture>
                lateinit var friendsAndFriendStories: List<FeedFriendStoreToFeedStoryPicture>
                lateinit var groupsAndGroupStories: List<FeedGroupStoreToFeedGroupStoryPicture>
                lateinit var companyAndCompanyStories: List<FeedCompanyToFeedCompanyStoryPicture>
                lateinit var networkAndNetworkStories: List<FeedNetworkStoreAndFeedCompanyStoryPicture>
                lateinit var newsAndNewsStories: List<FeedNewsStoreToFeedNewsStoryPicture>
                lateinit var loadFromCacheWrapper: FeedLoadFromCache

                val job = GlobalScope.async(Dispatchers.IO) {

                        try {
                            launch {
                                userAndUserStories =
                                    feedDao.getFeedUserStoreToFeedStoryPicture(authToken.id!!)
                            }
                        } catch (e: Exception) {
                            Log.e(
                                TAG,
                                "loadFromCache: getFeedUserStoreToFeedStoryPicture: ${e.message}"
                            )
                        }

                        try {
                            launch {
                                friendsAndFriendStories =
                                    feedDao.getFeedFriendStoreToFeedStoryPicture()
                            }
                        } catch (e: Exception) {
                            Log.e(
                                TAG,
                                "loadFromCache: getFeedFriendStoreToFeedStoryPicture: ${e.message}"
                            )
                        }
                        try {
                            launch {
                                groupsAndGroupStories =
                                    feedDao.getFeedGroupStoreToFeedGroupStoryPicture()
                            }
                        } catch (e: Exception) {
                            Log.e(
                                TAG,
                                "loadFromCache: getFeedGroupStoreToFeedGroupStoryPicture: ${e.message}"
                            )
                        }

                        try {
                            launch {
                                companyAndCompanyStories =
                                    feedDao.getFeedCompanyToCompanyStoryPicture()
                            }
                        } catch (e: Exception) {
                            Log.e(
                                TAG,
                                "loadFromCache: getFeedCompanyToCompanyStoryPicture: ${e.message}"
                            )
                        }

                        try {
                            launch {
                                networkAndNetworkStories =
                                    feedDao.getFeedNetworkStoreToCompanyStoryPicture()
                            }
                        } catch (e: Exception) {
                            Log.e(
                                TAG,
                                "loadFromCache: getFeedNetworkStoreToCompanyStoryPictures: ${e.message}"
                            )
                        }

                        try {
                            launch {

                                newsAndNewsStories = feedDao.getFeedNewsStoreToNewsStoryPicture()
                            }
                        } catch (e: Exception) {
                            Log.e(
                                TAG,
                                "loadFromCache: getFeedNewsStoreToNewsStoryPicture: ${e.message}"
                            )

                    }
                }

                runBlocking {
                    job.join()
                    loadFromCacheWrapper =
                        FeedLoadFromCache(
                            userAndUserStories = userAndUserStories,
                            friendsAndFriendStories = friendsAndFriendStories,
                            groupsAndGroupStories = groupsAndGroupStories,
                            companyAndCompanyStories = companyAndCompanyStories,
                            networkAndNetworkStories = networkAndNetworkStories,
                            newsAndNewsStories = newsAndNewsStories
                        )
                }


                return accountPropertiesDao.searchById(authToken.account_id!!)
                    .switchMap {
                        object : LiveData<FeedViewState>() {
                            override fun onActive() {
                                super.onActive()
                                value = FeedViewState(it, loadFromCacheWrapper)
                            }
                        }
                    }
            }

            override suspend fun updateLocalDb(cacheObject: FeedWrapper?) {
                if (cacheObject != null) {
                    withContext(IO) {
                        try {
                            launch {
                                feedDao.insertFeedUserStore(cacheObject.feedUserStore)
                            }
                        } catch (e: Exception) {
                            Log.e(
                                TAG,
                                "updateLocalDb: FeedRepository: Inserting FeedUserStore: <${e.message}>"
                            )
                        }

                        for (friend in cacheObject.feedFriendStore) {
                            launch {
                                try {
                                    feedDao.insertFeedFriendStore(friend)
                                } catch (e: Exception) {
                                    Log.e(
                                        TAG,
                                        "updateLocalDb: FeedRepository: Inserting FeedFriendStore: <${e.message}>"
                                    )
                                }
                            }
                        }

                        //FeedStoryPicture
                        for (storyPicture in cacheObject.feedStoryPicture) {
                            launch {
                                try {
                                    feedDao.insertFeedStoryPicture(storyPicture)
                                } catch (e: Exception) {
                                    Log.e(
                                        TAG,
                                        "updateLocalDb: FeedRepository: Inserting FeedStoryPicture: <${e.message}>"
                                    )
                                }
                            }
                        }
                        //FeedGroupStore
                        for (group in cacheObject.feedGroupStore) {
                            launch {
                                try {
                                    feedDao.insertFeedGroupStore(group)
                                } catch (e: Exception) {
                                    Log.e(
                                        TAG,
                                        "updateLocalDb: FeedRepository: Inserting FeedGroupStore: <${e.message}>"
                                    )
                                }
                            }
                        }
                        // Feed GroupStory Pictures
                        for (picture in cacheObject.feedGroupStoryPicture) {
                            launch {
                                try {
                                    feedDao.insertFeedGroupStoryPicture(picture)
                                } catch (e: Exception) {
                                    Log.e(
                                        TAG,
                                        "updateLocalDb: FeedRepository: Inserting FeedGroupStoryPicture: <${e.message}>"
                                    )
                                }
                            }
                        }
                        //Feed Company
                        launch {
                            try {
                                feedDao.insertFeedCompany(cacheObject.feedCompany)
                            } catch (e: Exception) {
                                Log.e(
                                    TAG,
                                    "updateLocalDb: FeedRepository: Inserting FeedCompany: <${e.message}>"
                                )
                            }
                        }

                        // FeedNetworkStore
                        for (network in cacheObject.feedNetworkStore) {
                            launch {
                                try {
                                    feedDao.insertFeedNetwork(network)
                                } catch (e: Exception) {
                                    Log.e(
                                        TAG,
                                        "updateLocalDb: FeedRepository: Inserting FeedNetwork: <${e.message}>"
                                    )
                                }
                            }
                        }

                        // FeedCompany Story Picture
                        for (picture in cacheObject.feedCompanyStoryPicture) {
                            launch {
                                try {
                                    feedDao.insertFeedCompanyStoryPicture(picture)
                                } catch (e: Exception) {
                                    Log.e(
                                        TAG,
                                        "updateLocalDb: FeedRepository: Inserting FeedCompanyStoryPicture: <${e.message}>"
                                    )
                                }
                            }
                        }

                        //Feed News Store
                        for (news in cacheObject.feedNewsStore) {
                            launch {
                                try {
                                    feedDao.insertFeedNewStore(news)
                                } catch (e: Exception) {
                                    Log.e(
                                        TAG,
                                        "updateLocalDb: FeedRepository: Inserting FeedNewStore: <${e.message}>"
                                    )
                                }
                            }
                        }
                        //Feed News Store Pictures
                        for (pictures in cacheObject.feedNewsStoryPicture) {
                            launch {
                                try {
                                    feedDao.insertFeedNewStoryPicture(pictures)
                                } catch (e: Exception) {
                                    Log.e(
                                        TAG,
                                        "updateLocalDb: FeedRepository: Inserting FeedNewStoryPicture: <${e.message}>"
                                    )
                                }
                            }
                        }


                    }
                } else {
                    Log.d(TAG, "updateLocalDb: feed list is null")
                }
            }

            override fun setJob(job: Job) {
                addJob("getFeed", job)
            }

        }.asLiveData()
    }

    fun getAccountProperties(authToken: AuthToken): LiveData<DataState<FeedViewState>>{
        return object: NetworkBoundResource<Any, Any, FeedViewState>(
            sessionManager.isConnectedToTheInternet(),
            false,
            true,
            true
        ){
            //If the network is down, view the cache and return
            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main){

                    //finish by viewing db cache
                    result.addSource(loadFromCache()){
                        onCompleteJob(DataState.data(it, null))
                    }
                }
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Any>) {
                updateLocalDb(response.body)

                createCacheRequestAndReturn()
            }

            override fun loadFromCache(): LiveData<FeedViewState> {
                return accountPropertiesDao.searchById(authToken.account_id!!)
                    .switchMap{
                        object: LiveData<FeedViewState>(){
                            override fun onActive() {
                                super.onActive()
                                value = FeedViewState(it, null)
                            }
                        }
                    }
            }

            override suspend fun updateLocalDb(cacheObject: Any?) {

            }

            override fun createCall(): LiveData<GenericApiResponse<Any>> {
                //Not a network request
                return AbsentLiveData.create()
            }

            override fun setJob(job: Job) {
                addJob("getAccountProperties", job)
            }
        }.asLiveData()
    }

    fun attemptCreateGroup(
        groupTitle: String,
        groupParticipants: List<GroupParticipants>,
        authToken: AuthToken
    ): LiveData<DataState<FeedViewState>> {

        Log.d(TAG, "attemptCreateGroup: groupTitle: ${groupTitle}, # of members: ${groupParticipants.size}")

        val fieldErrors = FeedViewState.CreateGroupFields(groupTitle, groupParticipants).isValidGroup()

        if(!fieldErrors.equals(FeedViewState.CreateGroupFields.CreateGroupFieldsError.none())){
            return returnErrorResponse(fieldErrors, ResponseType.Dialog())
        }else{
            return object: NetworkBoundResource<FeedCreateGroupResponse, Any,FeedViewState>(
                sessionManager.isConnectedToTheInternet(),
                true,
                true,
                false
            ){
                override fun loadFromCache(): LiveData<FeedViewState> {
                    //skip this
                    return AbsentLiveData.create()
                }

                override suspend fun updateLocalDb(cacheObject: Any?) {
                    //skip this
                }

                override suspend fun createCacheRequestAndReturn() {
                    //Skip this
                }

                override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<FeedCreateGroupResponse>) {
                    if(response.body.message.equals("ERROR")){
                        return onErrorReturn("ERROR", true,false)
                    }

                    onCompleteJob(
                        DataState.data(
                            data = null,
                            response = Response(response.body.message, ResponseType.Dialog())
                        )
                    )
                }

                override fun createCall(): LiveData<GenericApiResponse<FeedCreateGroupResponse>> {
                    val userIds = userIdsToString(groupParticipants)
                    return service.createGroup(
                        groupTitle = groupTitle,
                        groupAllUserIds = userIds,
                        userId = authToken.id.toString()
                    )
                }

                override fun setJob(job: Job) {
                    repositoryJob?.cancel()
                    repositoryJob = job
                }
            }.asLiveData()
        }
    }

    private fun returnErrorResponse(errorMessage: String, responseType: ResponseType): LiveData<DataState<FeedViewState>> {
        Log.d(TAG, "returnErrorResponse: ${errorMessage}")

        return object : LiveData<DataState<FeedViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                    Response(
                        errorMessage,
                        responseType
                    )
                )
            }
        }
    }

    private fun userIdsToString(groupParticipants: List<GroupParticipants>): String{
        return groupParticipants
            .map{
                it.id
            }.
            joinToString(separator = ",")

    }

}