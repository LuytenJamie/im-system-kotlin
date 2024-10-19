package pt.isel.daw.imsystem.entity

import jakarta.persistence.*

@Entity
@Table(name = "channel_user_access")
data class ChannelUserAccess(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "channel_id")
    val channel: Channel,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level")
    val accessLevel: AccessLevel
)
