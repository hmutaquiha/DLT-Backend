package dlt.dltbackendmaster.domain;

import java.util.List;

public class ReferencesServicesObject
{
    private BeneficiariesInterventions intervention;

    private List<References> references;

    public ReferencesServicesObject(BeneficiariesInterventions intervention, List<References> references) {
        super();
        this.intervention = intervention;
        this.references = references;
    }

    public BeneficiariesInterventions getIntervention() {
        return intervention;
    }

    public void setIntervention(BeneficiariesInterventions intervention) {
        this.intervention = intervention;
    }

    public List<References> getReferences() {
        return references;
    }

    public void setReferences(List<References> references) {
        this.references = references;
    }

}
