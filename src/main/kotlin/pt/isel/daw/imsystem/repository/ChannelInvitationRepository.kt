package pt.isel.daw.imsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.imsystem.entity.ChannelInvitation

interface ChannelInvitationRepository : JpaRepository<ChannelInvitation, Long> {
    fun findByCode(code: String): ChannelInvitation?
    //fun findByChannelIdAndInvitedUserId(channelId: Long, invitedUserId: Long): ChannelInvitation?
}