package com.factor8.opUndoor.UI.Main.Chat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.factor8.opUndoor.ViewModel.Main.AccountViewModel
import com.factor8.opUndoor.ViewModel.Main.ChatViewModel
import com.factor8.opUndoor.ViewModel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseChatFragment: DaggerFragment(){
    val TAG: String = "AppDebug"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: ChatViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(ChatViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }
}