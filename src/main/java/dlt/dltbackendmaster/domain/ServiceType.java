package dlt.dltbackendmaster.domain;

public enum ServiceType {
    CLINIC("Serviços Clínicos"),
    COMMUNITY("Serviços Comunitário");

    private String description;

    private ServiceType(String descritiption) {
        this.description = descritiption;
    }

    public String getDescription() {
        return this.description;
    }
}
