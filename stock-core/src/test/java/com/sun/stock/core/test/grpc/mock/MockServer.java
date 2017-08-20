package com.sun.stock.core.test.grpc.mock;

import com.alibaba.geabase.proto.query.QueryProtocol;
import com.alibaba.geabase.proto.query.QueryProtocol.*;
import com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap.HostShard;
import com.alibaba.geabase.proto.query.QueryServiceInterfaceGrpc;
import com.google.protobuf.Empty;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Logger;

public class MockServer {

    private static final Logger logger =
        Logger.getLogger(MockServer.class.getName());
    private final int port;
    private Server server;
    private final boolean slowServer;
    private HashSet<String> qualifiedCallers = new HashSet<String>();
    private boolean qualificationOn = false;
    private volatile StatusResponse.Status serverStatus = StatusResponse.Status.READY;
    public MockServer() {
        this.port = PortNumberGenerator.getPort();
        this.slowServer = false;
    }
    
    public MockServer(boolean slow) {
        this.port = PortNumberGenerator.getPort();
        this.slowServer = slow;
    }
    
    public MockServer(boolean slow, boolean qualificationOn) {
        this.port = PortNumberGenerator.getPort();
        this.slowServer = slow;
        this.qualificationOn = qualificationOn;
    }
    
    public void setQualifiedCallers(Collection<String> qualifiedCallers) {
        this.qualifiedCallers.addAll(qualifiedCallers);
    }

    public void start() throws Exception {

        server = NettyServerBuilder.forPort(port)
                .addService(new ResultInJsonImpl())
                .build()
                .start();

        logger.info("Server started, listenning on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {

                System.err.println(
                    "shutting down the server since JVM is shutting down");
                MockServer.this.stop();
                System.err.println("server shut down");
            }
        });
    }

    void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public void setServerStatus(StatusResponse.Status serverStatus) {
        this.serverStatus = serverStatus;
    }

    public StatusResponse.Status getServerStatus() {
        return serverStatus;
    }

    public void stop() {
        if (server != null) {
            server.shutdownNow();
        }

    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) throws Exception {
        final MockServer server = new MockServer();
        server.start();
        server.blockUntilShutdown();
    }

    private class ResultInJsonImpl extends QueryServiceInterfaceGrpc.QueryServiceInterfaceImplBase {

        @Override
        public void queryToJson(QueryRequest request, 
            StreamObserver<ResultInJson> responseObserver) {
            ResultInJson reply = 
                ResultInJson.newBuilder()
                    .setResult(request.getQueryString() 
                        + " from server").build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void query(QueryRequest request, 
            StreamObserver<QueryResponse> responseObserver) {

            int value = -1;
            String requestString = request.getQueryString().trim();
            long delayMilli = 0L;
            if (qualificationOn && !qualifiedCallers.contains(request.getCaller())) {
                QueryResponse reply = 
                    QueryResponse.newBuilder()
                                 .setError(24)
                                 .setErrMsg("Access denied")
                                 .build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
                return;
            }
            if(requestString.startsWith("port")) {
                QueryResponse reply = 
                    QueryResponse.newBuilder()
                        .addResult(
                            QueryResponseOneRow.newBuilder()
                                .addReturnFields(
                                    Value.newBuilder()
                                         .setIntVal(port)
                                         .build()
                                         )
                                .build())
                        .build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
                return;
            }
            if (requestString.startsWith("delay")) {
                String[] delayInfo = requestString.split(":");
                try {
                    delayMilli = Long.parseLong(delayInfo[1]);
                    Thread.sleep(delayMilli);
                } catch (Exception e) {

                }
            } else {
                try {
                    value = Integer.parseInt(request.getQueryString());
                } catch (Exception e) {

                }
            }

            QueryResponse reply = QueryResponse.newBuilder()
                .addResult(QueryResponseOneRow.newBuilder()
                    .addReturnFields(Value.newBuilder()
                    .setIntVal(value)
                    .build())
                    .build())
                .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
        
        @Override
        public void getShardmap(
            ShardRequest request, 
            StreamObserver<HostShardMap> responseObserver) {
            HostShard hostShard = 
                HostShard.newBuilder().setHostport("127.0.0.1:60000").build();
            HostShardMap shardMap = 
                HostShardMap.newBuilder().addEntry(hostShard).build();
            responseObserver.onNext(shardMap);
            responseObserver.onCompleted();

        }

        /**
         * Client should never touch queryInternal
         */
        @Override
        public void queryInternal(
            QueryInternalRequest request,
            StreamObserver<QueryResponse> responseObserver) {
            QueryResponse reply = QueryResponse.getDefaultInstance();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();

        }

        @Override
        public void getStatus(
            Empty request,
            StreamObserver<StatusResponse> responseObserver) {
            if (slowServer) {
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                }
            }
            
            StatusResponse reply =
                StatusResponse.newBuilder()
                    .setStatus(serverStatus)
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();

        }

        public void getParsingTree(
            QueryProtocol.ParserRequest request,
            StreamObserver<QueryProtocol.ParserResponse> responseObserver) {
            QueryProtocol.ParserResponse reply = QueryProtocol.ParserResponse.getDefaultInstance();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

    }

}
