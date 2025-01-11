# Simple gRPC server and client

## Prerequisite : Download protoc to compile .proto files and generate code
Download the **_compatible_** protoc from the https://github.com/protocolbuffers/protobuf/releases, _depending on your machine download linux-x86_64.zip or osx-x86_64.zip or win64.zip bundle of protoc._ 

## Version and Compatibility
**In this example I used protoc-25.0**, since [protobuf java](https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java) version and protoc version should match, make sure you are using the compatible versions of both. For example, Protobuf Java version 3.25.x uses protoc version 25.x

You can check more details about version compatibility [here](https://protobuf.dev/support/version-support/#java) 

!["Protobuf releases at Github"](images/github-protoc-releases.png?raw=true)

## Step 1 : Create .proto file and define messages and service

We define `ProductService`  with five `rpc` calls allowing us to perform CRUD operations on `Product` 

```proto
syntax = "proto3";

option java_package = "net.mahtabalam.protos";
option java_multiple_files = true;
option java_outer_classname = "ProductServiceProto";

message Product {
    string productId = 1;
    string productName = 2;
    string description = 3;
    map<string, string> attributes = 4;
}

message AddProductRequest {
    Product product = 1;
}

message AddProductResponse {
    string message = 1;
}

message GetAllProductsRequest {}

message GetAllProductsResponse {
    repeated Product products = 1;
}

message GetProductByIdRequest {
    string productId = 1;
}

message GetProductByIdResponse {
    Product product = 1;
}

message UpdateProductRequest {
    Product product = 1;
}

message UpdateProductResponse {
    string message = 1;
}

message DeleteProductRequest {
    string productId = 1;
}

message DeleteProductResponse {
    string message = 1;
}

service ProductService {
    rpc AddProduct(AddProductRequest) returns (AddProductResponse);
    rpc GetAllProducts(GetAllProductsRequest) returns (GetAllProductsResponse);
    rpc GetProductById(GetProductByIdRequest) returns (GetProductByIdResponse);
    rpc UpdateProduct(UpdateProductRequest) returns (UpdateProductResponse);
    rpc DeleteProduct(DeleteProductRequest) returns (DeleteProductResponse);
}
```
