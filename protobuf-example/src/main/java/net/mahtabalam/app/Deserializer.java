package net.mahtabalam.app;

import net.mahtabalam.protos.RawDataResultProto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Deserializer {

    public static void main(String[] args) {
        // Get the user's home directory and construct the file path
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "protobuf" + File.separator + "raw_data_result.bin";

        RawDataResultProto.RawDataResult rawDataResult = deserializeRawDataResult(filePath);
        if (rawDataResult != null) {
            System.out.println("Deserialized RawDataResult: " + rawDataResult);
        }
    }

    // Method to deserialize RawDataResult from a file
    private static RawDataResultProto.RawDataResult deserializeRawDataResult(String filePath) {
        RawDataResultProto.RawDataResult rawDataResult = null;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            rawDataResult = RawDataResultProto.RawDataResult.parseFrom(fis);
            System.out.println("Successfully deserialized RawDataResult from " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to deserialize RawDataResult: " + e.getMessage());
        }

        return rawDataResult;
    }
}
