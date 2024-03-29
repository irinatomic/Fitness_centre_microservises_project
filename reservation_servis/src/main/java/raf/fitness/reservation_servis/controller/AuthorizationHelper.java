package raf.fitness.reservation_servis.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthorizationHelper {

    public static Long extractIdFromToken(String authorization) {
        // Extract the token part after "Bearer "
        String token = authorization.substring(7);

        // Decode the token to extract information
        Claims claims = Jwts.parser().parseClaimsJws(token).getBody();

        // Ensure the claim is retrieved as a Long
        Long id = claims.get("id", Long.class);
        return id;
    }
}
