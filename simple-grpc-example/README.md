# Simple gRPC server and client

## Prerequisite : Download protoc to compile .proto files and generate code
Download the **_compatible_** protoc from the https://github.com/protocolbuffers/protobuf/releases, _depending on your machine download linux-x86_64.zip or osx-x86_64.zip or win64.zip bundle of protoc._ 

## Version and Compatibility
**In this example I used protoc-25.0**, since [protobuf java](https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java) version and protoc version should match, make sure you are using the compatible versions of both. For example, Protobuf Java version 3.25.x uses protoc version 25.x

You can check more details about version compatibility [here](https://protobuf.dev/support/version-support/#java) 

!["Protoc releases at Github"](images/github-protoc-releases.png?raw=true)

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

## Step 2 : Compile and generate the Java classes using protoc

Note that we are using **`protoc-gen-grpc-java`** plugin with version 1.59.0, this grpc java plugin generates the Stub classes, making it easy to write the gRPC client and server.


# Use the compatible version of protoc-gen-grpc-java plugin
Make sure you are using the right version of protoc-gen-grpc-java plugin when compiling the .proto file with protoc. **In this example I use protoc-gen-grpc-java-1.59.0, and specify the path to the plugin .exe file when generating classes with protoc.**

[Check the version details of protoc-gen-grpc-java](https://mvnrepository.com/artifact/io.grpc/protoc-gen-grpc-java)

```
~/Downloads/protoc-25.0-win64/bin/protoc.exe --proto_path=src/main/java/proto/ --java_out=src/main/java/ --grpc-java_out=src/main/java/ --plugin=protoc-gen-grpc-java=C:\\Users\\alamm\\Downloads\\protoc-gen-grpc-java-1.59.0-windows-x86_64.exe src/main/java/proto/ProductService.proto
```

!["simple-grpc-example Project"](images/project.png?raw=true)

## Step 3 : Include dependencies in pom.xml

**Make sure you are using the compatible version of dependencies, depending on the protoc compiler version and protoc-gen-grpc-java plugin.**

```xml
<dependencies>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-netty</artifactId>
            <version>1.61.0</version>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-protobuf</artifactId>
            <version>1.61.0</version>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-stub</artifactId>
            <version>1.61.0</version>
        </dependency>
        <dependency>
            <groupId>javax.annotation</groupId>
            <artifactId>javax.annotation-api</artifactId>
            <version>1.2</version>
        </dependency>
</dependencies>
```

## Step 4 : Write the gRPC server code

```java
package net.mahtabalam.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import net.mahtabalam.protos.Product;
import net.mahtabalam.protos.ProductServiceGrpc;
import net.mahtabalam.protos.AddProductRequest;
import net.mahtabalam.protos.AddProductResponse;
import net.mahtabalam.protos.GetAllProductsRequest;
import net.mahtabalam.protos.GetAllProductsResponse;
import net.mahtabalam.protos.GetProductByIdRequest;
import net.mahtabalam.protos.GetProductByIdResponse;
import net.mahtabalam.protos.UpdateProductRequest;
import net.mahtabalam.protos.UpdateProductResponse;
import net.mahtabalam.protos.DeleteProductRequest;
import net.mahtabalam.protos.DeleteProductResponse;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProductServiceServer {

    private static final Map<String, Product> productStorage = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new ProductServiceImpl())
                .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started on port 50051");
        server.awaitTermination();
    }

    static class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

        public void addProduct(AddProductRequest request,
                               StreamObserver<AddProductResponse> responseObserver) {
            Product product = request.getProduct();
            productStorage.put(product.getProductId(), product);

            AddProductResponse response = AddProductResponse.newBuilder()
                    .setMessage("Product added successfully")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        public void getAllProducts(GetAllProductsRequest request,
                                   StreamObserver<GetAllProductsResponse> responseObserver) {
            GetAllProductsResponse.Builder responseBuilder = GetAllProductsResponse.newBuilder();
            productStorage.values().forEach(responseBuilder::addProducts);

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }

        public void getProductById(GetProductByIdRequest request,
                                   StreamObserver<GetProductByIdResponse> responseObserver) {
            String productId = request.getProductId();
            Product product = productStorage.getOrDefault(productId, null);

            if (product != null) {
                GetProductByIdResponse response = GetProductByIdResponse.newBuilder()
                        .setProduct(product)
                        .build();
                responseObserver.onNext(response);
            } else {
                responseObserver.onError(new RuntimeException("Product not found"));
            }
            responseObserver.onCompleted();
        }

        public void updateProduct(UpdateProductRequest request,
                                  StreamObserver<UpdateProductResponse> responseObserver) {
            Product product = request.getProduct();
            if (productStorage.containsKey(product.getProductId())) {
                productStorage.put(product.getProductId(), product);
                UpdateProductResponse response = UpdateProductResponse.newBuilder()
                        .setMessage("Product updated successfully")
                        .build();
                responseObserver.onNext(response);
            } else {
                responseObserver.onError(new RuntimeException("Product not found"));
            }
            responseObserver.onCompleted();
        }

        public void deleteProduct(DeleteProductRequest request,
                                  StreamObserver<DeleteProductResponse> responseObserver) {
            String productId = request.getProductId();
            if (productStorage.remove(productId) != null) {
                DeleteProductResponse response = DeleteProductResponse.newBuilder()
                        .setMessage("Product deleted successfully")
                        .build();
                responseObserver.onNext(response);
            } else {
                responseObserver.onError(new RuntimeException("Product not found"));
            }
            responseObserver.onCompleted();
        }
    }
}
```
