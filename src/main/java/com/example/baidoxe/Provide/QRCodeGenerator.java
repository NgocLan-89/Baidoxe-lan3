package com.example.baidoxe.Provide;

import org.springframework.stereotype.Component;
@Component
public class QRCodeGenerator {
    public String generateQRCodeUrl(String qrCodeData) {
        // Sử dụng API tạo mã QR khác
        return "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + qrCodeData;
    }
}