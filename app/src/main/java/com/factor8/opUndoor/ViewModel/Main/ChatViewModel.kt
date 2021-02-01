package com.factor8.opUndoor.ViewModel.Main

import androidx.lifecycle.LiveData
import com.factor8.opUndoor.Repository.Main.ChatRepository
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Main.Chat.state.ChatStateEvent
import com.factor8.opUndoor.UI.Main.Chat.state.ChatViewState
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.ViewModel.BaseViewModel
import javax.inject.Inject

class ChatViewModel

@Inject
constructor( val chatRepository: ChatRepository): BaseViewModel<ChatStateEvent, ChatViewState>(){
    override fun handleStateEvent(stateEvent: ChatStateEvent): LiveData<DataState<ChatViewState>> {
        return AbsentLiveData.create()
    }

    override fun initNewViewState(): ChatViewState {
        return ChatViewState()
    }

}