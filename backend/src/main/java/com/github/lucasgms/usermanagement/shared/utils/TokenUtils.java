package com.github.lucasgms.usermanagement.shared.utils;

import java.time.Instant;

public class TokenUtils {
    static public Instant createExpiredDateToken(int seconds) {
        return Instant.now().plusSeconds(seconds);
    }
}
