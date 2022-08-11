package io.getarrays.server.controller;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Response;
import io.getarrays.server.model.Server;
import io.getarrays.server.service.implementation.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
@CrossOrigin
public class Controller {
    private final ServerServiceImpl serverService;
    @GetMapping("/list")
    public ResponseEntity<Response> getServer() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);

        return ResponseEntity.ok(//responseEntity.ok is a response given by the server to the client in the case of success
                Response.builder()//using the @superbuilder we can build our class and give it different parameters
                        .timeStamp(now())
                        .data(Map.of("servers", serverService.List(30)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipadress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipadress") String ipadress) throws IOException {
        Server server = serverService.ping(ipadress);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", server))
                        .message(server.getStatus() == Status.Server_Up ? "Ping success" : "Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")//when saving something in the backend we use postmapping
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {//grab the body of the request
        //which will contain the server the @valid will check for the validation

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", serverService.create(server)))
                        .message("server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }


    @GetMapping("/get/{id}")//get the id and the path of the url
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {//get the id as a long from the path
        //that exists in the http request
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", serverService.get(id)))//get the server and pass it as data
                        .message("server retreived")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")//get the id and the path of the url
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {//get the id as a long

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", serverService.delete(id)))//get the server and pass it as data
                        .message("server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)//get the id and the path of thez url
    public byte[]  getServerImage(@PathVariable("fileName") String fileName) throws IOException {//get the id as a long

        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Desktop/ahmed/study/SpringBoot/server/images" + fileName));
    }
}
