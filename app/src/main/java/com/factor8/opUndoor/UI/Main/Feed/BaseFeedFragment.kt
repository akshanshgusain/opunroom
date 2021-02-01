package com.factor8.opUndoor.UI.Main.Feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.factor8.opUndoor.ViewModel.Main.FeedViewModel
import com.factor8.opUndoor.ViewModel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFeedFragment: DaggerFragment(){

    val TAG: String = "AppDebug"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: FeedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(FeedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }
}