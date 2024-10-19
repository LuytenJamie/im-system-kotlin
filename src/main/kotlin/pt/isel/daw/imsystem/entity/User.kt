package pt.isel.daw.imsystem.entity
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0,

    @Column(nullable = false, unique = true)
    private var username: String,

    @Column(nullable = false)
    private var password: String,

    @Enumerated(EnumType.STRING)
    private var role: Role

) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("USER"))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }
    fun getRole(): Role {
        return role
    }
    fun getId(): Long {
        return id
    }

    override fun isEnabled(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true
}

