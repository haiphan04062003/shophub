package ShopHub.service;

import ShopHub.dto.request.RegisterRequest;
import ShopHub.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
}