package ShopHub.entity;

import ShopHub.constant.Role;
import ShopHub.constant.UserStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

// Class này là 1 bảng trong database
@Entity
// Tên bảng là "users". Cột email không được trùng (yêu cầu "chống trùng bằng unique index")
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uk_users_email", columnNames = "email")
)
public class User {

    // Khóa chính, hệ thống tự sinh, không cần tự đặt
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Họ tên: bắt buộc có, tối đa 100 ký tự
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    // Email: bắt buộc có, tối đa 254 ký tự
    @Column(nullable = false, length = 254)
    private String email;

    // Mật khẩu đã mã hóa (không bao giờ lưu mật khẩu gốc)
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // Số điện thoại: không có nullable = false nên có thể bỏ trống
    @Column(length = 20)
    private String phone;

    // Vai trò, mặc định là CUSTOMER. Lưu dạng chữ trong DB
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.CUSTOMER;

    // Trạng thái, mặc định là ACTIVE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;

    // Thời điểm tạo tài khoản
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // ===== Getter / Setter: để code khác đọc và gán giá trị cho các trường trên =====

    public UUID getId() { return id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
}