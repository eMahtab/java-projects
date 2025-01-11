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
