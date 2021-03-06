package com.maetdolvlog.maetdolvlog.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class VideoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/video")
    public ResponseEntity<ResourceRegion> getVideo(@RequestHeader HttpHeaders headers) throws IOException {

        UrlResource video = new UrlResource("file:/Users/acandiatale/IntelijWorkspace/video/KakaoTalk_Video_2021-10-16-02-20-29.mp4");
        ResourceRegion resourceRegion;

        final long chunkSize = 1000000L;
        long contentLength = video.contentLength();

        Optional<HttpRange> optional = headers.getRange().stream().findFirst();
        HttpRange httpRange;

        logger.info("range : " + optional.isPresent());
        if (optional.isPresent()) {
            httpRange = optional.get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            logger.info("start : {} end: {}", start, end);
            long rangeLength = Long.min(chunkSize, end - start + 1);
            resourceRegion = new ResourceRegion(video, start, rangeLength);
        } else {
            long rangeLength = Long.min(chunkSize, contentLength);
            resourceRegion = new ResourceRegion(video, 0, rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);
    }
}
