package com.example.baidoxe.service;

import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FirebaseService {

    public Map<String, Object> getDataFromFirebase(String path) throws InterruptedException {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        final Object[] result = {null};

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result[0] = dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Firebase read failed: " + error.getMessage());
            }
        });

        // Đợi cho đến khi dữ liệu được tải
        Thread.sleep(2000);
        return (Map<String, Object>) result[0];
    }
}
