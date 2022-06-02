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
    private String vbltLivesWith;
    private Boolean vbltIsOrphan;
    private Boolean vbltIsStudent;
    private Integer vbltSchoolGrade;
    private String vbltSchoolName;
    private Boolean vbltIsDeficient;
    private String vbltDeficiencyType;
    private Boolean vbltMarriedBefore;
    private Boolean vbltPregnantBefore;
    private Boolean vbltChildren;
    private Boolean vbltPregnantOrBreastfeeding;
    private Boolean vbltIsEmployed;
    private Boolean vbltTestedHiv;
    private Boolean vbltSexuallyActive;
    private Boolean vbltMultiplePartners;
    private Boolean vbltIsMigrant;
    private Boolean vbltTraffickingVictim;
    private Boolean vbltSexualExploitation;
    private String vbltSexploitationTime;
    private Boolean vbltVbgVictim;
    private String vbltVgbType;
    private String vbltVbgTime;
    private Boolean vbltAlcoholDrugsUse;
    private Boolean vbltStiHistory;
    private Boolean vbltSexWorker;
    private Boolean vbltHouseSustainer;
    
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
        this.vbltLivesWith = model.getVblt_lives_with();
        this.vbltIsOrphan = model.getVblt_is_orphan();
        this.vbltIsStudent = model.getVblt_is_student();
        this.vbltSchoolGrade = model.getVblt_school_grade();
        this.vbltSchoolName = model.getVblt_school_name();
        this.vbltIsDeficient = model.getVblt_is_deficient();
        this.vbltDeficiencyType = model.getVblt_deficiency_type();
        this.vbltMarriedBefore = model.getVblt_married_before();
        this.vbltPregnantBefore = model.getVblt_pregnant_before();
        this.vbltChildren = model.getVblt_children();
        this.vbltPregnantOrBreastfeeding = model.getVblt_pregnant_or_breastfeeding();
        this.vbltIsEmployed = model.getVblt_is_employed();
        this.vbltTestedHiv = model.getVblt_tested_hiv();
        this.vbltSexuallyActive = model.getVblt_sexually_active();
        this.vbltMultiplePartners = model.getVblt_multiple_partners();
        this.vbltIsMigrant = model.getVblt_is_migrant();
        this.vbltTraffickingVictim = model.getVblt_trafficking_victim();
        this.vbltSexualExploitation = model.getVblt_sexual_exploitation();
        this.vbltSexploitationTime = model.getVblt_sexploitation_time();
        this.vbltVbgVictim = model.getVblt_vbg_victim();
        this.vbltVgbType = model.getVblt_vbg_type();
        this.vbltVbgTime = model.getVblt_vbg_time();
        this.vbltAlcoholDrugsUse = model.getVblt_alcohol_drugs_use();
        this.vbltStiHistory = model.getVblt_sti_history();
        this.vbltSexWorker = model.getVblt_sex_worker();
        this.vbltHouseSustainer = model.getVblt_house_sustainer();
        
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
		return vbltLivesWith;
	}


	public void setVbltLivesWith(String vbltLivesWith) {
		this.vbltLivesWith = vbltLivesWith;
	}

	@Column(name = "vblt_is_orphan")
	public Boolean getVbltIsOrphan() {
		return vbltIsOrphan;
	}


	public void setVbltIsOrphan(Boolean vbltIsOrphan) {
		this.vbltIsOrphan = vbltIsOrphan;
	}

	@Column(name = "vblt_is_student")
	public Boolean getVbltIsStudent() {
		return this.vbltIsStudent;
	}


	public void setVbltIsStudent(Boolean vbltIsStudent) {
		this.vbltIsStudent = vbltIsStudent;
	}

	@Column(name = "vblt_school_grade")
	public Integer getVbltSchoolGrade() {
		return this.vbltSchoolGrade;
	}


	public void setVbltSchoolGrade(Integer vbltSchoolGrade) {
		this.vbltSchoolGrade = vbltSchoolGrade;
	}

	@Column(name = "vblt_school_name", nullable = true, length = 45)
	public String getVbltSchoolName() {
		return this.vbltSchoolName;
	}


	public void setVbltSchoolName(String vbltSchoolName) {
		this.vbltSchoolName = vbltSchoolName;
	}

	@Column(name = "vblt_is_deficient")
	public Boolean getVbltIsDeficient() {
		return this.vbltIsDeficient;
	}


	public void setVbltIsDeficient(Boolean vbltIsDeficient) {
		this.vbltIsDeficient = vbltIsDeficient;
	}

	@Column(name = "vblt_deficiency_type", nullable = true, length = 45)
	public String getVbltDeficiencyType() {
		return this.vbltDeficiencyType;
	}


	public void setVbltDeficiencyType(String vbltDeficiencyType) {
		this.vbltDeficiencyType = vbltDeficiencyType;
	}

	@Column(name = "vblt_married_before")
	public Boolean getVbltMarriedBefore() {
		return this.vbltMarriedBefore;
	}


	public void setVbltMarriedBefore(Boolean vbltMarriedBefore) {
		this.vbltMarriedBefore = vbltMarriedBefore;
	}

	@Column(name = "vblt_pregnant_before")
	public Boolean getVbltPregnantBefore() {
		return this.vbltPregnantBefore;
	}


	public void setVbltPregnantBefore(Boolean vbltPregnantBefore) {
		this.vbltPregnantBefore = vbltPregnantBefore;
	}

	@Column(name = "vblt_children")
	public Boolean getVbltChildren() {
		return this.vbltChildren;
	}


	public void setVbltChildren(Boolean vbltChildren) {
		this.vbltChildren = vbltChildren;
	}

	@Column(name = "vblt_pregnant_or_breastfeeding")
	public Boolean getVbltPregnantOrBreastfeeding() {
		return this.vbltPregnantOrBreastfeeding;
	}


	public void setVbltPregnantOrBreastfeeding(Boolean vbltPregnantOrBreastfeeding) {
		this.vbltPregnantOrBreastfeeding = vbltPregnantOrBreastfeeding;
	}

	@Column(name = "vblt_is_employed")
	public Boolean getVbltIsEmployed() {
		return this.vbltIsEmployed;
	}


	public void setVbltIsEmployed(Boolean vbltIsEmployed) {
		this.vbltIsEmployed = vbltIsEmployed;
	}

	@Column(name = "vblt_tested_hiv")
	public Boolean getVbltTestedHiv() {
		return this.vbltTestedHiv;
	}


	public void setVbltTestedHiv(Boolean vbltTestedHiv) {
		this.vbltTestedHiv = vbltTestedHiv;
	}

	@Column(name = "vblt_sexually_active")
	public Boolean getVbltSexuallyActive() {
		return this.vbltSexuallyActive;
	}

	
	public void setVbltSexuallyActive(Boolean vbltSexuallyActive) {
		this.vbltSexuallyActive = vbltSexuallyActive;
	}

	@Column(name = "vblt_multiple_partners")
	public Boolean getVbltMultiplePartners() {
		return this.vbltMultiplePartners;
	}


	public void setVbltMultiplePartners(Boolean vbltMultiplePartners) {
		this.vbltMultiplePartners = vbltMultiplePartners;
	}

	@Column(name = "vblt_is_migrant")
	public Boolean getVbltIsMigrant() {
		return this.vbltIsMigrant;
	}


	public void setVbltIsMigrant(Boolean vbltIsMigrant) {
		this.vbltIsMigrant = vbltIsMigrant;
	}

	@Column(name = "vblt_trafficking_victim")
	public Boolean getVbltTraffickingVictim() {
		return this.vbltTraffickingVictim;
	}


	public void setVbltTraffickingVictim(Boolean vbltTraffickingVictim) {
		this.vbltTraffickingVictim = vbltTraffickingVictim;
	}

	@Column(name = "vblt_sexual_exploitation")
	public Boolean getVbltSexualExploitation() {
		return this.vbltSexualExploitation;
	}


	public void setVbltSexualExploitation(Boolean vbltSexualExploitation) {
		this.vbltSexualExploitation = vbltSexualExploitation;
	}

	@Column(name = "vblt_sexploitation_time", nullable = true, length = 45)
	public String getVbltSexploitationTime() {
		return this.vbltSexploitationTime;
	}


	public void setVbltSexploitationTime(String vbltSexploitationTime) {
		this.vbltSexploitationTime = vbltSexploitationTime;
	}

	@Column(name = "vblt_vbg_victim")
	public Boolean getVbltVbgVictim() {
		return this.vbltVbgVictim;
	}


	public void setVbltVbgVictim(Boolean vbltVbgVictim) {
		this.vbltVbgVictim = vbltVbgVictim;
	}

	@Column(name = "vblt_vbg_type", nullable = true, length = 45)
	public String getVbltVgbType() {
		return this.vbltVgbType;
	}


	public void setVbltVgbType(String vbltVgbType) {
		this.vbltVgbType = vbltVgbType;
	}

	@Column(name = "vblt_vbg_time", nullable = true, length = 45)
	public String getVbltVbgTime() {
		return this.vbltVbgTime;
	}


	public void setVbltVbgTime(String vbltVbgTime) {
		this.vbltVbgTime = vbltVbgTime;
	}

	@Column(name = "vblt_alcohol_drugs_use")
	public Boolean getVbltAlcoholDrugsUse() {
		return this.vbltAlcoholDrugsUse;
	}


	public void setVbltAlcoholDrugsUse(Boolean vbltAlcoholDrugsUse) {
		this.vbltAlcoholDrugsUse = vbltAlcoholDrugsUse;
	}

	@Column(name = "vblt_sti_history")
	public Boolean getVbltStiHistory() {
		return this.vbltStiHistory;
	}


	public void setVbltStiHistory(Boolean vbltStiHistory) {
		this.vbltStiHistory = vbltStiHistory;
	}

	@Column(name = "vblt_sex_worker")
	public Boolean getVbltSexWorker() {
		return this.vbltSexWorker;
	}


	public void setVbltSexWorker(Boolean vbltSexWorker) {
		this.vbltSexWorker = vbltSexWorker;
	}

	@Column(name = "vblt_house_sustainer")
	public Boolean getVbltHouseSustainer() {
		return this.vbltHouseSustainer;
	}


	public void setVbltHouseSustainer(Boolean vbltHouseSustainer) {
		this.vbltHouseSustainer = vbltHouseSustainer;
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
            beneficiary.put("vblt_lives_with", vbltLivesWith);
            beneficiary.put("vblt_is_orphan", vbltIsOrphan);
            beneficiary.put("vblt_is_student", vbltIsStudent);
            beneficiary.put("vblt_school_grade", vbltSchoolGrade);
            beneficiary.put("vblt_school_name", vbltSchoolName);
            beneficiary.put("vblt_is_deficient", vbltIsDeficient);
            beneficiary.put("vblt_deficiency_type", vbltDeficiencyType);
            beneficiary.put("vblt_married_before", vbltMarriedBefore);
            beneficiary.put("vblt_pregnant_before", vbltPregnantBefore);
            beneficiary.put("vblt_children", vbltChildren);
            beneficiary.put("vblt_pregnant_or_breastfeeding", vbltPregnantOrBreastfeeding);
            beneficiary.put("vblt_is_employed", vbltIsEmployed);
            beneficiary.put("vblt_tested_hiv", vbltTestedHiv);
            beneficiary.put("vblt_sexually_active", vbltSexuallyActive);
            beneficiary.put("vblt_multiple_partners", vbltMultiplePartners);
            beneficiary.put("vblt_is_migrant", vbltIsMigrant);
            beneficiary.put("vblt_trafficking_victim", vbltTraffickingVictim);
            beneficiary.put("vblt_sexual_exploitation", vbltSexualExploitation);
            beneficiary.put("vblt_sexploitation_time", vbltSexploitationTime);
            beneficiary.put("vblt_vbg_victim", vbltVbgVictim);
            beneficiary.put("vblt_vbg_type", vbltVgbType);
            beneficiary.put("vblt_vbg_time", vbltVbgTime);
            beneficiary.put("vblt_alcohol_drugs_use", vbltAlcoholDrugsUse);
            beneficiary.put("vblt_sti_history", vbltStiHistory);
            beneficiary.put("vblt_sex_worker", vbltSexWorker);
            beneficiary.put("vblt_house_sustainer", vbltHouseSustainer);
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
        this.vbltLivesWith = model.getVblt_lives_with();
        this.vbltIsOrphan = model.getVblt_is_orphan();
        this.vbltIsStudent = model.getVblt_is_student();
        this.vbltSchoolGrade = model.getVblt_school_grade();
        this.vbltSchoolName = model.getVblt_school_name();
        this.vbltIsDeficient = model.getVblt_is_deficient();
        this.vbltDeficiencyType = model.getVblt_deficiency_type();
        this.vbltMarriedBefore = model.getVblt_married_before();
        this.vbltPregnantBefore = model.getVblt_pregnant_before();
        this.vbltChildren = model.getVblt_children();
        this.vbltPregnantOrBreastfeeding = model.getVblt_pregnant_or_breastfeeding();
        this.vbltIsEmployed = model.getVblt_is_employed();
        this.vbltTestedHiv = model.getVblt_tested_hiv();
        this.vbltSexuallyActive = model.getVblt_sexually_active();
        this.vbltMultiplePartners = model.getVblt_multiple_partners();
        this.vbltIsMigrant = model.getVblt_is_migrant();
        this.vbltTraffickingVictim = model.getVblt_trafficking_victim();
        this.vbltSexualExploitation = model.getVblt_sexual_exploitation();
        this.vbltSexploitationTime = model.getVblt_sexploitation_time();
        this.vbltVbgVictim = model.getVblt_vbg_victim();
        this.vbltVgbType = model.getVblt_vbg_type();
        this.vbltVbgTime = model.getVblt_vbg_time();
        this.vbltAlcoholDrugsUse = model.getVblt_alcohol_drugs_use();
        this.vbltStiHistory = model.getVblt_sti_history();
        this.vbltSexWorker = model.getVblt_sex_worker();
        this.vbltHouseSustainer = model.getVblt_house_sustainer();
    }
}
