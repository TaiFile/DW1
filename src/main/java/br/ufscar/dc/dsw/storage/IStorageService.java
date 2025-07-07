package br.ufscar.dc.dsw.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    void init();

    Boolean exists(String path);

    String store(MultipartFile file);

    Resource load(String path);

    void delete(String path);

    void deleteAll();
}
