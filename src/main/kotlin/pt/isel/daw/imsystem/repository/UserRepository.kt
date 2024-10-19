package pt.isel.daw.imsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.imsystem.entity.User
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Optional<User>
}