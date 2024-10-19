package pt.isel.daw.imsystem.api.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.imsystem.entity.User
import pt.isel.daw.imsystem.api.model.inputmodel.PostMessageInputModel
import pt.isel.daw.imsystem.api.model.outputmodel.MessageOutputModel
import pt.isel.daw.imsystem.service.MessageService

@RestController
@RequestMapping("/api/messages")
class MessageController(
    private val messageService: MessageService
) {
    @PostMapping("/{channelId}")
    fun postMessage(
        @PathVariable channelId: Long,
        @RequestBody request: PostMessageInputModel,
        @AuthenticationPrincipal currentUser: User
    ): ResponseEntity<MessageOutputModel> {
        return try {
            val message = messageService.postMessage(channelId, request.text, currentUser)
            ResponseEntity.ok(MessageOutputModel(message.id, message.text, message.user.username, message.createdAt.toString()))
        } catch (e: AccessDeniedException) {
            val errorMessage = MessageOutputModel(
                id = -1,
                text = e.message ?: "Access Denied",
                username = "Error",
                createdAt = ""
            )
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage)
        }
    }
    @GetMapping("/{channelId}")
    fun getMessages(@PathVariable channelId: Long): ResponseEntity<List<MessageOutputModel>> {
        val messages = messageService.getMessages(channelId)
        val response = messages.map { MessageOutputModel(it.id, it.text, it.user.username, it.createdAt.toString()) }
        return ResponseEntity.ok(response)
    }
}