package pt.isel.daw.imsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.imsystem.entity.UserInvitation
import java.util.*

interface UserInvitationRepository : JpaRepository<UserInvitation, Long> {
    fun findByToken(token: String): Optional<UserInvitation>
}