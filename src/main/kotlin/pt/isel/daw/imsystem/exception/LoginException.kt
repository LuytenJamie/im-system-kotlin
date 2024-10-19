package pt.isel.daw.imsystem.exception

import java.lang.RuntimeException

class LoginException (
    message: String
) : RuntimeException(message) {
}