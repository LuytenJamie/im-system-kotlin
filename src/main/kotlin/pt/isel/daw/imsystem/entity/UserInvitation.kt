package pt.isel.daw.imsystem.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_invitations")
data class UserInvitation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val username: String,

    val token: String,

    var isUsed: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "creator_id")
    val creator: User?
)