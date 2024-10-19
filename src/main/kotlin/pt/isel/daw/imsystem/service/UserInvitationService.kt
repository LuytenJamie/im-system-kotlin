package pt.isel.daw.imsystem.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.entity.User
import pt.isel.daw.imsystem.entity.UserInvitation
import pt.isel.daw.imsystem.repository.UserInvitationRepository
import pt.isel.daw.imsystem.repository.UserRepository

@Service
class UserInvitationService(
    @Autowired private val userInvitationRepository: UserInvitationRepository,
    @Autowired private val userRepository: UserRepository
) {
    fun createUserInvitation(username: String): UserInvitation {
        val creator = getCurrentUser() // Implement this to fetch the current user
        val userInvitation = UserInvitation(username = username, creator = creator)
        return userInvitationRepository.save(userInvitation)
    }

    private fun getCurrentUser(): User {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        if (!authentication.isAuthenticated) {
            throw IllegalArgumentException("No authenticated user found")
        }

        val username = when (val principal = authentication.principal) {
            is UserDetails -> principal.username
            is String -> principal
            else -> throw IllegalArgumentException("Unknown user principal type")
        }

        // Fetch the user from the repository and handle Optional
        return userRepository.findByUsername(username)
            .orElseThrow { IllegalArgumentException("User not found") }
    }
}