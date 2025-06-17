package br.ufscar.dc.dsw.storage.spec;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Esse serviço visa armazenar imagens para acesso privado. As imagens devem ser armazenadas
 * em um local seguro, não sendo acessadas diretamente pelo navegador. Para isso, o serviço
 * deve implementar {@link #store(MultipartFile file)} que servirá para fazer o download
 * dos arquivos.
 * <p>
 * Caso de uso: Armazenamento de documentos, exames ou imagens privadas.
 */
public interface ISecuredStorageService {
    void init();

    Boolean exists(String path);

    Resource load(String path);

    String store(MultipartFile file);

    void delete(String path);

    void deleteAll();
}
