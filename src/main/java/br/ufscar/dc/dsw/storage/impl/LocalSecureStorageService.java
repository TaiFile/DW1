package br.ufscar.dc.dsw.storage.impl;

import br.ufscar.dc.dsw.Utils;
import br.ufscar.dc.dsw.exceptions.StorageException;
import br.ufscar.dc.dsw.storage.spec.ISecuredStorageService;
import br.ufscar.dc.dsw.validation.validator.FileValidator;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalSecureStorageService implements ISecuredStorageService {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private FileValidator fileValidator;

    private static final String FILE_UPLOAD_ACCESS_PREFIX = "uploads";

    private Path physicalUploadBaseDir;

    private final Logger logger = LoggerFactory.getLogger(LocalSecureStorageService.class.getName());

    private Path getUploadBaseDir() {
        if (physicalUploadBaseDir == null) {
            String realPath = servletContext.getRealPath("");
            if (realPath == null) {
                throw new StorageException("ServletContext.getRealPath(\"\") returned null. Cannot determine upload directory.");
            }
            physicalUploadBaseDir = Paths.get(realPath, FILE_UPLOAD_ACCESS_PREFIX).normalize();
        }
        return physicalUploadBaseDir;
    }

    @Override
    @PostConstruct
    public void init() {
        Path baseDir = getUploadBaseDir();

        if (!Files.exists(baseDir)) {
            try {
                Files.createDirectories(baseDir);
                logger.info("Created directory: {}", baseDir);
            } catch (IOException e) {
                throw new StorageException("Could not initialize upload directory: " + e.getMessage());
            }
        }
    }

    @Override
    public Boolean exists(String relativePath) {
        Path physicalFilePath = getUploadBaseDir().resolve(relativePath).normalize();
        return Files.exists(physicalFilePath);
    }

    @Override
    public String store(MultipartFile file) {
        fileValidator.validate(file);

        String originalFilename = Utils.getOriginalFilename(file);

        String sanitizedOriginalFilename = originalFilename
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-zA-Z0-9.\\-_]", "");

        if (sanitizedOriginalFilename.isEmpty()) {
            sanitizedOriginalFilename = "file";
        }

        Path storageDir = getUploadBaseDir();

        String filename = UUID.randomUUID() + "-" + sanitizedOriginalFilename;
        Path filePath = storageDir.resolve(filename);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + e.getMessage());
        }

        logger.info("Stored new file at: {}", filePath);

        return filename;
    }

    @Override
    public Resource load(String relativePath) {
        try {
            Path physicalFilePath = getUploadBaseDir().resolve(relativePath).normalize();

            Resource resource = new UrlResource(physicalFilePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + relativePath);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Failed to read file (Malformed URL from relative path): " + relativePath);
        }
    }

    @Override
    public void delete(String relativePath) {
        try {
            Path physicalFilePath = getUploadBaseDir().resolve(relativePath).normalize();

            if (Files.exists(physicalFilePath)) {
                Files.delete(physicalFilePath);
            } else {
                throw new StorageException("File not found to delete: " + relativePath);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to delete file: " + relativePath);
        }
        logger.info("Deleted file: {}", relativePath);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(getUploadBaseDir().toFile());
        logger.info("Deleted all files in directory: {}", getUploadBaseDir());
    }
}