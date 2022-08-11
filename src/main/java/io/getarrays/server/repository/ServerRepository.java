package io.getarrays.server.repository;

import io.getarrays.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository <Server, Long> {//ctrl+leftClick to enter the class
    Server findByipadress(String ipadress); //whatever u write with findBy... Jpa will select a server that
    //has the same ipadress u pass as a parameter
}
