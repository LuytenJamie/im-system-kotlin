package pt.isel.daw.imsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.imsystem.entity.UserInvitation

interface UserInvitationRepository : JpaRepository<UserInvitation, Long> {
}