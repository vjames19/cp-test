package com.github.vjames19.cptest.api.endpoint

import com.github.vjames19.cptest.UserId
import com.github.vjames19.cptest.network.NetworkService
import org.jooby.mvc.GET
import org.jooby.mvc.Path
import javax.inject.Inject

@Path("/network")
class NetworkEndpoint @Inject constructor(val networkService: NetworkService) {

    @GET
    @Path("/maxConnections")
    fun userWithMaxConnections() = networkService.getUserWithMaxConnections()

    @GET
    @Path("/minConnections")
    fun userWithMinConnections() = networkService.getUserWithMinConnections()

    @GET
    @Path("/:id")
    fun getUser(id: UserId) = networkService.getUser(id)

    @GET
    @Path("/:id/connections/size")
    fun numberOfConnections(id: UserId) = networkService.numberOfConnections(id)

    @GET
    @Path("/:id/connections/common/:userId")
    fun numberOfConnectionsInCommon(id: UserId, userId: UserId) = networkService.numberOfCommonConnections(id, userId)

    @GET
    @Path("/:id/introduce/:userId")
    fun whoCanIntroduce(id: UserId, userId: UserId) = networkService.whoCanIntroduce(id, userId)
}