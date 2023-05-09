package com.kai.netty;

import dto.RpcRequest;

/**
 * The transport server interface, define the basic of a server.
 *
 * @author wangkaiping
 * @date 2023.4.26
 * @modify 2023.4.28
 */
public interface ServiceTransport {

    /**
     * The action of the client endpoint. To send the Rpc request to the server,
     * and generate the results from the response of server.
     * @param rpcRequest the rpc Request object
     * @return the results object
     */
    public Object sendRpcRequest(RpcRequest rpcRequest);
}
