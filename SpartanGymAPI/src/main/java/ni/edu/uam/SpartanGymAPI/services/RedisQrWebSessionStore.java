package ni.edu.uam.SpartanGymAPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/** Redis-backed QR session state; failure is not silently downgraded to local memory. */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.qr.session-store", havingValue = "redis")
public class RedisQrWebSessionStore implements QrWebSessionStore {
    private static final String PREFIX = "spartangym:qr:web:";
    private static final Duration RETENTION = Duration.ofSeconds(600);
    private final StringRedisTemplate redis;

    @Override
    public void guardar(UUID sessionId, String tokenHash, Instant expira) {
        Duration ttl = Duration.between(Instant.now(), expira.plus(RETENTION));
        if (ttl.isNegative() || ttl.isZero()) {
            throw new IllegalArgumentException("La sesion QR ya vencio");
        }
        redis.opsForValue().set(PREFIX + sessionId, tokenHash + "|" + expira.toEpochMilli(), ttl);
    }

    @Override
    public Optional<Session> buscar(UUID sessionId) {
        String data = redis.opsForValue().get(PREFIX + sessionId);
        if (data == null) {
            return Optional.empty();
        }
        int separator = data.lastIndexOf('|');
        if (separator <= 0 || separator >= data.length() - 1) {
            throw new IllegalStateException("Estado QR invalido en Redis");
        }
        try {
            return Optional.of(new Session(
                    data.substring(0, separator),
                    Instant.ofEpochMilli(Long.parseLong(data.substring(separator + 1)))));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Expiracion QR invalida en Redis", e);
        }
    }
}
