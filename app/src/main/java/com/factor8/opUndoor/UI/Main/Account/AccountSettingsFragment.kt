package com.factor8.opUndoor.UI.Main.Account

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.*
import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent
import com.factor8.opUndoor.UI.Main.Account.state.AccountViewState
import com.factor8.opUndoor.Util.Constants
import com.factor8.opUndoor.Util.Constants.Companion.GALLERY_REQUEST_CODE
import com.factor8.opUndoor.Util.ErrorHandling.Companion.ERROR_MUST_SELECT_IMAGE
import com.factor8.opUndoor.Util.ErrorHandling.Companion.ERROR_SOMETHING_WRONG_WITH_IMAGE
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_account_settings.*
import kotlinx.android.synthetic.main.fragment_account_settings.editText_current_company
import kotlinx.android.synthetic.main.fragment_account_settings.imageView_user_display_picture
import kotlinx.android.synthetic.main.fragment_account_settings.spinner_profession
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AccountSettingsFragment : BaseAccountFragment() {

    lateinit private var currentSwitchVal: String //holds Current configuration of switch
    lateinit var currentProfession: String
    lateinit var workExperience: String
    lateinit var pickImageFlag: String // 1 for displayImage and 2 for coverImage
    var displayPictureURI: Uri? = null
    var coverPictureURI: Uri? = null

    var network = ""

    var isFirstRun = 0 //try to do this in a better/cleaner way


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView_back.setOnClickListener {
            findNavController().navigateUp()
        }

        imageView_user_display_picture.setOnClickListener {
            if (stateChangeListener.isStoragePermissionGranted()) {
                pickImageFlag = "1"
                pickFromGallery()
            }
        }

        imageView_cover.setOnClickListener {
            if (stateChangeListener.isStoragePermissionGranted()) {
                pickImageFlag = "2"
                pickFromGallery()
            }
        }

        button_update.setOnClickListener {
            saveSettings()
        }

        subscribeObservers()
    }

    fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { datastate ->
            stateChangeListener.onDataStateChange(datastate)
            if (datastate != null) {
                datastate.data?.let { data ->
                    data.data?.let { event ->
                        event.getContentIfNotHandled()?.let { viewState ->
                            viewState.accountProperties?.let { accountProperties ->
                                //Log.d(TAG, "Account Fragment, DataState : f_name: ${accountProperties.f_name} and l_name: ${accountProperties.l_name}")
                                viewModel.setAccountPropertiesData(accountProperties)
                            }
                        }

                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {
                viewState.accountProperties?.let {
                    //Log.d(TAG, "View State: f_name: ${it.f_name},  l_name: ${it.l_name},  coverpicture: ${it.cover_picture}")
                    if (isFirstRun == 0) {
                        setAccountDataFields(it)
                    }
                }
            }

            if (viewState != null) {
                viewState.updatedProfileFields?.let {
                    //Log.d(TAG, "View State: f_name: ${it.f_name},  l_name: ${it.l_name},  coverpicture: ${it.cover_picture}")
                    if (isFirstRun != 0) {
                        setUpdatedFields(it)
                    }
                }
            }
        })
    }

    private fun setAccountDataFields(accountProperties: AccountProperties) {
        Log.d(TAG, " AccountSettingsFragment  setAccountDataFields: ")
        //Set All the Fields
        editText_fullName.setText(accountProperties.f_name)
        editText_last_name.setText(accountProperties.l_name)
        editText_current_company.setText(accountProperties.current_company)
        editText_current_company.setText(accountProperties.current_company)
        editText_email.setText(accountProperties.email)
        editText_username.setText(accountProperties.username)

        //Load Images
        requestManager.load(Constants.PROFILE_IMAGES + accountProperties.profile_picture).into(imageView_user_display_picture)
        requestManager.load(Constants.PROFILE_IMAGES + accountProperties.cover_picture).into(imageView_cover)

        //setup Spinners

        setSpinnerProfession(accountProperties.profession)
        setSpinnerWorkExperience(accountProperties.experience)

        //setUp Make-public Switch
        setUpSwitch(accountProperties.privacy)

        network = accountProperties.network
        isFirstRun = 1
    }

    private fun setUpdatedFields(updatedProfileFields: AccountViewState.UpdatedProfileFields) {

        //Load Updated Images

        updatedProfileFields.displayPicture?.let {
            requestManager.load(updatedProfileFields.displayPicture).into(imageView_user_display_picture)
            Log.d(TAG, "setUpdatedFields: Set Display picture")
        }
        updatedProfileFields.coverPicture?.let {
            requestManager.load(updatedProfileFields.coverPicture).into(imageView_cover)
            Log.d(TAG, "setUpdatedFields: Set Cover picture")
        }


    }

    //Setup initial value of the switch based on privicy setting in accountProperties object
    private fun setUpSwitch(privacy: String) {
        if (privacy == "2") {
            switch_make_public.setChecked(false)
            currentSwitchVal = "2"
        } else if (privacy == "1") {
            switch_make_public.setChecked(true)
            currentSwitchVal = "1"
        }

        switch_make_public.setOnClickListener {
            createAlertDialog(currentSwitchVal)
        }
    }

    private fun setSpinnerProfession(profession: String) {
        val profession_array = resources.getStringArray(R.array.professions)
        val spinner = spinner_profession
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, profession_array)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                currentProfession = profession_array[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                currentProfession = profession
            }
        }

        val index: Int = selectSpinnerValue(profession_array, profession)
        spinner.setSelection(index, false)
    }

    private fun setSpinnerWorkExperience(workEx: String) {
        val workExArray = resources.getStringArray(R.array.experience)
        val spinner = spinner_work_experience
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, workExArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                workExperience = workExArray[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                workExperience = workEx
            }
        }

        val index: Int = selectSpinnerValue(workExArray, workEx)
        spinner.setSelection(index, false)
    }


    //Function to get Current position of selected array item in the array
    private fun selectSpinnerValue(ListSpinner: Array<String>, myString: String): Int {
        var index = 0
        for (i in ListSpinner.indices) {
            if (ListSpinner[i] == myString) {
                index = i
                break
            }
        }
        return index
    }

    // createAlertDialog("Make Account Public", getString(R.string.makeAccount_public), "Make Public", "1")
    private fun createAlertDialog(switchValue: String) {
        val callback: MakeAccountPublicCallback = object : MakeAccountPublicCallback {
            override fun proceed(switchVal: String) {
                if (switchVal == "1") {
                    currentSwitchVal = "2"
                } else {
                    currentSwitchVal = "1"
                }
            }

            override fun cancel() {
                //Do nothing
            }

        }

        uiCommunicationListener.onUIMessageReceived(
                UIMessage(
                        if (switchValue.equals("1")) "Are you sure you want to make your account private?" else "Are you sure you want to make your account public?",
                        UIMessageType.MakeAccountPublic(callback),
                        switchValue
                )
        )
    }

    //Image picker, picks images from gallery
    private fun pickFromGallery() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "CROP: RESULT OK")
            when (requestCode) {

                GALLERY_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        activity?.let {
                            launchImageCrop(uri)
                        }
                    } ?: showErrorDialog(ERROR_SOMETHING_WRONG_WITH_IMAGE)
                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    Log.d(TAG, "CROP: CROP_IMAGE_ACTIVITY_REQUEST_CODE")
                    val result = CropImage.getActivityResult(data)
                    val resultUri = result.uri
                    if (pickImageFlag == "1") {

                        viewModel.setUpdatedProfileFields(
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                resultUri,
                                coverPictureURI
                        )
                        displayPictureURI = resultUri
                    }
                    if (pickImageFlag == "2") {
                        viewModel.setUpdatedProfileFields(
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                displayPictureURI,
                                resultUri
                        )
                        coverPictureURI = resultUri
                    }


                }

                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
                    Log.d(TAG, "CROP: ERROR")
                    showErrorDialog(ERROR_SOMETHING_WRONG_WITH_IMAGE)
                }
            }
        }
    }

    //Launch Image Cropper
    private fun launchImageCrop(uri: Uri) {
        context?.let {
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(it, this)
        }
    }

    //Create Error Dialog
    private fun showErrorDialog(errorMessage: String) {
        stateChangeListener.onDataStateChange(
                DataState(
                        Event(StateError(Response(errorMessage, ResponseType.Dialog()))),
                        Loading(isLoading = false),
                        Data(Event.dataEvent(null), null)
                )
        )
    }

    override fun onPause() {
        super.onPause()
        viewModel.setUpdatedProfileFields(
                editText_fullName.text.toString(),
                editText_last_name.text.toString(),
                editText_current_company.text.toString(),
                currentProfession,
                workExperience,
                editText_email.text.toString(),
                editText_username.text.toString(),
                currentSwitchVal,
                null,
                null
        )
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearNewBlogFields()
    }

    private fun saveSettings() {
        var multipartBody: MultipartBody.Part? = null
        var multipartBody2: MultipartBody.Part? = null

        viewModel.viewState.value?.updatedProfileFields?.displayPicture?.let { displayPictureUri ->
            displayPictureUri.path?.let { filePath ->
                val imageFile = File(filePath)
                Log.d(TAG, "saveSettings: Image File: file: ${imageFile}")

                val requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile)
                // name = field name in serializer
                // filename = name of the image file
                // requestBody = file with file type information

                multipartBody = MultipartBody.Part.createFormData("picture", imageFile.name, requestBody)
                Log.d(TAG, "saveSettings: multipart 1: $multipartBody")
            }
        }

        viewModel.viewState.value?.updatedProfileFields?.coverPicture?.let { coverPictureUri ->
            coverPictureUri.path?.let { filePath ->
                val imageFile = File(filePath)
                Log.d(TAG, "saveSettings: Image File Cover: file: ${imageFile}")

                val requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile)
                // name = field name in serializer
                // filename = name of the image file
                // requestBody = file with file type information

                multipartBody2 = MultipartBody.Part.createFormData("coverpic", imageFile.name, requestBody)
                Log.d(TAG, "saveSettings: multipart 2: $multipartBody2")
            }
        }

        //make a list of images
        val images: List<MultipartBody.Part?> = listOf(multipartBody, multipartBody2)

        viewModel.setStateEvent(AccountStateEvent.UpdateUserProfileEvent(
                    firstName = editText_fullName.text.toString(),
                    lastName = editText_last_name.text.toString(),
                    currentCompany = editText_current_company.text.toString(),
                    fieldOfWork = currentProfession,
                    totalWorkExperience = workExperience,
                    email = editText_email.text.toString(),
                    username = editText_username.text.toString(),
                    makeAccountPublic = currentSwitchVal,
                    network = network,
                    images = images
            ))


    }

}