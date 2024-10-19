package pt.isel.daw.imsystem.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.repository.UserRepository

@Service
class CustomUserDetailsService @Autowired constructor(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val optionalUser = userRepository.findByUsername(username)
        if (optionalUser.isEmpty) {
            throw EntityNotFoundException("User with username: $username not found!")
        }
        return optionalUser.get() // Ensure this returns a UserDetails object
    }
}