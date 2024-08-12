package com.example.ecommerce.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileUtil {
	
	private static final String UPLOAD_DIR = "uploads";
	
	public static void saveBase64ToFile(String base64Image, String fileName) throws IOException {
		File uploadDir = new File(UPLOAD_DIR);
		
	    if (!uploadDir.exists()) {
	        uploadDir.mkdir();
	    }
	    
		byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

        // Tentukan lokasi penyimpanan
        Path path = Paths.get(UPLOAD_DIR, fileName);

        // Membuat folder jika belum ada
        Files.createDirectories(path.getParent());

        // Simpan file
        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            fos.write(decodedBytes);
        }
    }
	
	public static String generateUniqueFileName(String extension, String title) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return title+"_" + timestamp + "." + extension;
    }
}
