package pt.isel.daw.imsystem.model.outputmodel

import pt.isel.daw.imsystem.entity.Channel
import pt.isel.daw.imsystem.entity.User

data class ChannelInvitationOutputModel(
    val message: String,
    val id: Long,
    val code: String,
    val channel: Channel,
    val isUsed: Boolean,
    val isReadOnly: Boolean
) {

}