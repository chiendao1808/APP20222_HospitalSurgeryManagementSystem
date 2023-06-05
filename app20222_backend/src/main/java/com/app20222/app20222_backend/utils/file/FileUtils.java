package com.app20222.app20222_backend.utils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static File covertMultipartToFile(MultipartFile multipartFile){
       try{
           if(Objects.isNull(multipartFile) || Objects.isNull(multipartFile.getOriginalFilename())) return null;
           File covertedFile = new File(multipartFile.getOriginalFilename());
           FileOutputStream fos = new FileOutputStream(covertedFile);
           fos.write(multipartFile.getBytes());
           fos.close();
           return covertedFile;
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }

}
