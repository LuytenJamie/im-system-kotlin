package pt.isel.daw.imsystem.exception

import java.lang.RuntimeException

class RegisterException(
    message: String
) : RuntimeException(message) {
}