package pt.isel.daw.imsystem.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.entity.Role
import pt.isel.daw.imsystem.entity.User
import pt.isel.daw.imsystem.exception.UserAlreadyExistsException
import pt.isel.daw.imsystem.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun addUser(username: String, password: String, role: Role): User {
        if (userRepository.existsByUsername(username)) {
            throw UserAlreadyExistsException("Username $username already exists!")
        }
        val hashedPassword = passwordEncoder.encode(password)
        val user = User(
            username = username,
            password = hashedPassword,
            role = role
        )
        userRepository.save(user)
        return user
    }
}