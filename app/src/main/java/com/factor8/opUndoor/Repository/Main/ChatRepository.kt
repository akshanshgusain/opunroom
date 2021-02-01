package com.factor8.opUndoor.Repository.Main

import android.util.Log
import com.factor8.opUndoor.API.Main.OpUndoorMainService
import com.factor8.opUndoor.Session.SessionManager
import kotlinx.coroutines.Job

class ChatRepository
constructor(
        var service: OpUndoorMainService,
        var sessionManager: SessionManager
){
    private val TAG: String = "AppDebug"
    private var repositoryJob: Job? = null

    fun cancelActiveJobs() {
        Log.d(TAG, "ChatRepository: Cancelling on-going jobs...")
        repositoryJob?.cancel()
    }
}