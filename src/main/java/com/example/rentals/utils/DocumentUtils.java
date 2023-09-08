package com.example.rentals.utils;

import com.example.rentals.entity.Users;
import com.example.rentals.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
@Component
public class DocumentUtils {

    private final String DIR_PICTURE_PATH;

    @Autowired
    private UserService userService;

    public DocumentUtils(@Value("${chatop.app.DirPircturePath}") String fileName){
        this.DIR_PICTURE_PATH = fileName;
    }

    public void createDirIfNotExist() throws Exception {
        File directory = new File(DIR_PICTURE_PATH);
        if (!directory.exists()) {
            try {
                directory.mkdir();
            } catch (SecurityException ex) {
                log.error("[DocumentUtils] isDirectoryExistOrCreate " + ex);
                throw new Exception("an error has occured");
            }
        }
    }

    public String getFileName(String pictureName){
        Users user = userService.getCurrentUser();
        String fileExt = pictureName.substring(pictureName.lastIndexOf("."));
        return user.getId() + "_" + user.getName()+"_"+System.currentTimeMillis()+ fileExt;
    }

    public String getPathPicture(MultipartFile picture) {
        return this.DIR_PICTURE_PATH+"/"+this.getFileName(picture.getOriginalFilename());
    }

    public String uploadUserRentalPicture(MultipartFile picture) throws Exception {
        this.createDirIfNotExist();
        String pathPicture = getPathPicture(picture);
        try {
            InputStream inputStream = picture.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(pathPicture);
            byte[] buf = new byte[1024];
            int numRead = 0;
            while((numRead = inputStream.read(buf)) >= 0){
                fileOutputStream.write(buf, 0, numRead);
            }
        }catch (Exception ex){
            log.error("[DocumentUtils] uploadUserRentalPicture "+ ex);
        }
        return pathPicture;
    }
}
