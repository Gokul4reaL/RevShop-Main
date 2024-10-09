package com.revshop.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import java.io.IOException;
import java.util.Collections;

public class GoogleDriveService {

    private static Drive driveService;

    // Method to initialize the Drive service
    public static void initialize() throws IOException {
        if (driveService == null) {
            // Load the credentials.json file
        	GoogleCredentials credentials = GoogleCredentials.fromStream(
        	        GoogleDriveService.class.getClassLoader().getResourceAsStream("revshop.json"))
        	        .createScoped(Collections.singleton(DriveScopes.DRIVE)); // Update to DRIVE if needed
        	
            System.out.println("Credentials: " + credentials);
            
            // Build the Drive service using GsonFactory
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            driveService = new Drive.Builder(new NetHttpTransport(), jsonFactory, new HttpCredentialsAdapter(credentials))
                    .setApplicationName("RevShop")
                    .build();
        }
    }

    // Method to get the Drive service
    public static Drive getDriveService() throws IOException {
        if (driveService == null) {
            initialize(); // Ensure the Drive service is initialized
        }
        return driveService;
    }

    // Method to upload a file to Google Drive
    public static String uploadFile(java.io.File filePath, String mimeType, String folderId) throws IOException {
        // Ensure the Drive service is available
        if (driveService == null) {
            throw new IllegalStateException("Drive service is not initialized. Call initialize() first.");
        }

        // Create a file metadata instance
        File fileMetadata = new File();
        fileMetadata.setName(filePath.getName());

        // Set the parent folder ID
        if (folderId != null && !folderId.isEmpty()) {
            fileMetadata.setParents(Collections.singletonList(folderId)); // Set the parent folder
        }

        // Create the media content
        FileContent mediaContent = new FileContent(mimeType, filePath);
        
        // Upload the file to the specified folder
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        
        return "https://drive.google.com/uc?id=" + uploadedFile.getId(); // Return the URL of the uploaded file
    }
}
