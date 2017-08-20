package com.alibaba.geabase.proto.query;

import io.grpc.stub.ClientCalls;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.3.0)",
        comments = "Source: query_api.proto")
public final class QueryServiceInterfaceGrpc {

  private QueryServiceInterfaceGrpc() {}

  public static final String SERVICE_NAME = "alibaba.biggraph.proto.QueryServiceInterface";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest,
          com.alibaba.geabase.proto.query.QueryProtocol.ResultInJson> METHOD_QUERY_TO_JSON =
          io.grpc.MethodDescriptor.create(
                  io.grpc.MethodDescriptor.MethodType.UNARY,
                  generateFullMethodName(
                          "alibaba.biggraph.proto.QueryServiceInterface", "queryToJson"),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest.getDefaultInstance()),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.ResultInJson.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest,
          com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse> METHOD_QUERY =
          io.grpc.MethodDescriptor.create(
                  io.grpc.MethodDescriptor.MethodType.UNARY,
                  generateFullMethodName(
                          "alibaba.biggraph.proto.QueryServiceInterface", "query"),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest.getDefaultInstance()),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.alibaba.geabase.proto.query.QueryProtocol.QueryInternalRequest,
          com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse> METHOD_QUERY_INTERNAL =
          io.grpc.MethodDescriptor.create(
                  io.grpc.MethodDescriptor.MethodType.UNARY,
                  generateFullMethodName(
                          "alibaba.biggraph.proto.QueryServiceInterface", "queryInternal"),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.QueryInternalRequest.getDefaultInstance()),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.alibaba.geabase.proto.query.QueryProtocol.ShardRequest,
          com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap> METHOD_GET_SHARDMAP =
          io.grpc.MethodDescriptor.create(
                  io.grpc.MethodDescriptor.MethodType.UNARY,
                  generateFullMethodName(
                          "alibaba.biggraph.proto.QueryServiceInterface", "getShardmap"),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.ShardRequest.getDefaultInstance()),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.alibaba.geabase.proto.query.QueryProtocol.ParserRequest,
          com.alibaba.geabase.proto.query.QueryProtocol.ParserResponse> METHOD_GET_PARSING_TREE =
          io.grpc.MethodDescriptor.create(
                  io.grpc.MethodDescriptor.MethodType.UNARY,
                  generateFullMethodName(
                          "alibaba.biggraph.proto.QueryServiceInterface", "getParsingTree"),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.ParserRequest.getDefaultInstance()),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.ParserResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.protobuf.Empty,
          com.alibaba.geabase.proto.query.QueryProtocol.StatusResponse> METHOD_GET_STATUS =
          io.grpc.MethodDescriptor.create(
                  io.grpc.MethodDescriptor.MethodType.UNARY,
                  generateFullMethodName(
                          "alibaba.biggraph.proto.QueryServiceInterface", "getStatus"),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Empty.getDefaultInstance()),
                  io.grpc.protobuf.ProtoUtils.marshaller(com.alibaba.geabase.proto.query.QueryProtocol.StatusResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QueryServiceInterfaceStub newStub(io.grpc.Channel channel) {
    return new QueryServiceInterfaceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QueryServiceInterfaceBlockingStub newBlockingStub(
          io.grpc.Channel channel) {
    return new QueryServiceInterfaceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static QueryServiceInterfaceFutureStub newFutureStub(
          io.grpc.Channel channel) {
    return new QueryServiceInterfaceFutureStub(channel);
  }

  /**
   */
  public static abstract class QueryServiceInterfaceImplBase implements io.grpc.BindableService {

    /**
     */
    public void queryToJson(com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest request,
                            io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.ResultInJson> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY_TO_JSON, responseObserver);
    }

    /**
     */
    public void query(com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest request,
                      io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY, responseObserver);
    }

    /**
     */
    public void queryInternal(com.alibaba.geabase.proto.query.QueryProtocol.QueryInternalRequest request,
                              io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY_INTERNAL, responseObserver);
    }

    /**
     */
    public void getShardmap(com.alibaba.geabase.proto.query.QueryProtocol.ShardRequest request,
                            io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SHARDMAP, responseObserver);
    }

    /**
     */
    public void getParsingTree(com.alibaba.geabase.proto.query.QueryProtocol.ParserRequest request,
                               io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.ParserResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PARSING_TREE, responseObserver);
    }

    /**
     */
    public void getStatus(com.google.protobuf.Empty request,
                          io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.StatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_STATUS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
              .addMethod(
                      METHOD_QUERY_TO_JSON,
                      asyncUnaryCall(
                              new MethodHandlers<
                                      com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest,
                                      com.alibaba.geabase.proto.query.QueryProtocol.ResultInJson>(
                                      this, METHODID_QUERY_TO_JSON)))
              .addMethod(
                      METHOD_QUERY,
                      asyncUnaryCall(
                              new MethodHandlers<
                                      com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest,
                                      com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse>(
                                      this, METHODID_QUERY)))
              .addMethod(
                      METHOD_QUERY_INTERNAL,
                      asyncUnaryCall(
                              new MethodHandlers<
                                      com.alibaba.geabase.proto.query.QueryProtocol.QueryInternalRequest,
                                      com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse>(
                                      this, METHODID_QUERY_INTERNAL)))
              .addMethod(
                      METHOD_GET_SHARDMAP,
                      asyncUnaryCall(
                              new MethodHandlers<
                                      com.alibaba.geabase.proto.query.QueryProtocol.ShardRequest,
                                      com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap>(
                                      this, METHODID_GET_SHARDMAP)))
              .addMethod(
                      METHOD_GET_PARSING_TREE,
                      asyncUnaryCall(
                              new MethodHandlers<
                                      com.alibaba.geabase.proto.query.QueryProtocol.ParserRequest,
                                      com.alibaba.geabase.proto.query.QueryProtocol.ParserResponse>(
                                      this, METHODID_GET_PARSING_TREE)))
              .addMethod(
                      METHOD_GET_STATUS,
                      asyncUnaryCall(
                              new MethodHandlers<
                                      com.google.protobuf.Empty,
                                      com.alibaba.geabase.proto.query.QueryProtocol.StatusResponse>(
                                      this, METHODID_GET_STATUS)))
              .build();
    }
  }

  /**
   */
  public static final class QueryServiceInterfaceStub extends io.grpc.stub.AbstractStub<QueryServiceInterfaceStub> {
    private QueryServiceInterfaceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryServiceInterfaceStub(io.grpc.Channel channel,
                                      io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryServiceInterfaceStub build(io.grpc.Channel channel,
                                              io.grpc.CallOptions callOptions) {
      return new QueryServiceInterfaceStub(channel, callOptions);
    }

    /**
     */
    public void queryToJson(com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest request,
                            io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.ResultInJson> responseObserver) {
      ClientCalls.asyncUnaryCall(
              getChannel().newCall(METHOD_QUERY_TO_JSON, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void query(com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest request,
                      io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
              getChannel().newCall(METHOD_QUERY, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void queryInternal(com.alibaba.geabase.proto.query.QueryProtocol.QueryInternalRequest request,
                              io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
              getChannel().newCall(METHOD_QUERY_INTERNAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getShardmap(com.alibaba.geabase.proto.query.QueryProtocol.ShardRequest request,
                            io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap> responseObserver) {
      ClientCalls.asyncUnaryCall(
              getChannel().newCall(METHOD_GET_SHARDMAP, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getParsingTree(com.alibaba.geabase.proto.query.QueryProtocol.ParserRequest request,
                               io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.ParserResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
              getChannel().newCall(METHOD_GET_PARSING_TREE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStatus(com.google.protobuf.Empty request,
                          io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.StatusResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
              getChannel().newCall(METHOD_GET_STATUS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class QueryServiceInterfaceBlockingStub extends io.grpc.stub.AbstractStub<QueryServiceInterfaceBlockingStub> {
    private QueryServiceInterfaceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryServiceInterfaceBlockingStub(io.grpc.Channel channel,
                                              io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryServiceInterfaceBlockingStub build(io.grpc.Channel channel,
                                                      io.grpc.CallOptions callOptions) {
      return new QueryServiceInterfaceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.alibaba.geabase.proto.query.QueryProtocol.ResultInJson queryToJson(com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest request) {
      return blockingUnaryCall(
              getChannel(), METHOD_QUERY_TO_JSON, getCallOptions(), request);
    }

    /**
     */
    public com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse query(com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest request) {
      return blockingUnaryCall(
              getChannel(), METHOD_QUERY, getCallOptions(), request);
    }

    /**
     */
    public com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse queryInternal(com.alibaba.geabase.proto.query.QueryProtocol.QueryInternalRequest request) {
      return blockingUnaryCall(
              getChannel(), METHOD_QUERY_INTERNAL, getCallOptions(), request);
    }

    /**
     */
    public com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap getShardmap(com.alibaba.geabase.proto.query.QueryProtocol.ShardRequest request) {
      return blockingUnaryCall(
              getChannel(), METHOD_GET_SHARDMAP, getCallOptions(), request);
    }

    /**
     */
    public com.alibaba.geabase.proto.query.QueryProtocol.ParserResponse getParsingTree(com.alibaba.geabase.proto.query.QueryProtocol.ParserRequest request) {
      return blockingUnaryCall(
              getChannel(), METHOD_GET_PARSING_TREE, getCallOptions(), request);
    }

    /**
     */
    public com.alibaba.geabase.proto.query.QueryProtocol.StatusResponse getStatus(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
              getChannel(), METHOD_GET_STATUS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class QueryServiceInterfaceFutureStub extends io.grpc.stub.AbstractStub<QueryServiceInterfaceFutureStub> {
    private QueryServiceInterfaceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryServiceInterfaceFutureStub(io.grpc.Channel channel,
                                            io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryServiceInterfaceFutureStub build(io.grpc.Channel channel,
                                                    io.grpc.CallOptions callOptions) {
      return new QueryServiceInterfaceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.alibaba.geabase.proto.query.QueryProtocol.ResultInJson> queryToJson(
            com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest request) {
      return futureUnaryCall(
              getChannel().newCall(METHOD_QUERY_TO_JSON, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse> query(
            com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest request) {
      return futureUnaryCall(
              getChannel().newCall(METHOD_QUERY, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse> queryInternal(
            com.alibaba.geabase.proto.query.QueryProtocol.QueryInternalRequest request) {
      return futureUnaryCall(
              getChannel().newCall(METHOD_QUERY_INTERNAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap> getShardmap(
            com.alibaba.geabase.proto.query.QueryProtocol.ShardRequest request) {
      return futureUnaryCall(
              getChannel().newCall(METHOD_GET_SHARDMAP, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.alibaba.geabase.proto.query.QueryProtocol.ParserResponse> getParsingTree(
            com.alibaba.geabase.proto.query.QueryProtocol.ParserRequest request) {
      return futureUnaryCall(
              getChannel().newCall(METHOD_GET_PARSING_TREE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.alibaba.geabase.proto.query.QueryProtocol.StatusResponse> getStatus(
            com.google.protobuf.Empty request) {
      return futureUnaryCall(
              getChannel().newCall(METHOD_GET_STATUS, getCallOptions()), request);
    }
  }

  private static final int METHODID_QUERY_TO_JSON = 0;
  private static final int METHODID_QUERY = 1;
  private static final int METHODID_QUERY_INTERNAL = 2;
  private static final int METHODID_GET_SHARDMAP = 3;
  private static final int METHODID_GET_PARSING_TREE = 4;
  private static final int METHODID_GET_STATUS = 5;

  private static final class MethodHandlers<Req, Resp> implements
          io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QueryServiceInterfaceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QueryServiceInterfaceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_TO_JSON:
          serviceImpl.queryToJson((com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest) request,
                  (io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.ResultInJson>) responseObserver);
          break;
        case METHODID_QUERY:
          serviceImpl.query((com.alibaba.geabase.proto.query.QueryProtocol.QueryRequest) request,
                  (io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse>) responseObserver);
          break;
        case METHODID_QUERY_INTERNAL:
          serviceImpl.queryInternal((com.alibaba.geabase.proto.query.QueryProtocol.QueryInternalRequest) request,
                  (io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.QueryResponse>) responseObserver);
          break;
        case METHODID_GET_SHARDMAP:
          serviceImpl.getShardmap((com.alibaba.geabase.proto.query.QueryProtocol.ShardRequest) request,
                  (io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.HostShardMap>) responseObserver);
          break;
        case METHODID_GET_PARSING_TREE:
          serviceImpl.getParsingTree((com.alibaba.geabase.proto.query.QueryProtocol.ParserRequest) request,
                  (io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.ParserResponse>) responseObserver);
          break;
        case METHODID_GET_STATUS:
          serviceImpl.getStatus((com.google.protobuf.Empty) request,
                  (io.grpc.stub.StreamObserver<com.alibaba.geabase.proto.query.QueryProtocol.StatusResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
            io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class QueryServiceInterfaceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.alibaba.geabase.proto.query.QueryProtocol.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (QueryServiceInterfaceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                  .setSchemaDescriptor(new QueryServiceInterfaceDescriptorSupplier())
                  .addMethod(METHOD_QUERY_TO_JSON)
                  .addMethod(METHOD_QUERY)
                  .addMethod(METHOD_QUERY_INTERNAL)
                  .addMethod(METHOD_GET_SHARDMAP)
                  .addMethod(METHOD_GET_PARSING_TREE)
                  .addMethod(METHOD_GET_STATUS)
                  .build();
        }
      }
    }
    return result;
  }
}
