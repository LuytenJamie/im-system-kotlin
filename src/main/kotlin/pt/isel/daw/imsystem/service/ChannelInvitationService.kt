package pt.isel.daw.imsystem.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.daw.imsystem.entity.Channel
import pt.isel.daw.imsystem.entity.ChannelInvitation
import pt.isel.daw.imsystem.entity.User
import pt.isel.daw.imsystem.exception.InvalidInvitationException
import pt.isel.daw.imsystem.exception.NotFoundException
import pt.isel.daw.imsystem.api.model.inputmodel.ChannelInvitationInputModel
import pt.isel.daw.imsystem.repository.ChannelInvitationRepository
import pt.isel.daw.imsystem.repository.ChannelRepository
import pt.isel.daw.imsystem.repository.UserRepository
import java.util.*

@Service
class ChannelInvitationService(
    private val channelInvitationRepository: ChannelInvitationRepository,
    private val userRepository: UserRepository,
    private val channelRepository: ChannelRepository
) {
    @Transactional
    fun createInvitation(inputModel: ChannelInvitationInputModel): ChannelInvitation {
        val channel: Channel = channelRepository.findById(inputModel.channelId)
            .orElseThrow { NotFoundException("Channel with ID ${inputModel.channelId} not found") }

        val inviter: User = userRepository.findById(inputModel.inviterId)
            .orElseThrow { NotFoundException("User with ID ${inputModel.inviterId} not found") }

//        val invitee: User = userRepository.findByUsername(inputModel.inviteeUsername)
//            .orElseThrow { NotFoundException("User with username ${inputModel.inviteeUsername} not found") }

        if (!channel.isInviterAuthorized(inviter)) {
            throw InvalidInvitationException("User ${inviter.username} does not have permission to invite others to this channel")
        }

        val invitation = ChannelInvitation(
            code = generateUniqueCode(),
            channel = channel,
            inviter = inviter,
            isReadOnly = inputModel.isReadOnly,
            isUsed = false
        )

        return channelInvitationRepository.save(invitation)
    }

    @Transactional
    fun useInvitation(code: String, userId: Long): ChannelInvitation {
        val user: User = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User with ID $userId not found") }

        val invitation: ChannelInvitation = channelInvitationRepository.findByCode(code)
            ?: throw NotFoundException("Invitation with code $code not found")

        if (invitation.isUsed) {
            throw InvalidInvitationException("This invitation has already been used.")
        }


        invitation.channel.addUser(user)
        invitation.isUsed = true

        return channelInvitationRepository.save(invitation)
    }

    private fun generateUniqueCode(): String {
        return UUID.randomUUID().toString()
    }
}