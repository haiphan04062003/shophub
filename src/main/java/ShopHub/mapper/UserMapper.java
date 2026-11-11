package ShopHub.mapper;

import ShopHub.constant.Role;
import ShopHub.constant.UserStatus;
import ShopHub.dto.request.RegisterRequest;
import ShopHub.dto.response.RegisterResponse;
import ShopHub.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class UserMapper {

    // Chuyển dữ liệu người dùng gửi lên thành entity User
    // (chưa có passwordHash, Service sẽ gán sau)
    public User toEntity(RegisterRequest request) {
        User user = new User();
        user.setFullName(request.getFullName().trim());
        user.setEmail(normalizeEmail(request.getEmail()));

        String phone = request.getPhone();
        user.setPhone(phone == null || phone.isBlank() ? null : phone.trim());

        user.setRole(Role.CUSTOMER);
        user.setStatus(UserStatus.ACTIVE);
        return user;
    }

    // Chuyển entity User thành dữ liệu trả về cho người dùng
    public RegisterResponse toRegisterResponse(User user) {
        return new RegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getStatus().name(),
                List.of(user.getRole().name())
        );
    }

    // Chuẩn hóa email: bỏ khoảng trắng đầu/cuối, đổi thành chữ thường
    public String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}