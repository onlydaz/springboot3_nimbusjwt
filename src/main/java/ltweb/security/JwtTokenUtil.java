package ltweb.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

import java.util.Date;

public class JwtTokenUtil {
    private static final String SECRET = "supersecretkey"; // Thay bằng key mạnh hơn
    private static final long EXPIRATION = 86400000; // 1 ngày

    public static String generateToken(String email) throws JOSEException {
        JWSSigner signer = new MACSigner(SECRET);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    public static boolean validateToken(String token) throws JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET);
        return signedJWT.verify(verifier) && !isTokenExpired(signedJWT);
    }

    public static String getEmailFromToken(String token) throws JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet().getSubject();
    }

    private static boolean isTokenExpired(SignedJWT signedJWT) throws JOSEException {
        return signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date());
    }
}
