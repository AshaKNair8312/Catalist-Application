package com.example.catalistapplication.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.example.catalistapplication.model.DetailModel
import com.example.catalistapplication.model.DetailSearchResponse
import com.example.catalistapplication.model.HomeModel
import com.example.catalistapplication.model.SearchResponse
import com.example.catalistapplication.network.RetroApi.retrofitService
import com.example.catalistapplication.utils.Constants
import com.example.catalistapplication.utils.SessionManager
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import kotlin.collections.ArrayList

class ConnectionManager {

    companion object {
        fun getDataManager(): ConnectionManager {
            return ConnectionManager()
        }
    }


    suspend fun getHomeData(
        callBack: ResultCallBack<ArrayList<HomeModel>>
    ) {
        val job = withTimeout(Constants.TIMEOUT.toLong()) {
            if (isInternetAvailable()) {

                try {
                    val getPropertiesDeferred =
                        retrofitService.getHomeDataAsync(
                            100
                        )
                    val response = getPropertiesDeferred.await()
                    Timber.d("response--> $response")
                    onResponseReceived(response, callBack)
                } catch (e: Exception) {
                    Timber.d("exception--> ${e.message}")
                    e.message?.let { callBack.onError(1, "Something went wrong") }
                }
            } else
                callBack.onError(2, "noNet")
        }
        if (job == null)
            callBack.onError(2, "Connection Timed Out")
    }

    suspend fun getSearchData(
        key: String,
        callBack: ResultCallBack<SearchResponse>
    ) {
        val job = withTimeout(Constants.TIMEOUT.toLong()) {
            if (isInternetAvailable()) {

                try {
                    val getPropertiesDeferred =
                        retrofitService.getSearchDataAsync(
                           key
                        )
                    val response = getPropertiesDeferred.await()
                    Timber.d("response--> $response")
                    onResponseReceived(response, callBack)
                } catch (e: Exception) {
                    Timber.d("exception--> ${e.message}")
                    e.message?.let { callBack.onError(1, "Something went wrong") }
                }
            } else
                callBack.onError(2, "noNet")
        }
        if (job == null)
            callBack.onError(2, "Connection Timed Out")
    }

    suspend fun getUser(
        name:String,
        callBack: ResultCallBack<HomeModel>
    ) {
        val job = withTimeout(Constants.TIMEOUT.toLong()) {
            if (isInternetAvailable()) {

                try {
                    val getPropertiesDeferred =
                        retrofitService.getUserAsync(
                            name
                        )
                    val response = getPropertiesDeferred.await()
                    Timber.d("response--> $response")
                    onResponseReceived(response, callBack)
                } catch (e: Exception) {
                    Timber.d("exception--> ${e.message}")
                    e.message?.let { callBack.onError(1, "Something went wrong") }
                }
            } else
                callBack.onError(2, "noNet")
        }
        if (job == null)
            callBack.onError(2, "Connection Timed Out")
    }


    suspend fun getRepositories(
        name:String,
        callBack: ResultCallBack<java.util.ArrayList<DetailModel>>
    ) {
        val job = withTimeout(Constants.TIMEOUT.toLong()) {
            if (isInternetAvailable()) {

                try {
                    val getPropertiesDeferred =
                        retrofitService.getRepositoriesAsync(name
                        )
                    val response = getPropertiesDeferred.await()
                    Timber.d("response--> $response")
                    onResponseReceived(response, callBack)
                } catch (e: Exception) {
                    Timber.d("exception--> ${e.message}")
                    e.message?.let { callBack.onError(1, "Something went wrong") }
                }
            } else
                callBack.onError(2, "noNet")
        }
        if (job == null)
            callBack.onError(2, "Connection Timed Out")
    }

    suspend fun getRepositorySearchData(
        key: String,
        callBack: ResultCallBack<DetailSearchResponse>
    ) {
        val job = withTimeout(Constants.TIMEOUT.toLong()) {
            if (isInternetAvailable()) {

                try {
                    val getPropertiesDeferred =
                        retrofitService.getSeaRepositoriesAsync(
                            key
                        )
                    val response = getPropertiesDeferred.await()
                    Timber.d("response--> $response")
                    onResponseReceived(response, callBack)
                } catch (e: Exception) {
                    Timber.d("exception--> ${e.message}")
                    e.message?.let { callBack.onError(1, "Something went wrong") }
                }
            } else
                callBack.onError(2, "noNet")
        }
        if (job == null)
            callBack.onError(2, "Connection Timed Out")
    }



    private fun isInternetAvailable(): Boolean {
        val result: Boolean
        val connectivityManager = SessionManager.context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }

        return result
    }


//    suspend fun getProductDetail(
//        id: Int,
//        time: String,
//        callBack: ResultCallBack<BaseResponse<ProductData>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred =
//                    retrofitService.getProductDetailAsync("application/json",id, time)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("exception--> ${e.message}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "noNet")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection Timed Out")
//    }
//
//    suspend fun getChefDetail(
//        id: Int,
//        time: String,
//        lat: Double?,
//        lng: Double?,
//        callBack: ResultCallBack<BaseResponse<ChefResponse>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                if (lat != null && lng != null) {
//                    val getPropertiesDeferred =
//                        retrofitService.getChefDetailAsync(id, time, lat, lng)
//                    try {
//                        val response = getPropertiesDeferred.await()
//                        onResponseReceived(response, callBack)
//                    } catch (e: Exception) {
//                        Timber.d("ChefDetailException ${e.message}")
//                        e.message?.let { callBack.onError(1, "Something went wrong") }
//                    }
//                }
//            } else
//                callBack.onError(2, "noNet")
//        }
//        if (job == null)
//            callBack.onError(2, "ConnectionTimedOut")
//    }
//
//    suspend fun getProducts(
//        id: Int,
//        time: String,
//        lat: Double,
//        lng: Double,
//        callBack: ResultCallBack<BaseResponse<ArrayList<ChefResponseData>>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.getProductsAsync(id, time, lat, lng)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else {
//                callBack.onError(2, "Check your internet connection")
//            }
//        }
//        if (job == null)
//            callBack.onError(2, "Connection Timed Out")
//    }
//
//    suspend fun getAddress(
//        callBack: ResultCallBack<BaseResponse<List<AddressResponse>>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred =
//                    retrofitService.getAddressAsync("application/json",)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "2")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection Timed Out")
//    }
//
//    suspend fun addAddress(
//        data: AddressData1,
//        callBack: ResultCallBack<BaseResponse<AddressResponse>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.addAddressAsync("application/json",data)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(
//                        response as Response<BaseResponse<AddressResponse>>,
//                        callBack
//                    )
//                } catch (e: Exception) {
//                    Timber.d("exception ${e.message}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection Timed Out")
//    }
//
//    suspend fun editAddress(
//        id: Int,
//        data: AddressData,
//        callBack: ResultCallBack<BaseResponse<AddressResponse>>
//    ) {
//        Timber.d("EditData $id// $data")
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.editAddressAsync("application/json",id, data)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(
//                        response,
//                        callBack
//                    )
//                } catch (e: Exception) {
//                    Timber.d("exception ${e.message}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection Timed Out")
//    }
//
//    suspend fun deleteAddress(
//        id: Int,
//        callBack: ResultCallBack<BaseResponse<Any>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.deleteAddressAsync("application/json",id)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(
//                        response,
//                        callBack
//                    )
//                } catch (e: Exception) {
//                    Timber.d("exception delete ${e.message}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection Timed Out")
//    }
//
//    suspend fun changePhone(
//        changePhoneData: ChangePhoneData,
//        callBack: ResultCallBack<BaseResponse<Any>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.changePhoneAsync("application/json",changePhoneData)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(
//                        response,
//                        callBack
//                    )
//                } catch (e: Exception) {
//                    Timber.d("exception ${e.message}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection Timed Out")
//    }
//
//    suspend fun changePhoneOtp(
//        changePhoneOtpData: ChangePhoneOtpData,
//        callBack: ResultCallBack<BaseResponse<ChangePhoneOtpData>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.changePhoneOtpAsync("application/json",changePhoneOtpData)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(
//                        response,
//                        callBack
//                    )
//                } catch (e: Exception) {
//                    Timber.d("exception ${e.message}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection Timed Out")
//    }
//
////    suspend fun validatePickup(
////        pickupData: ValidatePickupData,
////        callBack: ResultCallBack<BaseResponse<ValidateResponse>>
////    ) {
////        val job = withTimeout(Constants.TIMEOUT.toLong()) {
////            if (isInternetAvailable()) {
////                val getPropertiesDeferred = retrofitService.validatePickupAsync(pickupData)
////                try {
////                    val response = getPropertiesDeferred.await()
////                    onResponseReceived(response, callBack)
////                } catch (e: Exception) {
////                    Timber.d("LoginResponse exception-> ${e}")
////                    e.message?.let { callBack.onError(1, "Something went wrong") }
////                }
////            } else
////                callBack.onError(2, "Check your internet connection")
////        }
////        if (job == null)
////            callBack.onError(2, "Connection timed out")
////    }
//
////    suspend fun validateDelivery(
////        deliveryData: ValidateDeliveryData,
////        callBack: ResultCallBack<BaseResponse<ValidateResponse>>
////    ) {
////        val job = withTimeout(Constants.TIMEOUT.toLong()) {
////            if (isInternetAvailable()) {
////                val getPropertiesDeferred = retrofitService.validateDeliveryAsync(deliveryData)
////                try {
////                    val response = getPropertiesDeferred.await()
////                    onResponseReceived(response, callBack)
////                } catch (e: Exception) {
////                    Timber.d("LoginResponse exception-> ${e}")
////                    e.message?.let { callBack.onError(1, "Something went wrong") }
////                }
////            } else
////                callBack.onError(2, "Check your internet connection")
////        }
////        if (job == null)
////            callBack.onError(2, "Connection timed out")
////    }
//
//
//    suspend fun newValidateCart(
//        jsonObject: JsonObject,
//        callBack: ResultCallBack<BaseResponse<ValidateResponse>>
//    ) {
//
//        val requestBody =
//            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
//
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.newValidateCartAsync("application/json",requestBody)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("exception-> $e")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null) {
//            callBack.onError(2, "Connection timed out")
//        }
//    }
//
//
////    suspend fun pickup(
////        pickupData: PickupData,
////        callBack: ResultCallBack<BaseResponse<CheckoutResponse>>
////    ) {
////        val job = withTimeout(Constants.TIMEOUT.toLong()) {
////            if (isInternetAvailable()) {
////                val getPropertiesDeferred = retrofitService.pickupAsync(pickupData)
////                try {
////                    val response = getPropertiesDeferred.await()
////                    onResponseReceived(response, callBack)
////                } catch (e: Exception) {
////                    Timber.d("LoginResponse exception-> ${e}")
////                    e.message?.let { callBack.onError(1, "Something went wrong") }
////                }
////            } else
////                callBack.onError(2, "Check your internet connection")
////        }
////        if (job == null)
////            callBack.onError(2, "Connection timed out")
////    }
////
////    suspend fun delivery(
////        deliveryData: DeliveryData,
////        callBack: ResultCallBack<BaseResponse<CheckoutResponse>>
////    ) {
////        val job = withTimeout(Constants.TIMEOUT.toLong()) {
////            if (isInternetAvailable()) {
////                val getPropertiesDeferred = retrofitService.deliveryAsync(deliveryData)
////                try {
////                    val response = getPropertiesDeferred.await()
////                    onResponseReceived(response, callBack)
////                } catch (e: Exception) {
////                    Timber.d("LoginResponse exception-> ${e}")
////                    e.message?.let { callBack.onError(1, "Something went wrong") }
////                }
////            } else
////                callBack.onError(2, "Check your internet connection")
////        }
////        if (job == null) {
////            callBack.onError(2, "Connection timed out")
////        }
////    }
//
//    suspend fun checkoutItems(
//        jsonObject: JsonObject,
//        callBack: ResultCallBack<BaseResponse<CheckoutResponse>>
//    ) {
//
//        val requestBody =
//            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
//
//
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.checkoutAsync("application/json",requestBody)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("LoginResponse exception-> ${e}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null) {
//            callBack.onError(2, "Connection timed out")
//        }
//    }
//
//    suspend fun paymentMethodsApi(
//        paymentData: PaymentData,
//        callBack: ResultCallBack<BaseResponse<PaymentResponse>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.paymentMethodsAsync("application/json",paymentData)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("exception--> $e")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
//
//
//    suspend fun cartPayment(
//        requestBody: RequestBody,
//        callBack: ResultCallBack<BaseResponse<CheckoutResponse>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.cartPaymentAsync(requestBody)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("exception--> $e")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
//
//
//    suspend fun cartPaymentForAction(
//        requestBody: RequestBody,
//        callBack: ResultCallBack<BaseResponse<CheckoutResponse>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.cartPaymentForActionAsync(requestBody)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("exception--> $e")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
//
////     fun cartPaymentProcess(
////        requestBody: RequestBody,
////        callBack: ResultCallBack<BaseResponse<CheckoutResponse>>
////    ) {
////        val job = withTimeout(Constants.TIMEOUT.toLong()) {
////            if (isInternetAvailable()) {
////                val getPropertiesDeferred = retrofitService.cartPaymentAsync(requestBody)
////                try {
////                    val response = getPropertiesDeferred.await()
////                    onResponseReceived(response, callBack)
////                } catch (e: Exception) {
////                    Timber.d("exception--> $e")
////                    e.message?.let { callBack.onError(1, "Something went wrong") }
////                }
////            } else
////                callBack.onError(2, "Check your internet connection")
////        }
////        if (job == null)
////            callBack.onError(2, "Connection timed out")
////    }
//
//    suspend fun getOrders(
//        page: Int,
//        limit: Int,
//        callBack: ResultCallBack<BaseResponse<Orders<List<CheckoutResponse>>>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.getOrdersAsync("application/json",page, limit)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("LoginResponse exception-> $e")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(3, "")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
//
//    suspend fun getOrderDetail(
//        id: Int,
//        callBack: ResultCallBack<BaseResponse<CheckoutResponse>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.getOrderDetailAsync("application/json",id)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("LoginResponse exception-> ${e}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
//
//    suspend fun getRating(
//        id: Int,
//        callBack: ResultCallBack<BaseResponse<ArrayList<ProductData>>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.getRateListAsync("application/json",id)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("LoginResponse exception-> ${e}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
//
//    suspend fun setRating(
//        ratingData: RatingData,
//        callBack: ResultCallBack<BaseResponse<ArrayList<ProductData>>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.setRatingAsync("application/json",ratingData)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("exception-> $e")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
//
//    suspend fun getReOrderList(
//        reOrderData: ReOrderData,
//        callBack: ResultCallBack<BaseResponse<List<ProductData>>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.getReOrderListAsync("application/json",reOrderData)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("LoginResponse exception-> ${e}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
//
//
//    fun orderPayment(
//        body: RequestBody,
//        callback: ResultCallBack<RequestBody>
//    ) {
//        val call: Call<ResponseBody> = retrofitService.cartPaymentProcessAsync(body)
//        call.enqueue(object : Callback<ResponseBody?> {
//            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
//                if (response.body() != null) {
//                    callback.onSuccess(response.body())
//                } else {
//                    callback.onError(1, response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
//                callback.onError(1, t.message.toString())
//            }
//        })
//    }
//
//    suspend fun sendToken(
//        notiFicationData: NotificationData,
//        callBack: ResultCallBack<BaseResponse<Any>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.sendTokenAsync("application/json",notiFicationData)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("LoginResponse exception-> ${e}")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }

    private fun <T> onResponseReceived(response: Response<T>, callBack: ResultCallBack<T>) {
        if (response.isSuccessful) {
            if (response.body() != null)
                callBack.onSuccess(response.body())
            else
                callBack.onError(1, response.message())
        } else {

            try {

                val reader = response.errorBody()?.string() ?: ""
                val reader1 = response.body() ?: ""
                Timber.d("reader--> $reader")
                Timber.d("reader--> $reader1")
                val jsonObject = JSONObject(reader)
                var error = ""
                if (error == "") {
                    error = when {
                        jsonObject.has("message") -> {
                            jsonObject.get("message").toString()
                        }
                        jsonObject.has("error") -> {
                            jsonObject.get("error").toString()
                        }
                        else -> {
                            ""
                        }
                    }
                }

                callBack.onError(response.code(), error)


            } catch (e: Exception) {
                e.printStackTrace()
                callBack.onError(1, "")
            }
        }
    }
//
//    suspend fun getEphemeralFromrepo(
//        api_version: String,
//        callback: ResultCallBack<EphemeralKeyData>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred = retrofitService.getEphemeralKeyAsync("application/json",api_version)
//                try {
//                    val response = getPropertiesDeferred.await()
//                    Timber.d("EphemeralKey--> $response")
//                    onResponseReceived(response, callback)
//                } catch (e: Exception) {
//                    Timber.d("Ephemeralexception--> ${e.message}")
//                    e.message?.let { callback.onError(1, "") }
//                }
//            } else {
//                callback.onError(2, "Check your internet connection")
//            }
//        }
//        if (job == null) {
//            callback.onError(2, "Connection timed out")
//        }
//    }
//
//    suspend fun logout(
//        callBack: ResultCallBack<BaseResponse<Any>>
//    ) {
//        val job = withTimeout(Constants.TIMEOUT.toLong()) {
//            if (isInternetAvailable()) {
//                val getPropertiesDeferred =
//                    retrofitService.logoutAsync()
//                try {
//                    val response = getPropertiesDeferred.await()
//                    onResponseReceived(response, callBack)
//                } catch (e: Exception) {
//                    Timber.d("exception-> $e")
//                    e.message?.let { callBack.onError(1, "Something went wrong") }
//                }
//            } else
//                callBack.onError(2, "Check your internet connection")
//        }
//        if (job == null)
//            callBack.onError(2, "Connection timed out")
//    }
}