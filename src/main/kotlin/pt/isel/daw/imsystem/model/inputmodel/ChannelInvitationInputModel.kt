package pt.isel.daw.imsystem.model.inputmodel

data class ChannelInvitationInputModel(
    val channelId: Long,
    val inviterId: Long,
    val isReadOnly: Boolean
) {
}