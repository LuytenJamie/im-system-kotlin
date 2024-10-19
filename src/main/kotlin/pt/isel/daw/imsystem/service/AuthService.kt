package pt.isel.daw.imsystem.service

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.entity.User
import pt.isel.daw.imsystem.exception.LoginException
import pt.isel.daw.imsystem.exception.UserAlreadyExistsException
import pt.isel.daw.imsystem.api.model.inputmodel.LoginInputModel
import pt.isel.daw.imsystem.api.model.inputmodel.RegisterInputModel
import pt.isel.daw.imsystem.api.model.outputmodel.LoginOutputModel
import pt.isel.daw.imsystem.api.model.outputmodel.RegisterOutputModel
import pt.isel.daw.imsystem.repository.UserInvitationRepository
import pt.isel.daw.imsystem.repository.UserRepository
import pt.isel.daw.imsystem.security.JwtUtil
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val userInvitationRepository: UserInvitationRepository
) {
    fun registerUser(request: RegisterInputModel): RegisterOutputModel {
        // Check if the username already exists
        if (userRepository.existsByUsername(request.username)) {
            throw UserAlreadyExistsException("Username with username: " + request.username + " already exists")
        }
        val existingUsers = userRepository.findAll()

        // If no users, allow registration without invite
        if (existingUsers.isEmpty()) {
            val user = User(
                username = request.username,
                password = request.password,
                role = request.role
            )
            val savedUser = userService.addUser(user.username, user.password, user.getRole())
            return RegisterOutputModel(
                id = savedUser.getId(),
                username = savedUser.username,
                role = savedUser.getRole(),
                status = "Succesfully created first user without invitation"
            )
        }
        val userInvitation = userInvitationRepository.findById(request.invitationId)
            .orElseThrow { IllegalArgumentException("Invalid invitation ID: ${request.invitationId}") }

        if (userInvitation.isUsed) {
            throw IllegalArgumentException("This invitation has already been used")
        }
        // Create a new user
        val user = User(
            username = request.username,
            password = request.password,
            role = request.role
        )

        // Save the user to the database
        val savedUser = userService.addUser(user.username, user.password, user.getRole())

        // Mark the user invitation as used
        userInvitation.isUsed = true
        userInvitationRepository.save(userInvitation)

        return RegisterOutputModel(
            id = savedUser.getId(),
            username = savedUser.username,
            role = savedUser.getRole(),
            status = "Successfully created user with invitation"
        )
    }
    fun loginUser(request: LoginInputModel): LoginOutputModel {
        val optionalUser: Optional<User> = userRepository.findByUsername(request.username)
        if (optionalUser.isEmpty) {
            throw UsernameNotFoundException("User with username: " + request.username + " not found!")
        }

        val user: User = optionalUser.get()
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw LoginException("Password incorrect!")
        }
        val token = jwtUtil.generateToken(user)
        //val token: String = jwtTokenProvider.generateToken(user)
        return LoginOutputModel(
            request,
            role = user.getRole(),
            jwtToken = token,
            token = "test"
        )
    }
}