package com.app20222.app20222_backend.security.jwt;

import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.security.constants.SecurityConstants;
import com.app20222.app20222_backend.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
@Slf4j
@Getter
public class JwtUtils {

    @Value("${jwt.accessTokenExpiredMs}")
    public String ACCESS_TOKEN_EXPIRED_IN_MS;

    @Value("${jwt.refreshTokenExpiredMs}")
    public String REFRESH_TOKEN_EXPIRED_IN_MS;

    @Value("${jwt.secretKey}")
    public String SECRET_KEY;

    /**
     * Generate Json Token Web
     * @param user : login user
     * @return : JWT
     */
    public String generateJwt(User user){
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("username", user.getUsername());
        claims.put("userId", user.getId());
        claims.put("generatedTimeMs", System.currentTimeMillis());
        return Jwts.builder().setSubject(user.getUsername())
                .addClaims(claims)
                .setIssuedAt(DateUtils.getCurrentDateTime())
                .setExpiration(new Date(DateUtils.getCurrentDateTime().getTime() + Long.parseLong(ACCESS_TOKEN_EXPIRED_IN_MS)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
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
     * @return : valid : true - not valid : false
     */
    public Boolean validateToken(String jwt){
        try{
            Claims claims = getClaims(jwt);
            Date now = DateUtils.getCurrentDateTime();
            return !Objects.nonNull(claims) && !claims.getExpiration().after(now);
        }catch (Exception exception)
        {
            log.error("Check jwt expired error");
            exception.printStackTrace();
            return true;
        }
    }

    /**
     * Generate Refresh Token
     * @return : Refresh Token
     */
    public String generateRefreshToken(User user){
        return Base64.getEncoder().withoutPadding().encodeToString((user.getUsername() + user.getEmail()).getBytes());
    }

    /**
     * Get Token from Request Header
     * Authorization  = Bearer  " " + Token
     */
    public String getTokenFromRequest(HttpServletRequest request){
        String authHeader = request.getHeader(SecurityConstants.AUTH_HEADER);
        return Objects.nonNull(authHeader) ? authHeader.split(" ")[1] : null;
    }
}
