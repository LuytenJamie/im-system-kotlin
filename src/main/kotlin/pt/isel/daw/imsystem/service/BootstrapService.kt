package pt.isel.daw.imsystem.service

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.entity.UserInvitation
import pt.isel.daw.imsystem.repository.UserInvitationRepository
import pt.isel.daw.imsystem.repository.UserRepository

@Service
class BootstrapService(
    @Autowired private val userInvitationRepository: UserInvitationRepository,
    @Autowired private val userRepository: UserRepository
) {
    @PostConstruct
    fun init() {
        if (userRepository.count() == 0L) {
            val invitation = UserInvitation(username = "firstuser", isUsed = false, creator = null)
            userInvitationRepository.save(invitation)
        }
    }
}