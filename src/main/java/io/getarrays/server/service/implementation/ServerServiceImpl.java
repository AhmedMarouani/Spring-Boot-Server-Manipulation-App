package io.getarrays.server.service.implementation;

import io.getarrays.server.service.ServerService;
import io.getarrays.server.model.Server;
import io.getarrays.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static io.getarrays.server.enumeration.Status.Server_Down;
import static io.getarrays.server.enumeration.Status.Server_Up;
import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j//for the log
public class ServerServiceImpl implements ServerService {

    @Autowired
    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {//log something in the console, set the image, save the server
        log.info("Saving new server: {}", server.getName());
        server.setImageurl(setServerImageUrl());
        return serverRepository.save(server);
    }


    @Override
    public Server ping(String ipadress) throws IOException {
        log.info("pinging server ip: {}", ipadress);
        Server server = serverRepository.findByipadress(ipadress);//get inet address for a specific ipaddress
        InetAddress address = InetAddress.getByName(ipadress);
        server.setStatus(address.isReachable(10000) ? Server_Up : Server_Down);//if we can reach the server
        //within the timeout than we set the server status to UP if not ServerDown
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> List(int Limit) {
        log.info("fetching all servers ");
        return serverRepository.findAll(PageRequest.of(0, Limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("fetching servers by id: {}", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by id: {}", id);
        serverRepository.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/"
                + imageNames[new Random().nextInt(4)]).toUriString();
    }
}
