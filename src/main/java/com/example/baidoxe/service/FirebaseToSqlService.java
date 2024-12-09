package com.example.baidoxe.service;

import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.dto.ViTriDoDTO;
import com.example.baidoxe.models.DatCho;
import com.example.baidoxe.repository.DatChoRepository;
import com.example.baidoxe.repository.ViTriDoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
public class FirebaseToSqlService {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private ViTriDoRepository viTriDoRepository;

    @Autowired
    private DatChoRepository datChoRepository;

    @Autowired
    private DatChoService datChoService;

    // Logger để theo dõi log
    private static final Logger logger = Logger.getLogger(FirebaseToSqlService.class.getName());

    // Các tên bãi đỗ cần cập nhật và ID của chúng
    private static final Map<String, Integer> BAI_DO = Map.of(
            "Baido2", 2,  // Bãi đỗ 2 có ID = 2
            "Baido3", 3   // Bãi đỗ 3 có ID = 3
    );


    // Hàm tạo mã QR mới (có thể tùy chỉnh logic tạo mã)
    private String generateNewQRCode() {
        return "QR_" + UUID.randomUUID().toString().substring(0, 8);
    }


    @Scheduled(fixedRate = 10000) // Cập nhật mỗi 10 giây
    public void updateDataFromFirebase() {
        for (Map.Entry<String, Integer> entry : BAI_DO.entrySet()) {
            String baiDoName = entry.getKey();
            int baiDoId = entry.getValue();

            try {
                Map<String, Object> firebaseData = firebaseService.getDataFromFirebase(baiDoName);

                if (firebaseData != null && !firebaseData.isEmpty()) {
                    logger.info("Dữ liệu nhận từ Firebase cho " + baiDoName + ": " + firebaseData);
                    processServoData(firebaseData, baiDoId);

                    // Đọc dữ liệu Vitrido
                    for (Map.Entry<String, Object> vitriEntry : firebaseData.entrySet()) {
                        String key = vitriEntry.getKey();
                        if (!key.startsWith("Vitrido")) {
                            // Bỏ qua nếu không phải là Vitrido
                            continue;
                        }

                        String chiTietViTri = key.replace("Vitrido", "");
                        int statusFromFirebase;

                        try {
                            statusFromFirebase = Integer.parseInt(vitriEntry.getValue().toString());
                        } catch (NumberFormatException e) {
                            logger.warning("Giá trị không hợp lệ cho " + baiDoName + ": " + vitriEntry.getValue());
                            continue;
                        }

                        logger.info("Đang xử lý cho ViTriDo: " + chiTietViTri + " với trạng thái từ Firebase: " + statusFromFirebase);

                        int currentStatusInSql = viTriDoRepository.getStatus(baiDoId, Integer.parseInt(chiTietViTri));
                        logger.info("Trạng thái hiện tại trong SQL: " + currentStatusInSql + " Baido " + baiDoName);

                        if (currentStatusInSql == 3) {
                            if (statusFromFirebase == 1) {
                                // Lấy thời gian đăng ký ra từ DatCho
                                LocalDateTime dangKyGioRa = viTriDoRepository.getDangKyGioRa(baiDoId, Integer.parseInt(chiTietViTri));

                                if (dangKyGioRa != null && System.currentTimeMillis() > dangKyGioRa.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                                    logger.info("Thời gian hiện tại (millis): " + System.currentTimeMillis()+ " Baido " + baiDoName + " Vitri " + chiTietViTri);
                                    logger.info("Thời gian Đăng Ký Giờ Ra (millis): " + dangKyGioRa.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + " Baido " + baiDoName + " Vitri " + chiTietViTri);
                                    logger.info("Cập nhật ViTriDo với chiTietViTri: " + chiTietViTri + " Baido " + baiDoName + " thành status = 1 vì thời gian hiện tại lớn hơn thời gian đăng ký ra.");
                                    viTriDoRepository.updateStatus(1, baiDoId, Integer.parseInt(chiTietViTri));
                                } else {
                                    logger.info("Bỏ qua cập nhật cho ViTriDo với chiTietViTri: " + chiTietViTri + " Baido " + baiDoName +  " vì Firebase = 1 và thời gian hiện tại chưa đủ.");
                                }
                            } else if (statusFromFirebase == 2) {
                                logger.info("Cập nhật ViTriDo với chiTietViTri: " + chiTietViTri + " Baido " + baiDoName + " thành status = 2.");
                                viTriDoRepository.updateStatus(2, baiDoId, Integer.parseInt(chiTietViTri));
                            }
                        }else if (currentStatusInSql == 2) {
                            LocalDateTime dangKyGioRa = viTriDoRepository.getDangKyGioRa(baiDoId, Integer.parseInt(chiTietViTri));
                            if (statusFromFirebase == 1) {
                                logger.info("Cập nhật ViTriDo với chiTietViTri: " + chiTietViTri + " từ status = 2 thành status = 1.");
                                viTriDoRepository.updateStatus(1, baiDoId, Integer.parseInt(chiTietViTri));
                            } else if (dangKyGioRa != null) {
                                long currentTimeMillis = System.currentTimeMillis();
                                ZonedDateTime dangKyGioRaZoned = dangKyGioRa.atZone(ZoneId.of("Asia/Ho_Chi_Minh")); // Đảm bảo sử dụng múi giờ đúng
                                long dangKyGioRaMillis = dangKyGioRaZoned.toInstant().toEpochMilli();

                                logger.info("Thời gian hiện tại (millis): " + currentTimeMillis + " Baido " + baiDoName + " Vitri " + chiTietViTri);
                                logger.info("Thời gian Đăng Ký Giờ Ra (millis): " + dangKyGioRaMillis + " Baido " + baiDoName + " Vitri " + chiTietViTri);
                                logger.info("Thời gian hiện tại (local): " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                logger.info("Thời gian Đăng Ký Giờ Ra (local): " + dangKyGioRa.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                 logger.info("Điều kiện kiểm tra: " + (System.currentTimeMillis() > dangKyGioRaMillis )+ " Baido " + baiDoName + " Vitri " + chiTietViTri);

                                if (System.currentTimeMillis() > dangKyGioRaMillis) {
                                    logger.info("Thời gian đăng ký ra đã hết hạn và trạng thái = 2. Đang thêm đặt chỗ mới.");

                                    datChoService.extendExpiredReservations();
                                    logger.info("thêm đặt chỗ mới.");
                                }
                            }else {
                                logger.warning("Không tìm thấy thời gian Đăng Ký Giờ Ra cho ViTriDo: " + chiTietViTri);
                            }
                        } else {
                            logger.info("Không có thay đổi nào cần thực hiện cho trạng thái Firebase: " + statusFromFirebase);
                        }
                    }
                } else {
                    logger.warning("Không có dữ liệu từ Firebase hoặc dữ liệu rỗng cho " + baiDoName);
                }
            } catch (Exception e) {
                logger.severe("Lỗi khi cập nhật dữ liệu từ Firebase cho " + baiDoName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
//ĐỌC DU LIEU SERVO
private void processServoData(Map<String, Object> firebaseData, int baiDoId) {
    // Đọc và ghi log dữ liệu Servo1
    Object servo1Data = firebaseData.get("Servo1");
    if (servo1Data instanceof Number) {
        int statusServo1 = ((Number) servo1Data).intValue();
        logger.info("Bãi đỗ " + baiDoId + ": Trạng thái Servo1: " + statusServo1);
    } else {
        logger.warning("Bãi đỗ " + baiDoId + ": Dữ liệu không hợp lệ cho Servo1: " + servo1Data);
    }

    // Đọc và ghi log dữ liệu Servo2
    Object servo2Data = firebaseData.get("Servo2");
    if (servo2Data instanceof Number) {
        int statusServo2 = ((Number) servo2Data).intValue();
        logger.info("Bãi đỗ " + baiDoId + ": Trạng thái Servo2: " + statusServo2);
    } else {
        logger.warning("Bãi đỗ " + baiDoId + ": Dữ liệu không hợp lệ cho Servo2: " + servo2Data);
    }
}




}