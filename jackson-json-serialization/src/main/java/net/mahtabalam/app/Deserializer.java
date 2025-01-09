package net.mahtabalam.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mahtabalam.model.RawDataResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Deserializer {

    public static void main(String[] args) {
        // Get the user's home directory and construct the file path
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "jackson" + File.separator + "raw_data_result.json";

        RawDataResult rawDataResult = deserializeRawDataResult(filePath);
        if (rawDataResult != null) {
            System.out.println("Deserialized RawDataResult: " + rawDataResult);
        }
    }

    // Method to deserialize RawDataResult from a file
    private static RawDataResult deserializeRawDataResult(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        RawDataResult rawDataResult = null;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            rawDataResult = objectMapper.readValue(fis, RawDataResult.class);
            System.out.println("Successfully deserialized RawDataResult from " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to deserialize RawDataResult: " + e.getMessage());
        }
        return rawDataResult;
    }
}