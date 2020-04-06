package com.shepard.callcenter.controller;

import com.shepard.callcenter.config.CallcenterProperties;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alex Misha
 */
class DownloadCallRecordControllerTest {
    private static final CallcenterProperties CALLCENTER_PROPERTIES = new CallcenterProperties();
    private static final String TEST_STRING = "testData";

    private final DownloadCallRecordController controller = new DownloadCallRecordController(CALLCENTER_PROPERTIES);

    @BeforeAll
    static void beforeAll() {
        CALLCENTER_PROPERTIES.setCallRecordsPath("/tmp/");
    }

    @Test
    void downloadOk() throws IOException {
        final Path testCallcenterFile = Files.createTempFile("testCallcenterFile", ".tmp");
        testCallcenterFile.toFile().deleteOnExit();
        Files.writeString(testCallcenterFile, TEST_STRING);

        final ResponseEntity<Resource> download = controller.download(testCallcenterFile.getFileName().toString());

        assertThat(download.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(download.getBody()).isNotNull();
        assertThat(download.getBody().contentLength()).isEqualTo(testCallcenterFile.toFile().length());
    }

    @Test
    void downloadNotFound() throws IOException {
        final ResponseEntity<Resource> download = controller.download("notFoundFile");

        assertThat(download.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}