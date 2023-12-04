package exception

import java.lang.RuntimeException

open class AppException: RuntimeException{
    constructor(): super()
    constructor(message: String): super(message)
    constructor(cause: Throwable): super(cause)
}

class ConnectionException(cause: Throwable): AppException(cause = cause)

open class BackendException(
    message: String
): AppException(message = message)


