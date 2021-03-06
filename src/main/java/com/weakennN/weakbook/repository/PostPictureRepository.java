package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPictureRepository extends JpaRepository<PostPicture, Long> {
}
