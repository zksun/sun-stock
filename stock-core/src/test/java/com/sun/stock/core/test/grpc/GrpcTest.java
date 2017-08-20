package com.sun.stock.core.test.grpc;

import com.alibaba.geabase.proto.query.QueryProtocol;
import com.alibaba.geabase.proto.query.QueryServiceInterfaceGrpc;
import com.google.protobuf.Empty;
import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.StreamObserver;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static com.alibaba.geabase.proto.query.QueryServiceInterfaceGrpc.METHOD_QUERY;

/**
 * Created by zksun on 2017/7/21.
 */
public class GrpcTest {

    private HashSet<String> qualifiedCallers = new HashSet<String>();
    private boolean qualificationOn = false;

    private volatile ManagedChannel wrapped;

    private volatile Server server;

    @Before
    public void setUp() throws Exception {

        try {

            server = NettyServerBuilder.forPort(3000)
                    .addService(new ResultInJsonImpl())
                    .build()
                    .start();

            wrapped = NettyChannelBuilder.forAddress("127.0.0.1", 3000).
                    usePlaintext(true).maxInboundMessageSize(10 * 4194304).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void rpcClientTest() {

        if (null == server || null == wrapped) {
            try {
                Thread.currentThread().sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        QueryProtocol.QueryRequest request = QueryProtocol.QueryRequest.newBuilder().setQueryString("(showschema)").build();
        QueryProtocol.QueryResponse queryResponse = ClientCalls.blockingUnaryCall(
                wrapped,
                METHOD_QUERY,
                CallOptions.DEFAULT
                        .withDeadlineAfter(5000000,
                                TimeUnit.MILLISECONDS),
                request);

        System.out.println(queryResponse);

    }

    private class ResultInJsonImpl extends QueryServiceInterfaceGrpc.QueryServiceInterfaceImplBase {

        @Override
        public void queryToJson(QueryProtocol.QueryRequest request,
                                StreamObserver<QueryProtocol.ResultInJson> responseObserver) {
            QueryProtocol.ResultInJson reply =
                    QueryProtocol.ResultInJson.newBuilder()
                            .setResult(request.getQueryString()
                                    + " from server").build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void query(QueryProtocol.QueryRequest request,
                          StreamObserver<QueryProtocol.QueryResponse> responseObserver) {

            int value = -1;
            String requestString = request.getQueryString().trim();
            long delayMilli = 0L;
            if (qualificationOn && !qualifiedCallers.contains(request.getCaller())) {
                QueryProtocol.QueryResponse reply =
                        QueryProtocol.QueryResponse.newBuilder()
                                .setError(24)
                                .setErrMsg("Access denied")
                                .build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
                return;
            }
            if (requestString.startsWith("port")) {
                QueryProtocol.QueryResponse reply =
                        QueryProtocol.QueryResponse.newBuilder()
                                .addResult(
                                        QueryProtocol.QueryResponseOneRow.newBuilder()
                                                .addReturnFields(
                                                        QueryProtocol.Value.newBuilder()
                                                                .setIntVal(3000)
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

            QueryProtocol.QueryResponse reply = QueryProtocol.QueryResponse.newBuilder()
                    .addResult(QueryProtocol.QueryResponseOneRow.newBuilder()
                            .addReturnFields(QueryProtocol.Value.newBuilder()
                                    .setIntVal(value)
                                    .build())
                            .build())
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void getShardmap(
                QueryProtocol.ShardRequest request,
                StreamObserver<QueryProtocol.HostShardMap> responseObserver) {
            QueryProtocol.HostShardMap.HostShard hostShard =
                    QueryProtocol.HostShardMap.HostShard.newBuilder().setHostport("127.0.0.1:60000").build();
            QueryProtocol.HostShardMap shardMap =
                    QueryProtocol.HostShardMap.newBuilder().addEntry(hostShard).build();
            responseObserver.onNext(shardMap);
            responseObserver.onCompleted();

        }

        /**
         * Client should never touch queryInternal
         */
        @Override
        public void queryInternal(
                QueryProtocol.QueryInternalRequest request,
                StreamObserver<QueryProtocol.QueryResponse> responseObserver) {
            QueryProtocol.QueryResponse reply = QueryProtocol.QueryResponse.getDefaultInstance();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();

        }

        @Override
        public void getStatus(
                Empty request,
                StreamObserver<QueryProtocol.StatusResponse> responseObserver) {

            QueryProtocol.StatusResponse reply =
                    QueryProtocol.StatusResponse.newBuilder()
                            .setStatus(QueryProtocol.StatusResponse.Status.READY)
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
