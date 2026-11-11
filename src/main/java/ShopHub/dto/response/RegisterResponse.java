package ShopHub.dto.response;

import java.util.List;
import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String email,
        String fullName,
        String phone,
        String status,
        List<String> roles
) {}
