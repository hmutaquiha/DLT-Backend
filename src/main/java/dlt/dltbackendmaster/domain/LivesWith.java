package dlt.dltbackendmaster.domain;

public enum LivesWith {
    PARENTS("Pais"),
    GRAND_PARENTS("Avós"),
    PARTNER("Parceiro"),
    ALONE("Sozinho"),
    OTHER_FAMILIES("Outros Familiares");

    private String description;

    private LivesWith(String descritiption) {
        this.description = descritiption;
    }

    public String getDescription() {
        return this.description;
    }
}
