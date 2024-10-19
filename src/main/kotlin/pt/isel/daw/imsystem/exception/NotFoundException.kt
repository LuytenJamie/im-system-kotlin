package pt.isel.daw.imsystem.exception

import java.lang.RuntimeException

class NotFoundException(
message: String
) : RuntimeException(message) {
}