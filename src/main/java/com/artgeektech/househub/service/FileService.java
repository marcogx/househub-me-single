package com.artgeektech.househub.service;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
	
	@Value("${file.path}")
	private String filePath;

	public List<String> getImgPaths(List<MultipartFile> files) {
	    if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
		List<String> paths = new ArrayList<>();
		files.forEach(file -> {
			if (!file.isEmpty()) {
				try {
					File localFile = saveToLocal(file);
					String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
					paths.add(path);
				} catch (IOException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		return paths;
	}

    public String getImgPath(MultipartFile file) {
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        String relativePath = "";
        if (!file.isEmpty()) {
            try {
                File localFile = saveToLocal(file);
                relativePath = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return relativePath;
    }
	
	public static String getResourcePath(){
	  File file = new File(".");
	  return file.getAbsolutePath();
	}

	private File saveToLocal(MultipartFile file) throws IOException {
	 File localFile = new File(filePath + "/" +
			 Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
	 if (!localFile.exists()) {
		 localFile.getParentFile().mkdirs();
		 localFile.createNewFile();
	 }
	 Files.write(file.getBytes(), localFile);
     return localFile;
	}

}
