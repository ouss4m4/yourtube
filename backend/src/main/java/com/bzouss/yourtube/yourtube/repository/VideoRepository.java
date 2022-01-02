package com.bzouss.yourtube.yourtube.repository;

import com.bzouss.yourtube.yourtube.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video,String> {

}
