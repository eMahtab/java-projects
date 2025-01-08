# Serializing & Deserializing objects using Protobuf



## message
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



