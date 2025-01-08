package net.mahtabalam.app;

import net.mahtabalam.protos.RawDataResultProto.AlleleType;
import net.mahtabalam.protos.RawDataResultProto.RawDataResult;
import net.mahtabalam.protos.RawDataResultProto.Well;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer {

    public static void main(String[] args) {
        RawDataResult.Builder rawDataResultBuilder = RawDataResult.newBuilder();

        // Create 96 wells
        for (int i = 1; i <= 96; i++) {
            AlleleType alleleType = (i % 2 == 0) ? AlleleType.HOMOZYGOUS : AlleleType.HETEROZYGOUS;
            Well well = createWell(String.valueOf(i), alleleType);
            rawDataResultBuilder.addWellResults(well);
        }
        RawDataResult rawDataResult = rawDataResultBuilder.build();

        System.out.println("RawDataResult :" + rawDataResult);

        String userHome = System.getProperty("user.home");

        // Define the file path in the ~/protobuf directory
        String filePath = userHome + File.separator + "protobuf" + File.separator + "raw_data_result.bin";

        // Create the protobuf directory under home if it doesn't exist
        File dir = new File(userHome + File.separator + "protobuf");
        if (!dir.exists()) {
            dir.mkdirs();  // Create the directory (and any necessary parent directories)
        }

        // Serialize the protobuf object to the file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            rawDataResult.writeTo(fos);
            System.out.println("Serialized RawDataResult to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to serialize RawDataResult: " + e.getMessage());
        }
    }

    private static Well createWell(String id, AlleleType alleleType) {
        Well.Builder wellBuilder = Well.newBuilder();
        wellBuilder.setId(id);
        wellBuilder.setAlleleType(alleleType);
        // Add 10 values to allele1 and allele2
        for (int i = 1; i <= 10; i++) {
            wellBuilder.addAllele1(i * 0.9);
            wellBuilder.addAllele2(i * 0.8);
        }
        return wellBuilder.build();
    }
}
