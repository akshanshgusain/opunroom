package com.factor8.opUndoor.UI.Main.Camera

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.factor8.opUndoor.ViewModel.Main.AccountViewModel
import com.factor8.opUndoor.ViewModel.Main.CameraViewModel
import com.factor8.opUndoor.ViewModel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseCameraFragment : DaggerFragment(){
    val TAG: String = "AppDebug"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: CameraViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(CameraViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }
}