package pt.isel.daw.imsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.imsystem.entity.Channel
import pt.isel.daw.imsystem.entity.User

interface ChannelRepository : JpaRepository<Channel, Long> {
    fun findByName(name: String): Channel?
    fun findByUsersContains(user: User): List<Channel>
    fun findByIsPublic(isPublic: Boolean): List<Channel>
}