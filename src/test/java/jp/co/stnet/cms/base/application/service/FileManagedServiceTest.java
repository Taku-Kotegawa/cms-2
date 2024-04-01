package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.FileManagedRepository;
import jp.co.stnet.cms.base.domain.enums.FileStatus;
import jp.co.stnet.cms.base.domain.enums.FileType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FileManagedServiceTest {

    @Autowired
    FileManagedService target;

    @Autowired
    FileManagedRepository repository;


    private MultipartFile createMultipartFile() {

        Path path = Paths.get("src/test/resources/input.txt");
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        return result;
    }


    @Nested
    class store_MultipartFile {

        @Test
        void test001() throws IOException {

            // 実行
            var actual = target.store(createMultipartFile(), FileType.DEFAULT);

            // 検証
            assertThat(actual.getOriginalFilename()).isEqualTo("file.txt");

        }
    }


    @Nested
    class Store_Path {

        @Test
        void test001() throws IOException {
            // 実行
            var actual = target.store(Path.of("src/test/resources/input.txt"), FileType.DEFAULT);

            // 検証
            assertThat(actual.getOriginalFilename()).isEqualTo("input.txt");
        }

    }

    @Nested
    class getFile {

        @Test
        void test001() throws IOException {

            var fileManaged = target.store(createMultipartFile(), null);

            var actual = target.getFile(fileManaged.getUuid());

        }

    }

    @Nested
    class findById {

        @Test
        void test001() throws IOException {

            var expected = target.store(createMultipartFile(), null);

            var actual = target.findById(expected.getUuid());

            assertThat(actual).isEqualTo(expected);

        }
    }


    @Nested
    class permanent {

        @Test
        void test001() throws IOException {
            var expected = target.store(createMultipartFile(), null);
            target.permanent(expected.getUuid());
            var actual = target.findById(expected.getPrimaryKey());
            assertThat(actual.getStatus()).isEqualTo(FileStatus.PERMANENT.getCodeValue());
        }

    }

    @Nested
    class delete {

        @Test
        void test001() throws IOException {
            var expected = target.store(createMultipartFile(), null);
            target.delete(expected.getUuid());
        }

    }

    @Nested
    class cleanup {

        @Test
        void test001() {

            // 準備


            // 実行
            target.cleanup(LocalDateTime.now());
        }


    }

    @Nested
    class getFileStoreBaseDir {
    }

    @Nested
    class getContent {
    }

    @Nested
    class copyFile {
    }

    @Nested
    class deleteFile {

        @Test
        void test001() {
            target.delete("02ea9d01-a310-48da-b7ba-4c01740cdfaf");
        }

    }

    @Nested
    class escapeContent {
    }

    @Nested
    class getAttachmentContentDisposition {
    }
}