package br.ufscar.dc.dsw.storage;

import br.ufscar.dc.dsw.exceptions.BadRequestException;
import br.ufscar.dc.dsw.exceptions.StorageException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Set;

@Component
public class StorageValidator {

    public StorageValidator() {}

    private void baseValidate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        String originalFilename = StringUtils.cleanPath(
                Objects.requireNonNull(file.getOriginalFilename(), "Filename cannot be null.")
        );

        boolean isValidFileName =
                !originalFilename.contains("..") &&
                        !originalFilename.contains("/") &&
                        !originalFilename.contains("\\") &&
                        !originalFilename.contains(":");

        if (!isValidFileName) {
            throw new StorageException("Filename contains invalid path sequence: " + originalFilename);
        }
    }

    public void validateFile(MultipartFile file, Set<String> allowedContentTypes, String allowedTypesUserMessage) {
        baseValidate(file);

        if (allowedContentTypes != null && !allowedContentTypes.isEmpty()) {
            String actualContentType = file.getContentType();
            if (actualContentType == null || !allowedContentTypes.contains(actualContentType.toLowerCase())) {
                throw new BadRequestException("Invalid file type. Allowed types: " + allowedTypesUserMessage +
                        ". Received: " + (actualContentType != null ? actualContentType : "unknown"));
            }
        }
    }

    public void validateFileUpload(MultipartFile file) {
        Set<String> legacyAllowedTypes = Set.of(
                "image/png", "image/jpeg", "image/jpg",
                "application/pdf", "model/stl"
        );
        validateFile(file, legacyAllowedTypes, "PNG, JPEG, JPG, PDF, STL");
    }

    public void validateProfilePictureUpload(MultipartFile file) {
        Set<String> profilePictureAllowedTypes = Set.of(
                "image/png", "image/jpeg", "image/jpg"
        );
        validateFile(file, profilePictureAllowedTypes, "PNG, JPEG, JPG");
    }
}