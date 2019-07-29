package lean.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import org.nutz.repo.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;

public class JWTTestTokens {

    public static SecretKey generalKey() {
        byte[] var0 = Base64.decode("SECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRET");
        return new SecretKeySpec(var0, 0, var0.length, "HmacSHA256");
    }

    public static void main(String[] args) {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SecretKey key = generalKey();
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        Date exp = new Date(System.currentTimeMillis() + 1000000);
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("roles", "admin");
        String jws = Jwts.builder()
                .setClaims(hm)
                .signWith(key, algorithm).compact();
        System.out.println("Token:");
        System.out.println(jws);
        String encoded = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Shared secret:");
        System.out.println(encoded);
    }
}