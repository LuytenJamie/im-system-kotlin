package pt.isel.daw.imsystem.api.model.inputmodel

data class ChannelInvitationInputModel(
    val channelId: Long,
    val inviterId: Long,
    val isReadOnly: Boolean
) {
}