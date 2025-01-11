package net.mahtabalam.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.mahtabalam.protos.ProductServiceGrpc;
import net.mahtabalam.protos.Product;
import net.mahtabalam.protos.AddProductResponse;
import net.mahtabalam.protos.AddProductRequest;
import net.mahtabalam.protos.UpdateProductResponse;
import net.mahtabalam.protos.UpdateProductRequest;
import net.mahtabalam.protos.GetAllProductsResponse;
import net.mahtabalam.protos.GetAllProductsRequest;
import net.mahtabalam.protos.DeleteProductResponse;
import net.mahtabalam.protos.DeleteProductRequest;

public class ProductServiceClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ProductServiceGrpc.ProductServiceBlockingStub stub = ProductServiceGrpc.newBlockingStub(channel);

        Product product = Product.newBuilder()
                .setProductId("p1")
                .setProductName("Garmin Forerunner 165, Running Smartwatch")
                .setDescription("Colorful AMOLED Display, Training Metrics and Recovery Insights")
                .putAttributes("Operating System", "Android")
                .putAttributes("Memory", "4GB")
                .putAttributes("Connectivity Technology", "Bluetooth, Wi-Fi, USB")
                .build();

        // Add a new product
        AddProductResponse addResponse = stub.addProduct(
                AddProductRequest.newBuilder().setProduct(product).build());
        System.out.println("Add Product : Server Response: " + addResponse.getMessage());

        // Update the product
        Product updatedProduct = product.toBuilder()
                .setProductName("Updated Garmin Forerunner 165, Running Smartwatch")
                .putAttributes("Display Size", "1.3 Inches")
                .build();

        UpdateProductResponse updateResponse = stub.updateProduct(
                UpdateProductRequest.newBuilder().setProduct(updatedProduct).build());
        System.out.println("Update Product : Server Response: " + updateResponse.getMessage());

        // Get all products
        GetAllProductsResponse allProductsResponse = stub.getAllProducts(
                GetAllProductsRequest.newBuilder().build());
        System.out.println("All Products : Server Response: ");
        for (Product p : allProductsResponse.getProductsList()) {
            System.out.println(p);
        }

        // Delete the product
        DeleteProductResponse deleteResponse = stub.deleteProduct(
                DeleteProductRequest.newBuilder().setProductId("p1").build());
        System.out.println("Delete Product : Server Response: " + deleteResponse.getMessage());

        channel.shutdown();
    }
}
