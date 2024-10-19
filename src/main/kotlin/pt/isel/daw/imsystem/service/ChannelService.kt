package pt.isel.daw.imsystem.service

import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.entity.Channel
import pt.isel.daw.imsystem.entity.User
import pt.isel.daw.imsystem.repository.ChannelRepository
import pt.isel.daw.imsystem.repository.UserRepository

@Service
class ChannelService(
    private val channelRepository: ChannelRepository,
    private val userRepository: UserRepository
) {
    fun createChannel(name: String, isPublic: Boolean, owner: User): Channel {
        if (channelRepository.findByName(name) != null) {
            throw IllegalArgumentException("Channel name already exists!")
        }
        val channel = Channel(name = name, isPublic = isPublic, owner = owner)
        channel.users.add(owner) // Owner joins channel automatically
        return channelRepository.save(channel)
    }
    fun getJoinedChannels(user: User): List<Channel> {
        return channelRepository.findByUsersContains(user)
    }
    fun joinChannel(channelId: Long, user: User): Channel {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { IllegalArgumentException("Channel not found") }
        if (channel.isPublic || channel.owner == user) {
            channel.users.add(user)
            return channelRepository.save(channel)
        } else {
            throw IllegalArgumentException("Private channel --- Access denied")
        }
    }
    fun leaveChannel(channelId: Long, user: User): Channel {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { IllegalArgumentException("Channel not found") }

        channel.users.remove(user)
        return channelRepository.save(channel)
    }
}