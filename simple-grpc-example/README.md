# Simple gRPC server and client

## Prerequisite : Download protoc to compile .proto files and generate code
Download the **_compatible_** protoc from the https://github.com/protocolbuffers/protobuf/releases, _depending on your machine download linux-x86_64.zip or osx-x86_64.zip or win64.zip bundle of protoc._ 

## Version and Compatibility
In this example I used protoc-25.0, since [protobuf java](https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java) version and protoc version should match, make sure you are using the compatible versions of both. For example, Protobuf Java version 3.25.x uses protoc version 25.x

You can check more details about version compatibility [here](https://protobuf.dev/support/version-support/#java) 

!["Protobuf releases at Github"](images/github-protoc-releases.png?raw=true)

## Step 1 : Create .proto file and define messages
