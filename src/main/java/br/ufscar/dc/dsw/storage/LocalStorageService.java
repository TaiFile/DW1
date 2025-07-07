package br.ufscar.dc.dsw.storage;

import br.ufscar.dc.dsw.Utils;
import br.ufscar.dc.dsw.exceptions.StorageException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalStorageService implements IStorageService {

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    @Value("${app.url}")
    private String applicationBaseUrl;

    @PostConstruct
    @Override
    public void init() {
        Path baseDir = Paths.get(fileUploadDir);

        if (!Files.exists(baseDir)) {
            try {
                Files.createDirectories(baseDir);
            } catch (IOException e) {
                throw new StorageException("Could not initialize tmp directory, " + e.getMessage());
            }
        }
    }

    @Override
    public Boolean exists(String httpUrl) {
        try {
            String filename = Paths.get(new URI(httpUrl).getPath()).getFileName().toString();
            Path physicalFilePath = Paths.get(fileUploadDir).resolve(filename).normalize();
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

        Path storageDir = Path.of(fileUploadDir);
        Path filePath = storageDir.resolve(UUID.randomUUID() + "-" + sanitizedOriginalFilename);

        try {
            System.out.println("Salvando: " + filePath);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + e.getMessage());
        }

        // Ex: http://localhost:8080/tmp/uuid-filename.jpg
        return applicationBaseUrl + "/" + fileUploadDir + "/" + filePath.getFileName().toString();
    }

    @Override
    public Resource load(String httpUrl) {
        try {
            String filename = Paths.get(new URI(httpUrl).getPath()).getFileName().toString();

            Path physicalFilePath = Paths.get(fileUploadDir).resolve(filename).normalize();

            Resource resource = new UrlResource(physicalFilePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Failed to read file (Malformed URL): " + httpUrl);
        } catch (URISyntaxException e) {
            throw new StorageException("Failed to read file (Invalid URI syntax): " + httpUrl);
        }
    }

    @Override
    public void delete(String httpUrl) {
        try {
            String filename = Paths.get(new URI(httpUrl).getPath()).getFileName().toString();
            Path physicalFilePath = Paths.get(fileUploadDir).resolve(filename).normalize();

            if (!Files.exists(physicalFilePath)) {
                return;
            }

            System.out.println("Excluindo: " + httpUrl);
            Files.delete(physicalFilePath);

        } catch (IOException e) {
            throw new StorageException("Failed to delete file: " + httpUrl);
        } catch (URISyntaxException e) {
            throw new StorageException("Failed to delete file (Invalid URI syntax): " + httpUrl);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(new File(fileUploadDir));
    }
}
