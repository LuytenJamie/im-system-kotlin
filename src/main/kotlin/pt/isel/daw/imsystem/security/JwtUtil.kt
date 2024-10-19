package pt.isel.daw.imsystem.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import pt.isel.daw.imsystem.entity.User
import java.security.Key
import java.util.Base64
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    fun generateToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getSigningKey(): Key {
        val keyBytes = Base64.getDecoder().decode(secretKey)
        return SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.jcaName)
    }

    fun extractUsername(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String, username: String): Boolean {
        val tokenUsername = extractUsername(token)
        return (tokenUsername == username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.expiration
        return expiration.before(Date())
    }
}
