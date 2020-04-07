package com.shepard.callcenter.controller;

import com.shepard.callcenter.config.CallcenterProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex Misha
 */
@RestController
@RequestMapping("/downloadCallRecord/")
public class DownloadCallRecordController {
    private final Logger log = LoggerFactory.getLogger(DownloadCallRecordController.class);
    private final CallcenterProperties callcenterProperties;

    public DownloadCallRecordController(CallcenterProperties callcenterProperties) {
        this.callcenterProperties = callcenterProperties;
    }

    @GetMapping(value = "/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {
        final Path path = Paths.get(callcenterProperties.getCallRecordsPath() + fileName);
        if (!Files.exists(path)) {
            log.warn("Attempt to download non-existing file " + path);
            return ResponseEntity.notFound().build();
        }
        final File file = path.toFile();

        try {
            final InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .body(resource);
        } catch (FileNotFoundException e) {
            log.error("Suspicious file not found", e);
            return ResponseEntity.notFound().build();
        }
    }
}
