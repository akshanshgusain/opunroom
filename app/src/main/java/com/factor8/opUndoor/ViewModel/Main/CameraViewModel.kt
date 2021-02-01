package com.factor8.opUndoor.ViewModel.Main

import androidx.lifecycle.LiveData
import com.factor8.opUndoor.Repository.Main.CameraRepository
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Main.Camera.state.CameraStateEvent
import com.factor8.opUndoor.UI.Main.Camera.state.CameraViewState
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.ViewModel.BaseViewModel
import javax.inject.Inject

class CameraViewModel

@Inject
constructor(cameraRepository: CameraRepository): BaseViewModel<CameraStateEvent, CameraViewState>(){
    override fun handleStateEvent(stateEvent: CameraStateEvent): LiveData<DataState<CameraViewState>> {
        return AbsentLiveData.create()
    }

    override fun initNewViewState(): CameraViewState {
        return CameraViewState()
    }

}