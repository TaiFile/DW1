package br.ufscar.dc.dsw.storage.impl;

import br.ufscar.dc.dsw.Utils;
import br.ufscar.dc.dsw.exceptions.BadRequestException;
import br.ufscar.dc.dsw.exceptions.StorageException;
import br.ufscar.dc.dsw.storage.spec.IPublicStorageService;
import br.ufscar.dc.dsw.validation.validator.FileValidator;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalPublicStorageService implements IPublicStorageService {

    @Autowired
    private FileValidator fileValidator;

    private static final String APPLICATION_BASE_URL = "http://localhost:8080";
    private static final String FILE_UPLOAD_DIR = "src/main/resources/static";
    private static final String FILE_UPLOAD_SUB_DIR = "uploads";

    private final Logger logger = LoggerFactory.getLogger(LocalPublicStorageService.class.getName());

    @Override
    @PostConstruct
    public void init() {
        Path baseDir = Paths.get(FILE_UPLOAD_DIR).resolve(FILE_UPLOAD_SUB_DIR).normalize();

        if (!Files.exists(baseDir)) {
            try {
                Files.createDirectories(baseDir);
                logger.info("Created directory: {}", baseDir);
            } catch (IOException e) {
                throw new StorageException("Could not initialize tmp directory, " + e.getMessage());
            }
        }
    }

    @Override
    public Boolean exists(String httpUrl) {
        if (httpUrl == null || httpUrl.trim().isEmpty() || !httpUrl.startsWith(APPLICATION_BASE_URL)) {
            return false;
        }

        try {
            // de "http://localhost:8080/uploads/picture.jpg" para "/uploads/picture.jpg"
            String urlPath = new URI(httpUrl).getPath();

            // de "/uploads/picture.jpg" para "uploads/picture.jpg"
            String relativePath = urlPath.substring(1);

            // Ex: "src/main/resources/static/uploads/picture.jpg"
            Path physicalFilePath = Paths.get(FILE_UPLOAD_DIR).resolve(relativePath).normalize();

            return Files.exists(physicalFilePath);
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public String store(MultipartFile file) {

        String originalFilename = Utils.getOriginalFilename(file);

        String sanitizedOriginalFilename = originalFilename
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-zA-Z0-9.\\-_]", "");

        if (sanitizedOriginalFilename.isEmpty()) {
            sanitizedOriginalFilename = "file";
        }

        Path storageDir = Path.of(FILE_UPLOAD_DIR).resolve(FILE_UPLOAD_SUB_DIR).normalize();
        String fileName = UUID.randomUUID() + "-" + sanitizedOriginalFilename;
        Path filePath = storageDir.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + e.getMessage());
        }

        logger.info("Stored new file at : {}", filePath);

        // Ex: http://localhost:8080/uploads/picture.jpg
        return APPLICATION_BASE_URL + "/" + FILE_UPLOAD_SUB_DIR + "/" + fileName;
    }

    @Override
    public void delete(String httpUrl) {
        if (httpUrl == null || httpUrl.trim().isEmpty() || !httpUrl.startsWith(APPLICATION_BASE_URL)) {
            throw new BadRequestException("Invalid URL: " + httpUrl);
        }

        try {
            // De "http://localhost:8080/uploads/picture.jpg" para "/uploads/picture.jpg"
            String urlPath = new URI(httpUrl).getPath();

            // De "/uploads/picture.jpg" para "uploads/picture.jpg"
            String relativePath = urlPath.substring(1);

            // Ex: "src/main/resources/static/uploads/picture.jpg"
            Path physicalFilePath = Paths.get(FILE_UPLOAD_DIR).resolve(relativePath).normalize();

            if (Files.exists(physicalFilePath)) {
                Files.delete(physicalFilePath);
            } else {
                throw new StorageException("File not found to delete: " + physicalFilePath);
            }
        } catch (URISyntaxException e) {
            throw new StorageException("Failed to delete file (Invalid URI syntax): " + httpUrl);
        } catch (IOException e) {
            throw new StorageException("Failed to delete file: " + httpUrl);
        }

        logger.info("Deleted file: {}", httpUrl);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(new File(FILE_UPLOAD_DIR, FILE_UPLOAD_SUB_DIR));
        logger.info("Deleted all files in directory: {}", FILE_UPLOAD_DIR + "/" + FILE_UPLOAD_SUB_DIR);
    }
}
