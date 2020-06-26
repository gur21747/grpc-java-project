package com.learning.grpc.greeting.server;
/*
  Created by  : Ribhu Kashyap
  Created on  : 25/06/2020
  Purpose     : Demo of gRPC Unary call
 */
import com.proto.greet.*;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        // extract the fields we need
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();
        String lastName = greeting.getLastName();

        // create the response
        String result = "Hello " + firstName + " " + lastName ;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        // send the response
        responseObserver.onNext(response);

        // complete the RPC call
        responseObserver.onCompleted();

        //super.greet(request, responseObserver);
    }

    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        //super.greetManyTimes(request, responseObserver);

        String firstName = request.getGreeting().getFirstName();
        String lastName = request.getGreeting().getLastName();

        try{
            for(int i = 0; i < 10; i++){
                String result = "\nHello " + firstName + " " + lastName + " - response num = "+ i;
                GreetManyTimesResponse response = GreetManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();
                responseObserver.onNext(response);
                Thread.sleep(1000L);
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally {
            responseObserver.onCompleted();
        }
    }
}
