package com.shadyplace.springweb.services;

import com.shadyplace.springweb.config.UploadImageConfig;
import com.shadyplace.springweb.exception.WrongFileTypeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class UploadImageService {

    private final Path rootLocation;
    private final String uploadFolder;
    private List<String> allowedImageExtension;

    public UploadImageService(UploadImageConfig properties) {
        this.rootLocation = Paths.get("src/main/resources/static/"+properties.getLocation());
        this.uploadFolder = properties.getLocation();
        this.allowedImageExtension = properties.getAllowedFormat();
    }

    public String upload(MultipartFile file) throws IOException, WrongFileTypeException {
        if(!this.allowedImageExtension.contains(file.getContentType())){
            throw new WrongFileTypeException();
        }
        String filePath = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(filePath))
                .normalize().toAbsolutePath();
        Files.copy(file.getInputStream(), destinationFile,
                StandardCopyOption.REPLACE_EXISTING);

        return  "/" + uploadFolder + "/" + filePath;
    }

    public void init() throws IOException {
        Files.createDirectories(rootLocation);
    }
}

