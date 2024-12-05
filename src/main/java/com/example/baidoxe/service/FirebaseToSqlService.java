package com.example.baidoxe.service;

import com.example.baidoxe.repository.ViTriDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

@Service
public class FirebaseToSqlService {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private ViTriDoRepository viTriDoRepository;

    // Logger để theo dõi log
    private static final Logger logger = Logger.getLogger(FirebaseToSqlService.class.getName());

    // Các tên bãi đỗ cần cập nhật và ID của chúng
    private static final Map<String, Integer> BAI_DO = Map.of(
            "Baido2", 2,  // Bãi đỗ 2 có ID = 2
            "Baido3", 3   // Bãi đỗ 3 có ID = 3
    );

    @Scheduled(fixedRate = 10000) // Cập nhật mỗi 10 giây
    public void updateDataFromFirebase() {
        for (Map.Entry<String, Integer> entry : BAI_DO.entrySet()) {
            String baiDoName = entry.getKey();
            int baiDoId = entry.getValue();

            try {
                // Lấy dữ liệu từ Firebase cho từng bãi đỗ
                Map<String, Object> firebaseData = firebaseService.getDataFromFirebase(baiDoName);

                // Kiểm tra và xử lý dữ liệu từ Firebase
                if (firebaseData != null && !firebaseData.isEmpty()) {
                    logger.info("Dữ liệu nhận từ Firebase cho " + baiDoName + ": " + firebaseData);

                    // Duyệt qua các mục trong dữ liệu Firebase
                    for (Map.Entry<String, Object> vitriEntry : firebaseData.entrySet()) {
                        String chiTietViTri = vitriEntry.getKey().replace("Vitrido", ""); // Lấy số thứ tự của Vitrido
                        int status;

                        try {
                            // Chuyển giá trị từ Firebase sang int
                            status = Integer.parseInt(vitriEntry.getValue().toString());
                        } catch (NumberFormatException e) {
                            // Xử lý trường hợp giá trị không phải là số
                            logger.warning("Giá trị không hợp lệ cho " + baiDoName + ": " + vitriEntry.getValue());
                            continue;
                        }

                        // Log lại thông tin sẽ cập nhật
                        logger.info("Cập nhật ViTriDo với chiTietViTri: " + chiTietViTri + " và status: " + status + " cho bãi đỗ " + baiDoName);

                        // Cập nhật dữ liệu vào bảng ViTriDo cho từng bãi đỗ
                        viTriDoRepository.updateStatus(status, baiDoId, Integer.parseInt(chiTietViTri));
                    }
                } else {
                    logger.warning("Không có dữ liệu từ Firebase hoặc dữ liệu rỗng cho " + baiDoName);
                }
            } catch (Exception e) {
                // Log lỗi nếu có
                logger.severe("Lỗi khi cập nhật dữ liệu từ Firebase cho " + baiDoName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
