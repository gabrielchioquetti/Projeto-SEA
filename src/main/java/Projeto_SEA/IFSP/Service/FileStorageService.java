package Projeto_SEA.IFSP.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//Necessário para o próximo passo de "Alterando o nome do arquivo")
import org.springframework.util.StringUtils;
import java.util.UUID;


@Service
public class FileStorageService {

    private final Path path;

    public FileStorageService(Environment env) {
        String uploadDir = env.getProperty("file.upload-dir");
        if (uploadDir == null) {
            // Lança uma exceção se a propriedade não estiver definida
            throw new IllegalArgumentException("A propriedade 'file.upload-dir' não está definida no application.properties!");
        }

        this.path = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.path);
        } catch(Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório de uploads.", ex);
        }
    }

    public String store(MultipartFile file) {
        // Pega a extensão do arquivo original (ex: .jpg, .png)
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        // Gera um nome de arquivo único
        String fileName = UUID.randomUUID().toString() + "." + extension;
        try {
            Path targetLocation = this.path.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch(Exception ex) {
            throw new RuntimeException("Não foi possível salvar o arquivo " + fileName + ". Por favor, tente novamente!", ex);
        }
        return fileName;
    }

}
