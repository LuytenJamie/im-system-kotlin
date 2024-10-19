package pt.isel.daw.imsystem.config

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pt.isel.daw.imsystem.entity.Role
import pt.isel.daw.imsystem.service.UserService

@Component
class DbInitializer(
    private val userService: UserService
) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg args: String?) {
        userService.addUser("admin", "admin", Role.USER)
    }
}