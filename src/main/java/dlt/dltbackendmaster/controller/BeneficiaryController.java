package dlt.dltbackendmaster.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.NumericSequence;
import dlt.dltbackendmaster.domain.VulnerabilityHistory;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.SequenceGenerator;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController
{
    private final DAOService service;

    private SequenceGenerator generator;

    public BeneficiaryController(DAOService service) {
        this.service = service;
        this.generator = new SequenceGenerator(service);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Beneficiaries>> get(@RequestParam(name = "profile") Integer profile, @RequestParam(name = "level") String level, @RequestParam(name = "params",
                                                                                                                                                              required = false) @Nullable Integer[] params) {

        try {
            List<Beneficiaries> beneficiaries = null;

            if (level.equals("CENTRAL")) {
                beneficiaries = service.getAll(Beneficiaries.class);
            } else if (level.equals("PROVINCIAL")) {
                beneficiaries = service.GetAllEntityByNamedQuery("Beneficiary.findByProvinces", Arrays.asList(params));
            } else if (level.equals("DISTRITAL")) {
                beneficiaries = service.GetAllEntityByNamedQuery("Beneficiary.findByDistricts", Arrays.asList(params));
            } else {
                beneficiaries = service.GetAllEntityByNamedQuery("Beneficiary.findByLocalities", Arrays.asList(params));
            }

            return new ResponseEntity<List<Beneficiaries>>(beneficiaries, HttpStatus.OK);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Beneficiaries> get(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            Beneficiaries beneficiary = service.find(Beneficiaries.class, id);
            return new ResponseEntity<>(beneficiary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Beneficiaries> save(@RequestBody Beneficiaries beneficiary) {

        if (beneficiary == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            beneficiary.setDateCreated(new Date());
            String partnerNUI = beneficiary.getPartnerNUI();

            if (partnerNUI != null && partnerNUI != "") {
                Beneficiaries partner = service.GetUniqueEntityByNamedQuery("Beneficiary.findByNui", partnerNUI);
                // TODO: Throw exception for NUI not found
                if (partner != null)
                    beneficiary.setPartnerId(partner.getId());
            }
            NumericSequence nextNUI = generator.getNextNumericSequence();
            beneficiary.setNui(nextNUI.getSequence());
            Integer beneficiaryId = (Integer) service.Save(beneficiary);
            Beneficiaries createdBeneficiary = service.find(Beneficiaries.class, beneficiaryId);
            return new ResponseEntity<>(createdBeneficiary, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Beneficiaries> update(@RequestBody Beneficiaries beneficiary) {

        if (beneficiary == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            beneficiary.setDateUpdated(new Date());
            String partnerNUI = beneficiary.getPartnerNUI();

            if (partnerNUI != null && partnerNUI != "") {
                Beneficiaries partner = service.GetUniqueEntityByNamedQuery("Beneficiary.findByNui", partnerNUI);
                // TODO: Throw exception for NUI not found
                if (partner != null)
                    beneficiary.setPartnerId(partner.getId());
            } else {
				beneficiary.setPartnerId(null);
			}
			Beneficiaries updatedBeneficiary = service.update(beneficiary);

			saveVulnerabilityHistory(updatedBeneficiary);

			return new ResponseEntity<>(updatedBeneficiary, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void saveVulnerabilityHistory(Beneficiaries beneficiary) {
		createVulnerability("DEFICIENCY_TYPE", beneficiary.getVbltDeficiencyType(), beneficiary);
		createVulnerability("IS_EMPLOYED", beneficiary.getVbltIsEmployed(), beneficiary);
		createVulnerability("LIVES_WITH", beneficiary.getVbltLivesWith(), beneficiary);
		createVulnerability("SCHOOL_NAME", beneficiary.getVbltSchoolName(), beneficiary);
		createVulnerability("SEXPLOITATION_TIME", beneficiary.getVbltSexploitationTime(), beneficiary);
		createVulnerability("TESTED_HIV", beneficiary.getVbltTestedHiv(), beneficiary);
		createVulnerability("VBG_TIME", beneficiary.getVbltVbgTime(), beneficiary);
		createVulnerability("VBG_TYPE", beneficiary.getVbltVbgType(), beneficiary);
		createVulnerability("ALCOHOL_DRUGS_USE", String.valueOf(beneficiary.getVbltAlcoholDrugsUse()), beneficiary);
		createVulnerability("CHILDREN", String.valueOf(beneficiary.getVbltChildren()), beneficiary);
		createVulnerability("HOUSE_SUSTAINER", String.valueOf(beneficiary.getVbltHouseSustainer()), beneficiary);
		createVulnerability("IS_DEFICIENT", String.valueOf(beneficiary.getVbltIsDeficient()), beneficiary);
		createVulnerability("IS_MIGRANT", String.valueOf(beneficiary.getVbltIsMigrant()), beneficiary);
		createVulnerability("IS_ORPHAN", String.valueOf(beneficiary.getVbltIsOrphan()), beneficiary);
		createVulnerability("IS_STUDENT", String.valueOf(beneficiary.getVbltIsStudent()), beneficiary);
		createVulnerability("MARRIED_BEFORE", String.valueOf(beneficiary.getVbltMarriedBefore()), beneficiary);
		createVulnerability("MULTIPLE_PARTNERS", String.valueOf(beneficiary.getVbltMultiplePartners()), beneficiary);
		createVulnerability("PREGNANT_BEFORE", String.valueOf(beneficiary.getVbltPregnantBefore()), beneficiary);
		createVulnerability("PREGNANT_OR_BREASTFEEDING", String.valueOf(beneficiary.getVbltPregnantOrBreastfeeding()),
				beneficiary);
		createVulnerability("SCHOOL_GRADE", String.valueOf(beneficiary.getVbltSchoolGrade()), beneficiary);
		createVulnerability("SEXUAL_EXPLOITATION", String.valueOf(beneficiary.getVbltSexualExploitation()),
				beneficiary);
		createVulnerability("SEXUALLY_ACTIVE", String.valueOf(beneficiary.getVbltSexuallyActive()), beneficiary);
		createVulnerability("SEX_WORKER", String.valueOf(beneficiary.getVbltSexWorker()), beneficiary);
		createVulnerability("STI_HISTORY", String.valueOf(beneficiary.getVbltStiHistory()), beneficiary);
		createVulnerability("TRAFFICKING_VICTIM", String.valueOf(beneficiary.getVbltTraffickingVictim()), beneficiary);
		createVulnerability("VBG_VICTIM", String.valueOf(beneficiary.getVbltVbgVictim()), beneficiary);
	}

	private void createVulnerability(String vulnerabilityKey, String vulnerabilityValue, Beneficiaries beneficiary) {
		VulnerabilityHistory history = new VulnerabilityHistory();

		history.setBeneficiaries(beneficiary);
		history.setVulnerability(vulnerabilityKey);
		history.setValue(vulnerabilityValue);
		history.setStatus(beneficiary.getStatus());
		history.setCreatedBy(String.valueOf(beneficiary.getCreatedBy()));
		history.setDateCreated(beneficiary.getDateCreated());

		try {
		
			List<VulnerabilityHistory> vulnerabilityHistoryOrdered = service.GetAllEntityByNamedQuery(
					"VulnerabilityHistory.findByBeneficiaryAndVulnerability", beneficiary.getId(), vulnerabilityKey);

			if (vulnerabilityHistoryOrdered.isEmpty() || !vulnerabilityValue.equals(vulnerabilityHistoryOrdered.get(0).getValue())) {
				service.Save(history);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
