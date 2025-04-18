package com.shadyplace.springweb.services.articleBlog;

import com.shadyplace.springweb.config.UploadImageConfig;
import com.shadyplace.springweb.exception.FileTypeException;
import com.shadyplace.springweb.models.articleBlog.Image;
import com.shadyplace.springweb.repository.articleBlog.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    private final Path rootLocation;
    private UploadImageConfig config;

    public ImageService(UploadImageConfig config){
        this.rootLocation = Paths.get(config.getLocation());
        this.config = config;
    }

    public Image findByLocation(String location){
        return this.imageRepository.findFirstByLocation(location);
    }

    public void save(Image image){
        imageRepository.save(image);
    }

    public String upload(MultipartFile file) throws FileTypeException, IOException {
        if (!config.getAllowedFormat().contains(file.getContentType())) {
            throw new FileTypeException();
        }

        String filePath = UUID.randomUUID() + "-" + file.getOriginalFilename();

        Path destination = this.rootLocation.resolve(Paths.get(filePath)).normalize().toAbsolutePath();

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return config.getLocation() + "/" + filePath;
    }

    public void remove(Image image) {
        this.imageRepository.delete(image);
    }

    public void deleteImageFile(String location) throws IOException {

        Path locationPath = Paths.get(location);

        Files.delete(locationPath);
    }
}