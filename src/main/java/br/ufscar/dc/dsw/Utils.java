package br.ufscar.dc.dsw;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

public class Utils {
    private Utils() {
    }

    public static int generateRandomCode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }

    public static String getOriginalFilename(MultipartFile file) {
        return file.getOriginalFilename() == null ? "" : StringUtils.cleanPath(file.getOriginalFilename());
    }
}