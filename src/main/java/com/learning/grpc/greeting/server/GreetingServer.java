package com.learning.grpc.greeting.server;
/*
  Created by  : Ribhu Kashyap
  Created on  : 25/06/2020
  Purpose     : Demo of gRPC Unary call
 */
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("gRPC Server running...");

        Server server = ServerBuilder.forPort(50051)
                .addService(new GreetServiceImpl())
                .build();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received shut down request...");
            server.shutdown();
            System.out.println("Server is shut down");
        }));    //Whenever the application receive a shut request then we shut down the service.

        server.awaitTermination();  //Service is awaiting termination. Needs to be blocking for main thread

    }
}
