package com.joao.garageapp.ui.user

import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao.garageapp.R
import com.joao.garageapp.domain.model.User
import com.joao.garageapp.domain.usecase.user.CreateUserUseCase
import com.joao.garageapp.domain.usecase.user.GetUserLoggedUseCase
import com.joao.garageapp.domain.usecase.user.ListUsersUseCase
import com.joao.garageapp.domain.usecase.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val getUserLoggedUseCase: GetUserLoggedUseCase,
    private val listUsersUseCase: ListUsersUseCase
) : ViewModel() {

    private val _imageUriErrorResId = MutableLiveData<Int?>()
    val imageUriErrorResId: LiveData<Int?> = _imageUriErrorResId

    private val _nameFieldErrorResId = MutableLiveData<Int?>()
    val nameFieldErrorResId: LiveData<Int?> = _nameFieldErrorResId

    private val _emailFieldErrorResId = MutableLiveData<Int?>()
    val emailFieldErrorResId: LiveData<Int?> = _emailFieldErrorResId

    private val _passwordFieldErrorResId = MutableLiveData<Int?>()
    val passwordFieldErrorResId: LiveData<Int?> = _passwordFieldErrorResId

    private val _userCreated = MutableLiveData<User>()
    val userCreated: LiveData<User> = _userCreated

    private val _userLogged = MutableLiveData<User>()
    val userLogged: LiveData<User> = _userLogged

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private var isFormValid = false


    fun createUser(name: String, imageUri: Uri?, email: String, password: String, confirmPassword: String) =
        viewModelScope.launch {
            isFormValid = true

            _imageUriErrorResId.value = getErrorStringResIdImageUri(imageUri)
            _nameFieldErrorResId.value = getErrorStringResIdName(name)
            _emailFieldErrorResId.value = getErrorStringResIdEmail(email)
            _passwordFieldErrorResId.value = getErrorStringResIdPassword(password)

            if(isFormValid) {
                try {
                    val user = createUserUseCase(name, imageUri!!, email, password)
                    _userCreated.value = user
                } catch (e: Exception) {
                    Log.d("CreateUser", e.toString())
                }
            }
    }


    fun loginUser(email: String, password: String) =
        viewModelScope.launch {
            isFormValid = true

            _emailFieldErrorResId.value = getErrorStringResIdEmail(email)
            _passwordFieldErrorResId.value = getErrorStringResIdPassword(password)

            if(isFormValid) {
                try {
                    val user = loginUseCase(email, password)
                    _userLogged.value = user!!
                } catch (e: Exception) {
                    Log.d("CreateUser", e.toString())
                }
            }
        }

    fun getUserLogged() =
        viewModelScope.launch {
            try {
                getUserLoggedUseCase().collect {
                    _userLogged.postValue(it)
                }
            } catch (e: Exception) {
                Log.d("GetUserLogged", e.toString())
            }

        }

    fun listUsers() =
        viewModelScope.launch {
            try {
                val users = listUsersUseCase()
                _listUser.value = users
            } catch (e: Exception) {
                Log.d("ListUserUseCase",e.toString())
            }
        }


    private fun getErrorStringResIdImageUri(value: Uri?): Int? {
        return if (value == null) {
            isFormValid = false
            R.string.sign_up_fragment_null_image
        } else null
    }

    private fun getErrorStringResIdName(name: String): Int? {
        return if (name.isEmpty()){
            isFormValid = false
            R.string.sign_up_fragment_empty_name
        } else null
    }

    private fun getErrorStringResIdEmail(email: String): Int? {
        return if (email.isEmpty()){
            isFormValid = false
            R.string.sign_up_fragment_empty_email
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isFormValid = false
            R.string.sign_up_fragment_error_email
        }else null
    }

    private fun getErrorStringResIdPassword(value: String): Int?{
        return if (value.isEmpty()){
            isFormValid = false
            R.string.sign_up_fragment_empty_password
        } else null
    }



}