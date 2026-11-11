package ShopHub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400: dữ liệu không hợp lệ (thiếu tên, email sai, mật khẩu yếu...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> details = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(f -> details.putIfAbsent(f.getField(), f.getDefaultMessage()));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 400);
        body.put("error", "VALIDATION_ERROR");
        body.put("details", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 400: JSON sai cú pháp hoặc body rỗng
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 400);
        body.put("error", "MALFORMED_REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 409: email đã tồn tại
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(EmailAlreadyExistsException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 409);
        body.put("error", "EMAIL_ALREADY_EXISTS");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}