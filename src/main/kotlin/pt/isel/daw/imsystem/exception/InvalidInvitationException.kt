package pt.isel.daw.imsystem.exception

import java.lang.RuntimeException

class InvalidInvitationException(
    message: String
) : RuntimeException(message) {
}