package io.getarrays.server.enumeration;

public enum Status {
    Server_Up("Server_Up"),
    Server_Down("Server_Up");
    private final String status;

    Status(String status) {
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
}
