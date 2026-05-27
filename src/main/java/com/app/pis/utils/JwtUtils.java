package com.app.pis.utils;

import com.app.pis.entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private byte[] secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = secret.getBytes(StandardCharsets.UTF_8);
    }

    public String generateAccessToken(User user) {
        try {
            Date now = new Date();
            Date expiryDate = new Date(
                    now.getTime() + expiration
            );
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .claim("id", user.getId())
                    .claim("type", "ACCESS_TOKEN")
                    .claim("roles", List.of(user.getRole()))
                    .issueTime(now)
                    .expirationTime(expiryDate)
                    .build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(new MACSigner(secretKey));
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String generateRefreshToken(User user) {
        try {
            Date now = new Date();
            Date expiryDate = new Date(
                    now.getTime() + 1000L * 60 * 60 * 24 * 30
            );
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .claim("type", "REFRESH_TOKEN")
                    .issueTime(now)
                    .expirationTime(expiryDate)
                    .build();
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claimsSet
            );
            signedJWT.sign(new MACSigner(secretKey));
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generateForgotPasswordToken(User user) {
        try {
            Date now = new Date();
            Date expiryDate = new Date(
                    now.getTime() + 1000L * 60 * 15
            );

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .claim("id", user.getId())
                    .claim("type", "FORGOT_PASSWORD")
                    .issueTime(now)
                    .expirationTime(expiryDate)
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claimsSet
            );

            signedJWT.sign(new MACSigner(secretKey));

            return signedJWT.serialize();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract username
     */
    public String getUsernameFromToken(String token) {

        try {

            JWTClaimsSet claimsSet = extractClaims(token);

            return claimsSet.getSubject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract claims
     */
    public JWTClaimsSet extractClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier =
                    new MACVerifier(secretKey);
            boolean valid = signedJWT.verify(verifier);
            if (!valid) {
                throw new RuntimeException("Invalid token");
            }
            return signedJWT.getJWTClaimsSet();

        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {

        try {

            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier =
                    new MACVerifier(secretKey);

            boolean valid = signedJWT.verify(verifier);

            if (!valid) {
                return false;
            }

            Date expirationDate =
                    signedJWT.getJWTClaimsSet()
                            .getExpirationTime();

            return expirationDate.after(new Date());

        } catch (Exception e) {
            return false;
        }
    }
}