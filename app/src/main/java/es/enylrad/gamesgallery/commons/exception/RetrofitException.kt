package es.enylrad.gamesgallery.commons.exception

import androidx.annotation.StringRes
import es.enylrad.gamesgallery.R

sealed class RetrofitException(@StringRes val messageRes: Int, val report: String) : Exception() {
    class MaintenanceException(report: String) :
        RetrofitException(R.string.exception_api_mantinance, report)

    class RetrofitErrorException(report: String) :
        RetrofitException(R.string.exception_api_retrofit, report)

    class TimeOutException(report: String) :
        RetrofitException(R.string.exception_api_time_out, report)

    class ServerException(report: String) :
        RetrofitException(R.string.exception_api_server, report)
}