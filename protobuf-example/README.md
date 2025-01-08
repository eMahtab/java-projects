# Serializing & Deserializing objects using Protobuf

## Prerequisite : Download protoc to compile .proto files and generate code
Download the latest protoc from the https://github.com/protocolbuffers/protobuf/releases, depending on your machine download linux-x86_64.zip or osx-x86_64.zip
 or win64.zip bundle of protoc.

!["Protobuf releases at Github"](images/github-protoc-releases.png?raw=true)

## Step 1 : Creating .proto file and defining messages
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

## Step 2 : Compiling and generating the Java classes
```
~/Downloads/protoc-29.2-win64/bin/protoc.exe --proto_path=src/main/java/proto/ --java_out=src/main/java/ src/main/java/proto/RawDataResult.proto
```

