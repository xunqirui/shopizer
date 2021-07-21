package org.geektimes.microprofile.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * ShutdownEndPointService
 *
 * @author qrXun on 2021/7/21
 */
@Path("/actuator")
public interface ActuatorEndPointService {

    @POST
    @Path("/shutdown")
    String shutdown();


}
