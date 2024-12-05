package com.example.baidoxe.service;

import com.example.baidoxe.repository.ViTriDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
                Map<String, Object> firebaseData = firebaseService.getDataFromFirebase(baiDoName);

                if (firebaseData != null && !firebaseData.isEmpty()) {
                    logger.info("Dữ liệu nhận từ Firebase cho " + baiDoName + ": " + firebaseData);

                    for (Map.Entry<String, Object> vitriEntry : firebaseData.entrySet()) {
                        String chiTietViTri = vitriEntry.getKey().replace("Vitrido", "");
                        int statusFromFirebase;

                        try {
                            statusFromFirebase = Integer.parseInt(vitriEntry.getValue().toString());
                        } catch (NumberFormatException e) {
                            logger.warning("Giá trị không hợp lệ cho " + baiDoName + ": " + vitriEntry.getValue());
                            continue;
                        }

                        int currentStatusInSql = viTriDoRepository.getStatus(baiDoId, Integer.parseInt(chiTietViTri));
                        if (statusFromFirebase == currentStatusInSql) {
                            logger.info("Giữ nguyên trạng thái cho ViTriDo: " + chiTietViTri + " vì trạng thái trong SQL và Firebase giống nhau (" + currentStatusInSql + ").");
                            continue;
                        }

                        if (currentStatusInSql == 3) {
                            if (statusFromFirebase == 1) {
                                // Lấy thời gian đăng ký ra từ DatCho
                                LocalDateTime dangKyGioRa = viTriDoRepository.getDangKyGioRa(baiDoId, Integer.parseInt(chiTietViTri));


                                if (dangKyGioRa != null && System.currentTimeMillis() > dangKyGioRa.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                                    logger.info("Cập nhật ViTriDo với chiTietViTri: " + chiTietViTri + " thành status = 1 vì thời gian hiện tại lớn hơn thời gian đăng ký ra.");
                                    viTriDoRepository.updateStatus(1, baiDoId, Integer.parseInt(chiTietViTri));
                                } else {
                                    logger.info("Bỏ qua cập nhật cho ViTriDo với chiTietViTri: " + chiTietViTri + " vì Firebase = 1 và thời gian hiện tại chưa đủ.");
                                }

                            } else if (statusFromFirebase == 2) {
                                logger.info("Cập nhật ViTriDo với chiTietViTri: " + chiTietViTri + " thành status = 2.");
                                viTriDoRepository.updateStatus(2, baiDoId, Integer.parseInt(chiTietViTri));
                            } else {
                                logger.info("Bỏ qua cập nhật cho ViTriDo với chiTietViTri: " + chiTietViTri + " vì không thỏa mãn điều kiện.");
                            }
                        } else {
                            logger.info("Cập nhật trạng thái bình thường cho ViTriDo với chiTietViTri: " + chiTietViTri);
                            viTriDoRepository.updateStatus(statusFromFirebase, baiDoId, Integer.parseInt(chiTietViTri));
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


}
