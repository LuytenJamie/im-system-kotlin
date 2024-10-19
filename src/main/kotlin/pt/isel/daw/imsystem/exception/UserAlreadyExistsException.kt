package pt.isel.daw.imsystem.exception

import java.lang.RuntimeException

class UserAlreadyExistsException(
    message: String
) : RuntimeException(message) {
}