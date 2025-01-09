# Serializing & Deserializing objects using Jackson Json

## Step 1 : Add the required Jackson databind dependency in pom.xml

!["IntelliJ project"](images/project.png?raw=true)

```xml
<dependencies>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.18.2</version>
        </dependency>
</dependencies>
```

## Step 2 : Serialize the Java object to Json using Jackson

Serializer will serialize the RawDataResult instance to a file named **_raw_data_result.json_** inside users' home directory under jackson directory.


```java
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
```

!["Jackson Json Serialization"](images/json-serialization.png?raw=true)

# Step 3 : Deserialize the serialized object

```java
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
```


## Compairing Protobuf binary serialized and Jackson json serialized file size

!["Json and Binary Serialiazed file size comparison"](images/json-and-protobuf.png?raw=true)

