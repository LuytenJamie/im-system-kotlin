package pt.isel.daw.imsystem.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.imsystem.model.inputmodel.ChannelInvitationInputModel
import pt.isel.daw.imsystem.model.outputmodel.ChannelInvitationOutputModel
import pt.isel.daw.imsystem.service.ChannelInvitationService

@RestController
@RequestMapping("api/invitations")
class ChannelInvitationController(
    private val channelInvitationService: ChannelInvitationService
) {
    @PostMapping("/create")
    fun createInvitation(
        @RequestBody channelInvitationInputModel: ChannelInvitationInputModel
    ): ResponseEntity<ChannelInvitationOutputModel> {
        val invitation = channelInvitationService.createInvitation(channelInvitationInputModel)

        val response = ChannelInvitationOutputModel(
            message = "Succesfully created channelinvitation",
            id = invitation.id,
            code = invitation.code,
            channel = invitation.channel,
            isUsed = invitation.isUsed,
            isReadOnly = invitation.isReadOnly
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/use/{code}")
    fun useInvitation(
        @PathVariable code: String,
        @RequestParam userId: Long
    ): ResponseEntity<ChannelInvitationOutputModel> {
        val invitation = channelInvitationService.useInvitation(code, userId)

        val response = ChannelInvitationOutputModel(
            message = "Succesfully joined channel",
            id = invitation.id,
            code = invitation.code,
            channel = invitation.channel,
            isUsed = invitation.isUsed,
            isReadOnly = invitation.isReadOnly
        )
        return ResponseEntity.ok(response)
    }
}