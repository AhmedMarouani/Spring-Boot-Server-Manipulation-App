package io.getarrays.server.service;

import io.getarrays.server.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {//laying down functionalities that we want to have in our app

    Server create (Server server);//this function will return a Server
    Server ping (String ipadress) throws IOException;
    Collection<Server> List(int Limit);//list all the server
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);

}
