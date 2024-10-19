package pt.isel.daw.imsystem.api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.daw.imsystem.entity.UserInvitation
import pt.isel.daw.imsystem.service.UserInvitationService

@RestController
@RequestMapping("/api/user-invitations")
class UserInvitationController(
    @Autowired private val userInvitationService: UserInvitationService
) {
    @PostMapping
    fun createUserInvitation(@RequestBody username: String): UserInvitation {
        return userInvitationService.createUserInvitation(username)
    }
}