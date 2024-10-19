package pt.isel.daw.imsystem.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.imsystem.entity.User
import pt.isel.daw.imsystem.api.model.inputmodel.CreateChannelInputModel
import pt.isel.daw.imsystem.api.model.outputmodel.ChannelOutputModel
import pt.isel.daw.imsystem.service.ChannelService

@RestController
@RequestMapping("/api/channels")
class ChannelController(
    private val channelService: ChannelService
) {
    @PostMapping("/create")
    fun createChannel(
        @RequestBody request: CreateChannelInputModel,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<ChannelOutputModel> {
        return if (currentUser != null) {
            println("create CHANNEL")
            val channel = channelService.createChannel(request.name, request.isPublic, currentUser)
            ResponseEntity.ok(ChannelOutputModel(channel.id, channel.name, channel.isPublic))
        } else {
            println("user is null")
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/joined")
    fun getJoinedChannels(@AuthenticationPrincipal currentUser: User): ResponseEntity<List<ChannelOutputModel>> {
        val channels = channelService.getJoinedChannels(currentUser)
        val response = channels.map { ChannelOutputModel(it.id, it.name, it.isPublic) }
        return ResponseEntity.ok(response)
    }

    @PostMapping("/{id}/join")
    fun joinChannel(
        @PathVariable id: Long,
        @AuthenticationPrincipal currentUser: User
    ): ResponseEntity<ChannelOutputModel> {
        val channel = channelService.joinChannel(id, currentUser)
        return ResponseEntity.ok(ChannelOutputModel(channel.id, channel.name, channel.isPublic))
    }

    @PostMapping("{id}/leave")
    fun leaveChannel(
        @PathVariable id: Long,
        @AuthenticationPrincipal currentUser: User
    ): ResponseEntity<ChannelOutputModel> {
        val channel = channelService.leaveChannel(id, currentUser)
        return ResponseEntity.ok(ChannelOutputModel(channel.id, channel.name, channel.isPublic))
    }
}