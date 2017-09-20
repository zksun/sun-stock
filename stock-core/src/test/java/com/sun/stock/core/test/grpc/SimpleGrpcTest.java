package com.sun.stock.core.test.grpc;

import com.alibaba.geabase.proto.query.QueryProtocol;
import com.alibaba.myqueue.proto.broker.BrokerProtocol;
import com.google.protobuf.ByteString;
import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.ClientCalls;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.alibaba.geabase.proto.query.QueryServiceInterfaceGrpc.METHOD_QUERY;
import static com.alibaba.myqueue.proto.broker.BrokerInterfaceGrpc.METHOD_PUT_MESSAGE;

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
        synchronized (this) {
            try {
                this.wait(100000000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void myqueueuGrpcTest() throws UnsupportedEncodingException {
        ManagedChannel wrapped = NettyChannelBuilder.forAddress("10.244.13.156", 27200)
                .usePlaintext(true).maxInboundMessageSize(10 * 4194304).build();
        List<BrokerProtocol.MessagePB> list = new ArrayList<>();
        ByteString bytes = ByteString.copyFrom("hello myqueue", "utf-8");
        BrokerProtocol.MessagePB messagePB = BrokerProtocol.MessagePB.newBuilder().setData(bytes)
                .build();
        list.add(messagePB);
        BrokerProtocol.PutMessageRequestPB putMessageRequestPB = BrokerProtocol.PutMessageRequestPB.newBuilder()
                .setProjectName("test_zgw").setTopicName("java").setChannelId(119).addAllMessages(list).build();
        BrokerProtocol.PutMessageResponsePB putMessageResponsePB = ClientCalls.blockingUnaryCall(
                wrapped,
                METHOD_PUT_MESSAGE,
                CallOptions.DEFAULT
                        .withDeadlineAfter(Integer.MAX_VALUE,
                                TimeUnit.MILLISECONDS),
                putMessageRequestPB);
        System.out.println(putMessageResponsePB);
        synchronized (this) {
            try {
                this.wait(100000000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void checkTime() {
        Long time = 1473170400000000L;
        Date date = new Date(time);
        System.out.println(date.getYear());
    }

}
