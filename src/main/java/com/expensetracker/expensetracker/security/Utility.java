package com.expensetracker.expensetracker.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

public class Utility {




    public static String generateToken(String userName){
        Instant now = Instant.now();

        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(now.plusMillis(SecurityConstants.EXPIRATION_TIME)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.getTokenSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static boolean isTokenValid(String token) {
        boolean returnValue = true;
try{
        Claims claims= Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String user=claims.getSubject();
        } catch (ExpiredJwtException ex) {
            returnValue = false;
        }

        return returnValue;
    }


}
