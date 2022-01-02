package com.bzouss.yourtube.yourtube.service;

import com.bzouss.yourtube.yourtube.dto.UploadVideoResponse;
import com.bzouss.yourtube.yourtube.dto.VideoDto;
import com.bzouss.yourtube.yourtube.model.Video;
import com.bzouss.yourtube.yourtube.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository  videoRepo;

    public UploadVideoResponse uploadVideo(MultipartFile file){
        // upload file to aws s3
        String videoUrl = s3Service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        // save video metadata to mongodb
        var savedVideo = videoRepo.save(video);
        return new UploadVideoResponse(savedVideo.getId(),savedVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        // Find the video by videoid
        Video savedVideo = getVideoById(videoDto.getId());
        //Map videoDto fields to video
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());

        //save
        videoRepo.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        var savedVideo = getVideoById(videoId);
        var thumbnailUrl = s3Service.uploadFile(file);
        savedVideo.setThumbnailUrl(thumbnailUrl);
        videoRepo.save(savedVideo);
        return thumbnailUrl;
    }

    private Video getVideoById(String videoId) {
        return videoRepo.findById(videoId)
                .orElseThrow(()->
                        new IllegalArgumentException("Cannot find video by provided id" + videoId));
    }
}
