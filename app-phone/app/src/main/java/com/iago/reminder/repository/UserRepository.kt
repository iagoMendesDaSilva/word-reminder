package com.iago.reminder.repository

import com.iago.reminder.R
import com.iago.reminder.api.ReminderApi
import com.iago.reminder.models.*
import com.iago.reminder.utils.ErrorService
import com.iago.reminder.utils.Resource
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: ReminderApi) : ErrorService {

    suspend fun login(email: String, password: String): Resource<UserModel> {
        val response = try {
            api.login(LoginModel(email, password))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_login))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun confirmEmail(email: String): Resource<IdModel> {
        val response = try {
            api.confirmEmail(EmailModel(email))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_email_not_registered))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun verifyEmail(email: String): Resource<IdModel> {
        val response = try {
            api.verifyEmail(EmailModel(email))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_email_not_registered))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun register(email: String, password: String): Resource<SimpleResponseModel> {
        val response = try {
            api.register(UserModel(0, null, email, password, null))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_user_registered))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun recovery(token: String, password: String): Resource<UserModel> {
        val response = try {
            api.recovery(token, PasswordModel(password))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_default))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun confirmCode(id: Int, code: String): Resource<TokenModel> {
        val response = try {
            api.confirmCode(id, CodeModel(code))
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_verification_code))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun logout(token: String): Resource<SimpleResponseModel> {
        val response = try {
            api.logout(token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_default))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun savePlayerID(playerID: String, token: String): Resource<SimpleResponseModel> {
        val response = try {
            api.playerID(PlayerIdModel(playerID), token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_notification))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun unregister(token: String): Resource<SimpleResponseModel> {
        val response = try {
            api.unregister(token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_default))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

}