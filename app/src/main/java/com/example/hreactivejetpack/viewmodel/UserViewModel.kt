package com.example.hreactivejetpack.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hreactivejetpack.model.EmployeeDetailResposne
import com.example.hreactivejetpack.model.LoginModel
import com.example.hreactivejetpack.model.LoginResponse
import com.example.hreactivejetpack.model.Response.EditProfileResponse
import com.example.hreactivejetpack.retrofit.ApiInterface
import com.example.hreactivejetpack.utils.ApiState
import com.example.hreactivejetpack.utils.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val apiInterface: ApiInterface): ViewModel() {
    private val _users = mutableStateOf<ApiState<EmployeeDetailResposne>>(ApiState.Loading())
    val users: State<ApiState<EmployeeDetailResposne>> = _users
    private val _editProfile = mutableStateOf<ApiState<EditProfileResponse>>(ApiState.Loading())
    val editProfileResponse: State<ApiState<EditProfileResponse>> = _editProfile
    private val _login = MutableLiveData<ApiState<LoginResponse>>()
    val loginResponse: LiveData<ApiState<LoginResponse>> = _login
    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = apiInterface.getHomeDetail("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFtbGVhZHRlc3RpbmdAZ21haWwuY29tIiwiZXhwIjoxNzUyMTQ5MDczLCJpYXQiOjE3MjA2MTMwNzN9.CtXYr_TxlB_I0MbuwO5Vq3-0fYzdkJsAILL7LR_Oz4Nt_tnV1YnquAscbla3RMdaY10slQRvSsGjC1QuoPR8SA",28)
                _users.value = ApiState.Success(response)
            } catch (e: Exception) {
                _users.value = ApiState.Error("Failed to fetch data")
            }
        }
    }

    fun editProfile(context: Context, firstName:String,lastName:String,dob:String,gender:String,maritalStatus:String,nationality:String,userName:String,email:String,countryCode:String,mobileNumber:String,currentAdd:String,degreeEducation:String,intermediate:String,highSchool:String){
        viewModelScope.launch {
            try {
                val response = apiInterface.editProfileWithoutImage("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFtbGVhZHRlc3RpbmdAZ21haWwuY29tIiwiZXhwIjoxNzUyMTQ5MDczLCJpYXQiOjE3MjA2MTMwNzN9.CtXYr_TxlB_I0MbuwO5Vq3-0fYzdkJsAILL7LR_Oz4Nt_tnV1YnquAscbla3RMdaY10slQRvSsGjC1QuoPR8SA",28,firstName,lastName,dob,gender,maritalStatus,nationality,userName,email,91,mobileNumber,currentAdd,degreeEducation,intermediate,highSchool)
                _editProfile.value = ApiState.Success(response)
                Toast.makeText(context,"Update Successfully", Toast.LENGTH_LONG).show()

            }catch (e: Exception){
                _editProfile.value = ApiState.Error("Failed to fetch data")
                Toast.makeText(context,"Api Fail", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun editProfileWithImage(context: Context, image:MultipartBody.Part,firstName:RequestBody,lastName:RequestBody,dob:RequestBody,gender:RequestBody,maritalStatus:RequestBody,nationality:RequestBody,userName:RequestBody,email:RequestBody,countryCode:String,mobileNumber:RequestBody,currentAdd:RequestBody,degreeEducation:RequestBody,intermediate:RequestBody,highSchool:RequestBody){
        viewModelScope.launch {
            try {
                val response = apiInterface.editProfileWithImage("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFtbGVhZHRlc3RpbmdAZ21haWwuY29tIiwiZXhwIjoxNzUyMTQ5MDczLCJpYXQiOjE3MjA2MTMwNzN9.CtXYr_TxlB_I0MbuwO5Vq3-0fYzdkJsAILL7LR_Oz4Nt_tnV1YnquAscbla3RMdaY10slQRvSsGjC1QuoPR8SA",image,28,firstName,lastName,dob,gender,maritalStatus,nationality,userName,email,91,mobileNumber,currentAdd,degreeEducation,intermediate,highSchool)
                _editProfile.value = ApiState.Success(response)
                Toast.makeText(context,"Update Successfully", Toast.LENGTH_LONG).show()
            }catch (e: Exception){
                _editProfile.value = ApiState.Error("Failed to fetch data")
                Toast.makeText(context,"Api Fail", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun userLogin(context: Context,loginModel: LoginModel){
        viewModelScope.launch {
            try {
                val response = apiInterface.userLogin(loginModel)
                if (response.isSuccessful && response.body() != null) {
                    Utils.showToast(context,"Login Successfully")
                    _login.value = ApiState.Success(response.body())
                } else {
                    handleError(context,response)
                }
            } catch (e: Exception) {
                _login.value = ApiState.Error(e.message ?: "An error occurred")

            }
        }
    }

    private fun handleError(context: Context,response: Response<LoginResponse>) {
        try {
            val jObjError = JSONObject(response.errorBody()!!.string())
            val message = jObjError.optString("message", jObjError.optString("error", "Unknown error"))
            _login.value = ApiState.Error(message)
            Utils.showToast(context,message)

        } catch (e: Exception) {
            Log.e("error", e.toString())
            _login.value = ApiState.Error("Error")
            Utils.showToast(context,"Error")
        }
    }
}