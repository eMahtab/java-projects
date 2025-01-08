# Serializing & Deserializing objects using Protobuf

## Prerequisite : Download protoc to compile .proto files and generate code
Download the latest protoc from the https://github.com/protocolbuffers/protobuf/releases, _depending on your machine download linux-x86_64.zip or osx-x86_64.zip
 or win64.zip bundle of protoc._

!["Protobuf releases at Github"](images/github-protoc-releases.png?raw=true)

## Step 1 : Create .proto file and define messages
```proto
syntax = "proto2";

option java_package = "net.mahtabalam.protos";
option java_outer_classname = "RawDataResultProto";

message RawDataResult {
    repeated Well wellResults = 1;
}

message Well {
   optional string id = 1;
   optional AlleleType alleleType = 2;
   repeated double allele1 = 3;
   repeated double allele2 = 4;
}

enum AlleleType {
    HOMOZYGOUS = 0;
    HETEROZYGOUS = 1;
}
```

## Step 2 : Compile and generate the Java classes
```
~/Downloads/protoc-29.2-win64/bin/protoc.exe --proto_path=src/main/java/proto/ --java_out=src/main/java/ src/main/java/proto/RawDataResult.proto
```

!["protobuf-example Project"](images/project.png?raw=true)

## Step 3 : Include protobuf-java dependency in pom.xml

```xml
<dependencies>
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>4.29.2</version>
        </dependency>
</dependencies>
```
## Step 4 : Write code to serialize Proto Object to file

First we create the RawDataResult instance and then serialize it and save it to file named _**raw_data_result.bin**_ inside user's home directory under protobuf directory.

```java
public class Serializer {

    public static void main(String[] args) {
        RawDataResult.Builder rawDataResultBuilder = RawDataResult.newBuilder();
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
```

```
 cd ~/protobuf
 ls -alh raw_data_result.bin
 -rw-r--r-- 1 alamm 197121 18K Jan  8 12:06 raw_data_result.bin
```

## Step 5 : Deserialize object from the protobuf binary file

```java
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
```
