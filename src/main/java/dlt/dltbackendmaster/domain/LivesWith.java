package dlt.dltbackendmaster.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

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
    
    @JsonCreator
    public static LivesWith forName(String name) {
        for(LivesWith c: values()) {
            if(c.name().equals(name)) {
                return c;
            }
        }

        return null;
    }
}
