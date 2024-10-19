package pt.isel.daw.imsystem.service

import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.entity.Message
import pt.isel.daw.imsystem.entity.User
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
        val message = Message(text = text, user = user, channel = channel)
        return messageRepository.save(message)
    }

    fun getMessages(channelId: Long): List<Message> {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { IllegalArgumentException("Channel not found") }
        return messageRepository.findByChannelOrderByCreatedAtAsc(channel)
    }
}