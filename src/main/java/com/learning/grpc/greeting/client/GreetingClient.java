package com.learning.grpc.greeting.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.printf("gRPC Client");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",50051)
                .usePlaintext() //Force SSL to be de-activated during development. Not to be done in prod.
                .build();
        //DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);
        //DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel);
        //Call APIs if defined. (this is dummy only)

        // created a greet service client (blocking - synchronous)
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Unary
        // created a protocol buffer greeting message
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Ribhu")      //set the fields defined in message Greeting in .proto
                .setLastName("Kashyap")
                .build();

        // created a protocol buffer for a GreetRequest
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)      //set the message GreetRequest in .proto
                .build();

        // call the RPC and get back a GreetResponse (protocol buffers)
        GreetResponse greetResponse = greetClient.greet(greetRequest);
        /* Note : the above call to greet is an RPC call. The method is
           defined as a service in the greet.proto file. The greet function
           is implemented in the autogenerated code and override by us on
           the GreetServiceImpl (server) and it looks like a plain method call
           but it is actually going over the network. That is the RPC call.
         */
        System.out.println(greetResponse.getResult());

        System.out.println("\nShutting down channel...");
        channel.shutdown();
    }
}