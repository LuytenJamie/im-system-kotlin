package pt.isel.daw.imsystem.entity

import jakarta.persistence.*

@Entity
@Table(name = "channel_invitations")
data class ChannelInvitation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val code: String,

    @ManyToOne
    @JoinColumn(name = "channel")
    val channel: Channel,

    @ManyToOne
    @JoinColumn(name = "inviter")
    val inviter: User,

    @Column(nullable = false)
    var isUsed: Boolean,

    @Column(nullable = false)
    val isReadOnly: Boolean
)