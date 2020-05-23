package es.enylrad.gamesgallery.commons.exception

sealed class RetrofitException(message: String?) : Exception() {
    class MaintenanceException : RetrofitException(null)
    class RetrofitErrorException(message: String?) : RetrofitException(message)
    class ControlledException(message: String?) : RetrofitException(message)
    class ServerException(message: String?) : RetrofitException(message)
}