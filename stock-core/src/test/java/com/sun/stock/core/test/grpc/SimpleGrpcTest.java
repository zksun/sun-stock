package com.sun.stock.core.test.grpc;

import com.alibaba.geabase.proto.query.QueryProtocol;
import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.ClientCalls;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.alibaba.geabase.proto.query.QueryServiceInterfaceGrpc.METHOD_QUERY;

/**
 * Created by zksun on 2017/8/10.
 */
public class SimpleGrpcTest {

    @Test
    public void realConntctionTest() {
        ManagedChannel wrapped = NettyChannelBuilder.forAddress("10.244.13.168", 13579)
                .usePlaintext(true).maxInboundMessageSize(10 * 4194304).build();

        QueryProtocol.QueryRequest request = QueryProtocol.QueryRequest.newBuilder().setQueryString("(showschema)").build();
        QueryProtocol.QueryResponse queryResponse = ClientCalls.blockingUnaryCall(
                wrapped,
                METHOD_QUERY,
                CallOptions.DEFAULT
                        .withDeadlineAfter(Integer.MAX_VALUE,
                                TimeUnit.MILLISECONDS),
                request);

        System.out.println(queryResponse);
    }

    @Test
    public void checkTime(){
        Long time = 1473170400000000L;
        Date date = new Date(time);
        System.out.println(date.getYear());
    }

}
