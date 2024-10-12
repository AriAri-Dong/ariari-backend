package com.ariari.ariari.commons.image;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class DeletedImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deleted_image_id")
    private Long id;

    private String imagePath;

    @CreationTimestamp
    private LocalDateTime createdDateTime; // 논리 삭제 처리된 날짜,시간

}
