package pt.isel.daw.imsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.imsystem.entity.Channel
import pt.isel.daw.imsystem.entity.Message

interface MessageRepository : JpaRepository<Message, Long> {
    fun findByChannelId(channelId: Long): List<Message>
    fun findByChannelOrderByCreatedAtAsc(channel: Channel): List<Message>
}