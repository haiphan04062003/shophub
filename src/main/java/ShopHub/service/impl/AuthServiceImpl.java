package ShopHub.service.impl;

import ShopHub.dto.request.RegisterRequest;
import ShopHub.dto.response.RegisterResponse;
import ShopHub.entity.User;
import ShopHub.exception.EmailAlreadyExistsException;
import ShopHub.mapper.UserMapper;
import ShopHub.repository.UserRepository;
import ShopHub.service.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        // 1. Chuẩn hóa email + tạo User (ACTIVE, CUSTOMER)
        User user = userMapper.toEntity(request);

        // 2. Kiểm tra email đã tồn tại chưa -> báo lỗi 409
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        // 3. Hash mật khẩu
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // 4. Lưu vào DB. Unique index chặn trường hợp 2 người đăng ký cùng email cùng lúc
        try {
            User saved = userRepository.saveAndFlush(user);
            return userMapper.toRegisterResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
    }
}