package dlt.dltbackendmaster.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeficiencyType {
    NO_WALK("Não Anda"),
    NO_TALK("Não fala"),
    NO_SEE("Não Vê"),
    NO_LISTEN("Não Ouve"),
    HANDICAP("Tem Algum Membro Amputado ou Deformado");

    private String description;

    private DeficiencyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    @JsonCreator
    public static DeficiencyType forName(String name) {
        for(DeficiencyType c: values()) {
            if(c.name().equals(name)) {
                return c;
            }
        }

        return null;
    }
}
