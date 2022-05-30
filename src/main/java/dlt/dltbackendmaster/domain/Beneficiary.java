package dlt.dltbackendmaster.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dlt.dltbackendmaster.domain.watermelondb.BeneficiarySyncModel;
import dlt.dltbackendmaster.serializers.NeighborhoodSerializer;
import dlt.dltbackendmaster.serializers.PartnersSerializer;

@SuppressWarnings("serial")
@Entity
@Table(name = "beneficiaries", catalog = "dreams_db")
@NamedQueries({ @NamedQuery(name = "Beneficiary.findAll", query = "SELECT b FROM Beneficiary b"),
                @NamedQuery(name = "Beneficiary.findByDateCreated",
                            query = "select b from Beneficiary b where b.dateUpdated is null and b.dateCreated > :lastpulledat"),
                @NamedQuery(name = "Beneficiary.findByDateUpdated",
                            query = "select b from Beneficiary b where (b.dateUpdated >= :lastpulledat) or (b.dateUpdated >= :lastpulledat and b.dateCreated = b.dateUpdated)") })
public class Beneficiary extends BasicLifeCycle implements Serializable
{
    private String nui;
    private String surname;
    private String nickName;
    private Partners organization;
    private Date dateOfBirth;
    private Character gender;
    private String address;
    private String phoneNumber;
    private String email;
    private Boolean via;
    private Beneficiary partnerId;
    private String entryPoint;
    private Neighborhood neighborhood;
    private Integer usId;
    private Partners partner;
    private String VbltLivesWith;
    private Boolean VbltIsOrphan;
    private Boolean VbltIsStudent;
    private Integer VbltSchoolGrade;
    private String VbltSchoolName;
    private Boolean VbltIsDeficient;
    private String VbltDeficiencyType;
    private Boolean VbltMarriedBefore;
    private Boolean VbltPregnantBefore;
    private Boolean VbltChildren;
    private Boolean VbltPregnantOrBreastfeeding;
    private Boolean VbltIsEmployed;
    private Boolean VbltTestedHiv;
    private Boolean VbltSexuallyActive;
    private Boolean VbltMultiplePartners;
    private Boolean VbltIsMigrant;
    private Boolean VbltTraffickingVictim;
    private Boolean VbltSexualExploitation;
    private String VbltSexploitationTime;
    private Boolean VbltVbgVictim;
    private String VbltVgbType;
    private String VbltVbgTime;
    private Boolean VbltAlcoholDrugsUse;
    private Boolean VbltStiHistory;
    private Boolean VbltSexWorker;
    private Boolean VbltHouseSustainer;
    
    private Set<BeneficiaryIntervention> interventions;
   

    public Beneficiary() {}

    
    public Beneficiary(BeneficiarySyncModel model, String timestamp) {
        super(model.getName(), Integer.valueOf(model.getStatus()));
        Long t = Long.valueOf(timestamp);
        Date regDate = new Date(t);
        this.nui = model.getNui();
        this.surname = model.getSurname();
        this.nickName = model.getNick_name();
        this.organization = new Partners(model.getOrganization_id());
        this.dateOfBirth = model.getDate_of_birth();
        this.gender = model.getGender();
        this.address = model.getAddress();
        this.phoneNumber = model.getPhone_number();
        this.email = model.getE_mail();
        this.via = model.getVia();
        this.partnerId = new Beneficiary(model.getPartner_id());
        this.entryPoint = model.getEntry_point();
        this.neighborhood = new Neighborhood(model.getNeighbourhood_id());
        this.usId = model.getUs_id();
        this.offlineId = model.getId();
        this.dateCreated = regDate;
        this.dateUpdated = regDate;
        this.VbltLivesWith = model.getVblt_lives_with();
        this.VbltIsOrphan = model.getVblt_is_orphan();
        this.VbltIsStudent = model.getVblt_is_student();
        this.VbltSchoolGrade = model.getVblt_school_grade();
        this.VbltSchoolName = model.getVblt_school_name();
        this.VbltIsDeficient = model.getVblt_is_deficient();
        this.VbltDeficiencyType = model.getVblt_deficiency_type();
        this.VbltMarriedBefore = model.getVblt_married_before();
        this.VbltPregnantBefore = model.getVblt_pregnant_before();
        this.VbltChildren = model.getVblt_children();
        this.VbltPregnantOrBreastfeeding = model.getVblt_pregnant_or_breastfeeding();
        this.VbltIsEmployed = model.getVblt_is_employed();
        this.VbltTestedHiv = model.getVblt_tested_hiv();
        this.VbltSexuallyActive = model.getVblt_sexually_active();
        this.VbltMultiplePartners = model.getVblt_multiple_partners();
        this.VbltIsMigrant = model.getVblt_is_migrant();
        this.VbltTraffickingVictim = model.getVblt_trafficking_victim();
        this.VbltSexualExploitation = model.getVblt_sexual_exploitation();
        this.VbltSexploitationTime = model.getVblt_sexploitation_time();
        this.VbltVbgVictim = model.getVblt_vbg_victim();
        this.VbltVgbType = model.getVblt_vbg_type();
        this.VbltVbgTime = model.getVblt_vbg_time();
        this.VbltAlcoholDrugsUse = model.getVblt_alcohol_drugs_use();
        this.VbltStiHistory = model.getVblt_sti_history();
        this.VbltSexWorker = model.getVblt_sex_worker();
        this.VbltHouseSustainer = model.getVblt_house_sustainer();
        
    }

    public Beneficiary(Integer id) {
        this.id = id;
    }

    @Column(name = "nui", unique = true, nullable = false)
    public String getNui() {
        return nui;
    }

    public void setNui(String nui) {
        this.nui = nui;
    }

    @Column(name = "surname", nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    @JsonProperty("organization")
    @JsonSerialize(using = PartnersSerializer.class)
    public Partners getOrganization() {
        return organization;
    }

    public void setOrganization(Partners organization) {
        this.organization = organization;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "gender")
    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "e_mail")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name = "via")
    public Boolean getVia() {
        return via;
    }

    public void setVia(Boolean via) {
        this.via = via;
    }
    
    @Column(name = "entry_point")
    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "neighbourhood_id")
    @JsonProperty("neighborhood")
    @JsonSerialize(using = NeighborhoodSerializer.class)
    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Column(name = "us_id")
    public Integer getUsId() {
        return usId;
    }

    public void setUsId(Integer usId) {
        this.usId = usId;
    }
    
    @OneToOne
    @JoinColumn(name = "partner_id")
    public Beneficiary getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Beneficiary partner) {
        this.partnerId = partner;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner")
    @JsonProperty("partner")
    public Partners getPartner() {
		return partner;
	}

	public void setPartner(Partners partner) {
		this.partner = partner;
	}

	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiary_id")
    public Set<BeneficiaryIntervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(Set<BeneficiaryIntervention> interventions) {
        this.interventions = interventions;
    }
    
    @Column(name = "offline_id", nullable = true, length = 45)
    public String getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(String offlineId) {
        this.offlineId = offlineId;
    }
    
    
    @Column(name = "vblt_lives_with", nullable = true, length = 45)
    public String getVbltLivesWith() {
		return VbltLivesWith;
	}


	public void setVbltLivesWith(String vbltLivesWith) {
		VbltLivesWith = vbltLivesWith;
	}

	@Column(name = "vblt_is_orphan")
	public Boolean getVbltIsOrphan() {
		return VbltIsOrphan;
	}


	public void setVbltIsOrphan(Boolean vbltIsOrphan) {
		VbltIsOrphan = vbltIsOrphan;
	}

	@Column(name = "vblt_is_student")
	public Boolean getVbltIsStudent() {
		return VbltIsStudent;
	}


	public void setVbltIsStudent(Boolean vbltIsStudent) {
		VbltIsStudent = vbltIsStudent;
	}

	@Column(name = "vblt_school_grade")
	public Integer getVbltSchoolGrade() {
		return VbltSchoolGrade;
	}


	public void setVbltSchoolGrade(Integer vbltSchoolGrade) {
		VbltSchoolGrade = vbltSchoolGrade;
	}

	@Column(name = "vblt_school_name", nullable = true, length = 45)
	public String getVbltSchoolName() {
		return VbltSchoolName;
	}


	public void setVbltSchoolName(String vbltSchoolName) {
		VbltSchoolName = vbltSchoolName;
	}

	@Column(name = "vblt_is_deficient")
	public Boolean getVbltIsDeficient() {
		return VbltIsDeficient;
	}


	public void setVbltIsDeficient(Boolean vbltIsDeficient) {
		VbltIsDeficient = vbltIsDeficient;
	}

	@Column(name = "vblt_deficiency_type", nullable = true, length = 45)
	public String getVbltDeficiencyType() {
		return VbltDeficiencyType;
	}


	public void setVbltDeficiencyType(String vbltDeficiencyType) {
		VbltDeficiencyType = vbltDeficiencyType;
	}

	@Column(name = "vblt_married_before")
	public Boolean getVbltMarriedBefore() {
		return VbltMarriedBefore;
	}


	public void setVbltMarriedBefore(Boolean vbltMarriedBefore) {
		VbltMarriedBefore = vbltMarriedBefore;
	}

	@Column(name = "vblt_pregnant_before")
	public Boolean getVbltPregnantBefore() {
		return VbltPregnantBefore;
	}


	public void setVbltPregnantBefore(Boolean vbltPregnantBefore) {
		VbltPregnantBefore = vbltPregnantBefore;
	}

	@Column(name = "vblt_children")
	public Boolean getVbltChildren() {
		return VbltChildren;
	}


	public void setVbltChildren(Boolean vbltChildren) {
		VbltChildren = vbltChildren;
	}

	@Column(name = "vblt_pregnant_or_breastfeeding")
	public Boolean getVbltPregnantOrBreastfeeding() {
		return VbltPregnantOrBreastfeeding;
	}


	public void setVbltPregnantOrBreastfeeding(Boolean vbltPregnantOrBreastfeeding) {
		VbltPregnantOrBreastfeeding = vbltPregnantOrBreastfeeding;
	}

	@Column(name = "vblt_is_employed")
	public Boolean getVbltIsEmployed() {
		return VbltIsEmployed;
	}


	public void setVbltIsEmployed(Boolean vbltIsEmployed) {
		VbltIsEmployed = vbltIsEmployed;
	}

	@Column(name = "vblt_tested_hiv")
	public Boolean getVbltTestedHiv() {
		return VbltTestedHiv;
	}


	public void setVbltTestedHiv(Boolean vbltTestedHiv) {
		VbltTestedHiv = vbltTestedHiv;
	}

	@Column(name = "vblt_sexually_active")
	public Boolean getVbltSexuallyActive() {
		return VbltSexuallyActive;
	}

	
	public void setVbltSexuallyActive(Boolean vbltSexuallyActive) {
		VbltSexuallyActive = vbltSexuallyActive;
	}

	@Column(name = "vblt_multiple_partners")
	public Boolean getVbltMultiplePartners() {
		return VbltMultiplePartners;
	}


	public void setVbltMultiplePartners(Boolean vbltMultiplePartners) {
		VbltMultiplePartners = vbltMultiplePartners;
	}

	@Column(name = "vblt_is_migrant")
	public Boolean getVbltIsMigrant() {
		return VbltIsMigrant;
	}


	public void setVbltIsMigrant(Boolean vbltIsMigrant) {
		VbltIsMigrant = vbltIsMigrant;
	}

	@Column(name = "vblt_trafficking_victim")
	public Boolean getVbltTraffickingVictim() {
		return VbltTraffickingVictim;
	}


	public void setVbltTraffickingVictim(Boolean vbltTraffickingVictim) {
		VbltTraffickingVictim = vbltTraffickingVictim;
	}

	@Column(name = "vblt_sexual_exploitation")
	public Boolean getVbltSexualExploitation() {
		return VbltSexualExploitation;
	}


	public void setVbltSexualExploitation(Boolean vbltSexualExploitation) {
		VbltSexualExploitation = vbltSexualExploitation;
	}

	@Column(name = "vblt_sexploitation_time", nullable = true, length = 45)
	public String getVbltSexploitationTime() {
		return VbltSexploitationTime;
	}


	public void setVbltSexploitationTime(String vbltSexploitationTime) {
		VbltSexploitationTime = vbltSexploitationTime;
	}

	@Column(name = "vblt_vbg_victim")
	public Boolean getVbltVbgVictim() {
		return VbltVbgVictim;
	}


	public void setVbltVbgVictim(Boolean vbltVbgVictim) {
		VbltVbgVictim = vbltVbgVictim;
	}

	@Column(name = "vblt_vbg_type", nullable = true, length = 45)
	public String getVbltVgbType() {
		return VbltVgbType;
	}


	public void setVbltVgbType(String vbltVgbType) {
		VbltVgbType = vbltVgbType;
	}

	@Column(name = "vblt_vbg_time", nullable = true, length = 45)
	public String getVbltVbgTime() {
		return VbltVbgTime;
	}


	public void setVbltVbgTime(String vbltVbgTime) {
		VbltVbgTime = vbltVbgTime;
	}

	@Column(name = "vblt_alcohol_drugs_use")
	public Boolean getVbltAlcoholDrugsUse() {
		return VbltAlcoholDrugsUse;
	}


	public void setVbltAlcoholDrugsUse(Boolean vbltAlcoholDrugsUse) {
		VbltAlcoholDrugsUse = vbltAlcoholDrugsUse;
	}

	@Column(name = "vblt_sti_history")
	public Boolean getVbltStiHistory() {
		return VbltStiHistory;
	}


	public void setVbltStiHistory(Boolean vbltStiHistory) {
		VbltStiHistory = vbltStiHistory;
	}

	@Column(name = "vblt_sex_worker")
	public Boolean getVbltSexWorker() {
		return VbltSexWorker;
	}


	public void setVbltSexWorker(Boolean vbltSexWorker) {
		VbltSexWorker = vbltSexWorker;
	}

	@Column(name = "vblt_house_sustainer")
	public Boolean getVbltHouseSustainer() {
		return VbltHouseSustainer;
	}


	public void setVbltHouseSustainer(Boolean vbltHouseSustainer) {
		VbltHouseSustainer = vbltHouseSustainer;
	}


    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode beneficiary = mapper.createObjectNode();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (offlineId != null) {
            beneficiary.put("id", offlineId);
        } else {
            beneficiary.put("id", id);
        }

        if (dateUpdated == null || dateUpdated.after(dateCreated)) {
            beneficiary.put("id", id);
            beneficiary.put("nui", nui);
            beneficiary.put("name", name);
            beneficiary.put("surname", surname);
            beneficiary.put("nickname", nickName);
            beneficiary.put("organization_id", organization.getId());
            beneficiary.put("date_of_birth", dateFormat.format(dateOfBirth));
            beneficiary.put("gender", String.valueOf(gender));
            beneficiary.put("adress", address);
            beneficiary.put("phone_number", phoneNumber);
            beneficiary.put("email", email);
            beneficiary.put("via", via);
            beneficiary.put("partner_id", partnerId == null ? null : partnerId.getId());
            beneficiary.put("entry_point", entryPoint);
            beneficiary.put("neighborhood_id", neighborhood.getId());
            beneficiary.put("us_id", usId);
            beneficiary.put("online_id", id); // flag to control if entity is synchronized with the backend
            beneficiary.put("vblt_lives_with", VbltLivesWith);
            beneficiary.put("vblt_is_orphan", VbltIsOrphan);
            beneficiary.put("vblt_is_student", VbltIsStudent);
            beneficiary.put("vblt_school_grade", VbltSchoolGrade);
            beneficiary.put("vblt_school_name", VbltSchoolName);
            beneficiary.put("vblt_is_deficient", VbltIsDeficient);
            beneficiary.put("vblt_deficiency_type", VbltDeficiencyType);
            beneficiary.put("vblt_married_before", VbltMarriedBefore);
            beneficiary.put("vblt_pregnant_before", VbltPregnantBefore);
            beneficiary.put("vblt_children", VbltChildren);
            beneficiary.put("vblt_pregnant_or_breastfeeding", VbltPregnantOrBreastfeeding);
            beneficiary.put("vblt_is_employed", VbltIsEmployed);
            beneficiary.put("vblt_tested_hiv", VbltTestedHiv);
            beneficiary.put("vblt_sexually_active", VbltSexuallyActive);
            beneficiary.put("vblt_multiple_partners", VbltMultiplePartners);
            beneficiary.put("vblt_is_migrant", VbltIsMigrant);
            beneficiary.put("vblt_trafficking_victim", VbltTraffickingVictim);
            beneficiary.put("vblt_sexual_exploitation", VbltSexualExploitation);
            beneficiary.put("vblt_sexploitation_time", VbltSexploitationTime);
            beneficiary.put("vblt_vbg_victim", VbltVbgVictim);
            beneficiary.put("vblt_vbg_type", VbltVgbType);
            beneficiary.put("vblt_vbg_time", VbltVbgTime);
            beneficiary.put("vblt_alcohol_drugs_use", VbltAlcoholDrugsUse);
            beneficiary.put("vblt_sti_history", VbltStiHistory);
            beneficiary.put("vblt_sex_worker", VbltSexWorker);
            beneficiary.put("vblt_house_sustainer", VbltHouseSustainer);
        } else { // ensure online_id is updated first
            beneficiary.put("online_id", id);
        }
        return beneficiary;
    }

    public void update(BeneficiarySyncModel model, String timestamp) {
        Long t = Long.valueOf(timestamp);
        this.offlineId = model.getId();
        this.dateUpdated = new Date(t);
        this.nui = model.getNui();
        this.name = model.getName();
        this.surname = model.getSurname();
        this.nickName = model.getNick_name();
        this.organization.setId(model.getOrganization_id());
        this.dateOfBirth = model.getDate_of_birth();
        this.gender = model.getGender();
        this.address = model.getAddress();
        this.phoneNumber = model.getPhone_number();
        this.email = model.getE_mail();
        this.via = model.getVia();
        this.partner.setId(model.getPartner_id());
        this.entryPoint = model.getEntry_point();
        this.neighborhood.setId(model.getNeighbourhood_id());
        this.usId = model.getUs_id();
        this.status = Integer.valueOf(model.getStatus());
        this.VbltLivesWith = model.getVblt_lives_with();
        this.VbltIsOrphan = model.getVblt_is_orphan();
        this.VbltIsStudent = model.getVblt_is_student();
        this.VbltSchoolGrade = model.getVblt_school_grade();
        this.VbltSchoolName = model.getVblt_school_name();
        this.VbltIsDeficient = model.getVblt_is_deficient();
        this.VbltDeficiencyType = model.getVblt_deficiency_type();
        this.VbltMarriedBefore = model.getVblt_married_before();
        this.VbltPregnantBefore = model.getVblt_pregnant_before();
        this.VbltChildren = model.getVblt_children();
        this.VbltPregnantOrBreastfeeding = model.getVblt_pregnant_or_breastfeeding();
        this.VbltIsEmployed = model.getVblt_is_employed();
        this.VbltTestedHiv = model.getVblt_tested_hiv();
        this.VbltSexuallyActive = model.getVblt_sexually_active();
        this.VbltMultiplePartners = model.getVblt_multiple_partners();
        this.VbltIsMigrant = model.getVblt_is_migrant();
        this.VbltTraffickingVictim = model.getVblt_trafficking_victim();
        this.VbltSexualExploitation = model.getVblt_sexual_exploitation();
        this.VbltSexploitationTime = model.getVblt_sexploitation_time();
        this.VbltVbgVictim = model.getVblt_vbg_victim();
        this.VbltVgbType = model.getVblt_vbg_type();
        this.VbltVbgTime = model.getVblt_vbg_time();
        this.VbltAlcoholDrugsUse = model.getVblt_alcohol_drugs_use();
        this.VbltStiHistory = model.getVblt_sti_history();
        this.VbltSexWorker = model.getVblt_sex_worker();
        this.VbltHouseSustainer = model.getVblt_house_sustainer();
    }
}
