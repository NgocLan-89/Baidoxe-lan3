package com.example.baidoxe.Provide;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LuuAnh {

    // Tiêm thư mục upload từ file application.properties
    @Value("${upload.dir}")
    private String uploadDir;

    public String LuuAnhUser(MultipartFile duongDanAnh) {
        if (duongDanAnh.isEmpty()) {
            return null; // Trả về null hoặc xử lý lỗi nếu không có tệp được chọn
        }

        try {
            // Tạo tên tệp duy nhất bằng dấu thời gian hiện tại
            String fileName = System.currentTimeMillis() + "_" + duongDanAnh.getOriginalFilename();

            // Chuyển đổi đường dẫn tương đối từ application.properties sang đường dẫn tuyệt đối
            Path filePath = Paths.get(uploadDir).toAbsolutePath().resolve(fileName);

            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(filePath.getParent());

            // Lưu tệp vào hệ thống
            Files.write(filePath, duongDanAnh.getBytes());

            // Trả về đường dẫn tương đối của tệp
            return fileName;
        } catch (IOException e) {
            e.printStackTrace(); // Ghi lại lỗi nếu có
            return null; // Hoặc trả về thông báo lỗi phù hợp
        }
    }
}