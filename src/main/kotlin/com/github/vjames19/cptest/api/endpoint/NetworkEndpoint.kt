package com.github.vjames19.cptest.api.endpoint

import com.github.vjames19.cptest.UserId
import com.github.vjames19.cptest.network.NetworkService
import org.jooby.mvc.GET
import org.jooby.mvc.Path
import java.util.*
import javax.inject.Inject

@Path("/network")
class NetworkEndpoint @Inject constructor(val networkService: NetworkService) {

    @GET
    @Path("/maxConnections")
    fun userWithMaxConnections(degree: Optional<Int>) =
            networkService.getUserWithMaxConnections(degreeOrDefault(degree))

    @GET
    @Path("/minConnections")
    fun userWithMinConnections(degree: Optional<Int>) =
            networkService.getUserWithMinConnections(degreeOrDefault(degree))

    @GET
    @Path("/:id")
    fun getUser(id: UserId) =
            networkService.getUser(id)

    @GET
    @Path("/:id/connections/size")
    fun numberOfConnections(id: UserId, degree: Optional<Int>) =
            networkService.numberOfConnections(id, degreeOrDefault(degree))

    @GET
    @Path("/:id/connections/common/:userId")
    fun numberOfConnectionsInCommon(id: UserId, userId: UserId, degree: Optional<Int>) =
            networkService.numberOfCommonConnections(id, userId, degreeOrDefault(degree))

    @GET
    @Path("/:id/introduce/:toUserId")
    fun whoCanIntroduce(id: UserId, toUserId: UserId, degree: Optional<Int>) =
            networkService.whoCanIntroduce(id, toUserId, degreeOrDefault(degree))

    private fun degreeOrDefault(degree: Optional<Int>) = degree.orElseGet { NetworkService.DEFAULT_DEGREE }
}