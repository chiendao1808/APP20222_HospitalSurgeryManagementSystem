package com.app20222.app20222_backend.security.jwt;

import com.app20222.app20222_backend.entities.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@Getter
public class JwtUtils {

    @Value("@{jwt.accessTokenExpiredMs}")
    public String ACCESS_TOKEN_EXPIRED_IN_MS;

    @Value("@{jwt.refreshTokenExpiredMs}")
    public String REFRESH_TOKEN_EXPIRED_IN_MS;

    @Value("${jwt.secretKey}")
    public String SECRET_KEY;

    /**
     * Generate Json Token Web
     * @param user : login user
     * @return : JWT
     */
    public String generateJwt(User user){
        long currentMs = System.currentTimeMillis();
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("username", user.getUsername());
        claims.put("userId", user.getId());
        claims.put("generatedTimeMs", System.currentTimeMillis());
        return Jwts.builder().setSubject(user.getUsername())
                .addClaims(claims)
                .setIssuedAt(new Date(currentMs))
                .setExpiration(new Date(currentMs + Long.valueOf(ACCESS_TOKEN_EXPIRED_IN_MS)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact()
                .toString();
    }

    /**
     * Get claims in jwt
     * @param jwt : token
     * @return : claims
     */
    public Claims getClaims(String jwt){
        try{
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(jwt).getBody();
        }catch (Exception exception)
        {
            log.error("Get claims frm jwt error");
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Get user name from jwt
     * @param jwt : token
     * @return : username
     */
    public String getUsernameFromJwt(String jwt){
        try{
            Claims claims = getClaims(jwt);
            return claims.get("username", String.class);
        }catch (Exception ex){
            log.error("Get username frm jwt error");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get user id from jwt
     * @param jwt : token
     * @return : id
     */
    public Long getUserIdFromJwt(String jwt){
        try{
            Claims claims = getClaims(jwt);
            return claims.get("userId", Long.class);
        }catch (Exception ex){
            log.error("Get userid frm jwt error");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Valid whether
     * @param jwt : token
     * @return : expired : true - not expired : false
     */
    public Boolean validateToken(String jwt){
        try{
            Claims claims = getClaims(jwt);
            Date now = new Date();
            return Objects.nonNull(claims) && claims.getExpiration().after(now) ? false : true;
        }catch (Exception exception)
        {
            log.error("Check jwt expired errot");
            exception.printStackTrace();
            return true;
        }
    }
}
