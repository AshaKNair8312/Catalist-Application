package com.example.catalistapplication.utils

class Constants {
    companion object {

        //url
//        const val BASE_URL = "https://developer.github.com/v3/"
        const val BASE_URL = "https://api.github.com/"

        //timeout
        const val SPLASH_TIME_OUT = 1000L
        const val CONNECT_TIMEOUT = 40
        const val TIMEOUT = 40000

        //request codes
        const val REQ_TO_OTP = 100
        const val REQ_TO_SETUP = 101
        const val REQ_TO_LOCATION = 102
        const val REQ_TO_NEWADDRESS = 103
        const val REQ_TO_CHANGE = 104
        const val REQ_TO_NEW_DELIVERY_ADDRESS = 105
        const val REQ_TO_EDIT_PROFILE = 106
        const val REQ_TO_ADD_ADDRESS = 107
        const val REQ_TO_SAVED_ADDRESS = 108
        const val REQ_TO_CHANGE_PHONE = 109
        const val AUTOCOMPLETE_REQUEST_CODE = 110
        const val REQ_TO_CHECKOUT = 111
        const val REQ_TO_RATING = 112
        const val REQ_TO_CHANGE_ADDRESS = 113

    }
}
