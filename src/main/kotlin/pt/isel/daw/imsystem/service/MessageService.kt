package pt.isel.daw.imsystem.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.entity.Message
import pt.isel.daw.imsystem.entity.User
import pt.isel.daw.imsystem.exception.AccessDeniedException
import pt.isel.daw.imsystem.repository.ChannelRepository
import pt.isel.daw.imsystem.repository.MessageRepository

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val channelRepository: ChannelRepository
) {
    fun postMessage(channelId: Long, text: String, user: User): Message {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { IllegalArgumentException("Channel not found") }

        // Check if user is member
        if (!channel.users.contains(user)) {
            throw AccessDeniedException("User is not a member of the channel!")
        }
        // Check if user has read-write access
        if (!channel.hasReadWriteAccess(user)) {
            throw AccessDeniedException("User does not have read-write access to this channel!")
        }


        val message = Message(text = text, user = user, channel = channel)
        return messageRepository.save(message)
    }

    fun getMessages(channelId: Long): List<Message> {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { IllegalArgumentException("Channel not found") }
        return messageRepository.findByChannelOrderByCreatedAtAsc(channel)
    }
}