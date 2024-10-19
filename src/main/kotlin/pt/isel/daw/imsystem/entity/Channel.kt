package pt.isel.daw.imsystem.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "channels")
data class Channel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val name: String,

    @Column(name = "is_public")
    val isPublic: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "owner")
    val owner: User,

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY)
    val userAccesses: Set<ChannelUserAccess> = setOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "channel_users",
        joinColumns = [JoinColumn(name = "channel_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val users: MutableSet<User> = mutableSetOf(),


    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun isInviterAuthorized(inviter: User): Boolean {
        return if (inviter == owner) {
            true
        } else {
            users.contains(inviter) && hasReadWriteAccess(inviter)
        }
    }
    fun hasReadWriteAccess(user: User): Boolean {
        return userAccesses.any { it.user == user && it.accessLevel == AccessLevel.READ_WRITE }
    }
    fun addUser(user: User) {
        users.add(user)
    }
}

