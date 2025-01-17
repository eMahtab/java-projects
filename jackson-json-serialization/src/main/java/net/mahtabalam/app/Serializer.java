package net.mahtabalam.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mahtabalam.model.AlleleType;
import net.mahtabalam.model.RawDataResult;
import net.mahtabalam.model.Well;

import java.io.File;
import java.io.IOException;

public class Serializer {

    public static void main(String[] args) {
        RawDataResult rawDataResult = new RawDataResult();
        Well[] wells = new Well[96];
        for (int i = 0; i < 96; i++) {
            AlleleType alleleType = (i % 2 == 0) ? AlleleType.HOMOZYGOUS : AlleleType.HETEROZYGOUS;
            Well well = createWell(String.valueOf(i), alleleType);
            wells[i] = well;
        }
        rawDataResult.setWellResults(wells);

        System.out.println("RawDataResult :" + rawDataResult);
        String userHome = System.getProperty("user.home");

        // Define the file path in the ~/jackson directory
        String filePath = userHome + File.separator + "jackson" + File.separator + "raw_data_result.json";

        File dir = new File(userHome + File.separator + "jackson");
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory (and any necessary parent directories)
        }

        ObjectMapper objectMapper = new ObjectMapper();
        // Serialize the object to JSON
        try {
            objectMapper.writeValue(new File(filePath), rawDataResult);
            System.out.println("Serialized RawDataResult to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to serialize RawDataResult: " + e.getMessage());
        }
    }

    private static Well createWell(String id, AlleleType alleleType) {
        Well well = new Well();
        well.setId(id);
        well.setAlleleType(alleleType);
        // Add 10 values to allele1 and allele2
        double[] allele1 = new double[10];
        double[] allele2 = new double[10];
        for (int i = 0; i < 10; i++) {
            allele1[i] = i * 0.9;
            allele2[i] = i * 0.8;
        }
        well.setAllele1(allele1);
        well.setAllele2(allele2);
        return well;
    }
}