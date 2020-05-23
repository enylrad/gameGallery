package es.enylrad.gamesgallery.commons.utils

import es.enylrad.gamesgallery.commons.exception.RetrofitException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun <T> callbackResponse(
    success: (response: T?) -> Unit,
    fail: (exception: RetrofitException?) -> Unit,
    TAG: String
): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            when {
                response.isSuccessful -> {
                    success(response.body())
                }
                response.code() in 400..499 -> {
                    val errorBody = response.errorBody()
                    val message: String =
                        if (errorBody != null && !errorBody.string().isNullOrBlank()) {
                            errorBody.string()
                        } else if (!response.message().isNullOrBlank()) {
                            response.message()
                        } else {
                            ""
                        }

                    fail(RetrofitException.ControlledException(message))
                }
                response.code() == 504 -> {
                    fail(RetrofitException.MaintenanceException())
                }
                else -> {
                    val exception = RetrofitException.ServerException(
                        "Error Server:\n" +
                                " - code: ${response.code()}\n" +
                                " - message: ${response.message()}"
                    )
                    reportCrash(exception, TAG)
                    fail(exception)
                }
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val exception = t.getRetrofitError()
            reportCrash(exception, TAG)
            fail(exception)
        }
    }
}

fun Throwable.getRetrofitError(): RetrofitException {
    return when (this) {
        // TODO
        else -> {
            RetrofitException.RetrofitErrorException(this.message)
        }
    }
}