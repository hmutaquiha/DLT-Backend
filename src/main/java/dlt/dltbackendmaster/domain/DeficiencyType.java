package dlt.dltbackendmaster.domain;

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
}
