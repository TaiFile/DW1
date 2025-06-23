package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.exceptions.BadRequestException;
import br.ufscar.dc.dsw.exceptions.StorageException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Set;

@Component
public class FileValidator {

    public void validateFileName(MultipartFile file) {
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

    public void validateContentType(MultipartFile file) {
        Set<String> allowedContentTypes = Set.of(
                "image/png", "image/jpeg", "image/jpg"
        );

        String allowedTypesUserMessage = "PNG, JPEG, JPG";

        String actualContentType = file.getContentType();
        if (actualContentType == null || !allowedContentTypes.contains(actualContentType.toLowerCase())) {
            throw new BadRequestException("Invalid file type. Allowed types: " + allowedTypesUserMessage +
                    ". Received: " + (actualContentType != null ? actualContentType : "unknown"));
        }
    }

    public void validate(MultipartFile file) {
        validateFileName(file);
        validateContentType(file);
    }
}