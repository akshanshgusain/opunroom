package com.factor8.opUndoor.DI.Main

import androidx.lifecycle.ViewModel
import com.factor8.opUndoor.DI.ViewModelKey
import com.factor8.opUndoor.ViewModel.Auth.AuthViewModel
import com.factor8.opUndoor.ViewModel.Main.AccountViewModel
import com.factor8.opUndoor.ViewModel.Main.CameraViewModel
import com.factor8.opUndoor.ViewModel.Main.ChatViewModel
import com.factor8.opUndoor.ViewModel.Main.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAuthViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    abstract fun bindCameraViewModel(cameraViewModel: CameraViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(chatViewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(feedViewModel: FeedViewModel): ViewModel

}