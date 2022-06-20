package com.iago.reminder.repository

import android.content.res.Resources
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.api.ReminderApi
import com.iago.reminder.models.*
import com.iago.reminder.utils.ErrorService
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: ReminderApi) : ErrorService {

    suspend fun login(email: String, password: String): Resource<UserModel> {
        val response = try {
            api.login(LoginModel(email, password))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), ContextProvider.getContext().getString(R.string.ERROR_LOGIN)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

    suspend fun logout(token: String): Resource<SimpleResponseModel> {
        val response = try {
            api.logout(token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

    suspend fun savePlayerID(playerID: String, token: String): Resource<SimpleResponseModel> {
        val response = try {
            api.playerID(PlayerIdModel(playerID), token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_ONESIGNAL)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

    suspend fun verifyEmail(email: String): Resource<IdModel> {
        val response = try {
            api.verifyEmail(EmailModel(email))
        } catch (e: HttpException) {
            return if (e.code() == 404)
                Resource.Success(IdModel(0))
            else
                Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_INVALID_EMAIL))
    }

    suspend fun confirmEmail(email: String): Resource<IdModel> {
        val response = try {
            api.confirmEmail(EmailModel(email))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), ContextProvider.getContext().getString(R.string.ERROR_NOT_REGISTERED_EMAIL)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

    suspend fun register(email: String, password: String): Resource<SimpleResponseModel> {
        val response = try {
            api.register(UserModel(0, null, email, password, null))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_USER_REGISTERED)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

    suspend fun confirmCode(id: Int, code: String): Resource<TokenModel> {
        val response = try {
            api.confirmCode(id, CodeModel(code))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_WRONG_CODE)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

   suspend fun recovery(password: String, token: String): Resource<UserModel> {
       val response = try {
           api.recovery(token, PasswordModel(password))
       } catch (e: HttpException) {
           return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)))
       } catch (e: Exception) {
           return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
       }
       return Resource.Success(response)
    }
}