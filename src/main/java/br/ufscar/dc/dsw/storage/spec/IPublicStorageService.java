package br.ufscar.dc.dsw.storage.spec;

import org.springframework.web.multipart.MultipartFile;

/**
 * Esse serviço visa armazenar imagens para acesso público. Todas as imagens devem ser armazenadas em
 * alguma pasta acessível, por exemplo, "src/main/resources/static/uploads". Assim o acesso é possível no navegador,
 * por uma URL base. Ex: "http://localhost:8080/uploads/**".
 * <p>
 * Caso de uso: Armazenamento de foto de perfil, fotos de veículos, gifs ou videos.
 */
public interface IPublicStorageService {

    void init();

    Boolean exists(String path);

    String store(MultipartFile file);

    void delete(String path);

    void deleteAll();
}
