package com.factor8.opUndoor.Util

class Constants {

    companion object {

        const val BASE_URL = "https://dass.io/oppo/api/user/"
        const val PROFILE_IMAGES = "https://dass.io/oppo/app/profile/image/"
        const val COMPANY_IMAGES = "https://dass.io/oppo/app/story/company/"

        const val NETWORK_TIMEOUT = 3000L
        const val TESTING_NETWORK_DELAY = 0L // fake network delay for testing
        const val TESTING_CACHE_DELAY = 0L // fake cache delay for testing

        const val INVALID_CREDENTIALS = "Either the username or password is incorrect"

        const val CHAT_FRAGMENT_POSITION = 0;
        const val CAMERA_FRAGMENT_POSITION = 1;
        const val FEED_FRAGMENT_POSITION = 2;

        const val GALLERY_REQUEST_CODE = 201
        const val PERMISSIONS_REQUEST_READ_STORAGE: Int = 301
        const val CROP_IMAGE_INTENT_CODE: Int = 401
    }
}