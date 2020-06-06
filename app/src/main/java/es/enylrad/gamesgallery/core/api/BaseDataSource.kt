package es.enylrad.gamesgallery.core.api

import android.content.Context
import es.enylrad.gamesgallery.commons.exception.RetrofitException
import es.enylrad.gamesgallery.commons.utils.reportCrash
import es.enylrad.gamesgallery.core.db.Result
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource(val context: Context?) {

    companion object {
        const val TAG = "BaseDataSource"
    }

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(response)
        } catch (exception: Exception) {
            return error(exception)
        }
    }

    private fun <T> error(response: Response<T>): Result<T> {
        return when (response.code()) {
            in 400..499 -> {
                val errorText = response.errorBody()?.string()
                val message = if (!errorText.isNullOrBlank()) {
                    errorText
                } else if (!response.message().isNullOrBlank()) {
                    response.message()
                } else {
                    ""
                }

                Result.error(message)
            }
            504 -> {
                val report = "${response.code()} : ${response.message()}"
                val exception = RetrofitException.MaintenanceException(report)

                Result.error(context?.getString(exception.messageRes) ?: "Error")
            }
            else -> {
                val report = "${response.code()} : ${response.message()}"
                val exception = RetrofitException.ServerException(report)

                reportCrash(exception, TAG)
                Result.error(context?.getString(exception.messageRes) ?: "Error")
            }
        }
    }

    private fun <T> error(exception: Exception): Result<T> {
        val e = exception.getRetrofitError()
        reportCrash(e, TAG)
        Timber.e(e)
        return Result.error(context?.getString(e.messageRes) ?: "Error")
    }

}

fun Throwable.getRetrofitError(): RetrofitException {
    val message = this.message ?: "null message"
    return when (this) {
        is SocketTimeoutException ->
            RetrofitException.TimeOutException(message)
        else -> {
            RetrofitException.RetrofitErrorException(message)
        }
    }
}