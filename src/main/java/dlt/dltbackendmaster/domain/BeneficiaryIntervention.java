package dlt.dltbackendmaster.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dlt.dltbackendmaster.domain.watermelondb.BeneficiaryInterventionSyncModel;
import dlt.dltbackendmaster.serializers.BeneficiarySerializer;
import dlt.dltbackendmaster.serializers.SubServiceSerializer;

@SuppressWarnings("serial")
@Entity
@Table(name = "beneficiaries_interventions")
@NamedQueries({ @NamedQuery(name = "BeneficiaryIntervention.findAll",
                            query = "SELECT b FROM BeneficiaryIntervention b"),
                @NamedQuery(name = "BeneficiaryIntervention.findByBeneficiaryAndSubService",
                            query = "SELECT b FROM BeneficiaryIntervention b where b.beneficiary.id = :beneficiary_id and b.subService.id = :sub_service_id"),
                @NamedQuery(name = "BeneficiaryIntervention.findByDateCreated",
                            query = "select b from BeneficiaryIntervention b where b.dateUpdated is null and b.dateCreated > :lastpulledat"),
                @NamedQuery(name = "BeneficiaryIntervention.findByDateUpdated",
                            query = "select b from BeneficiaryIntervention b where (b.dateUpdated >= :lastpulledat) or (b.dateUpdated >= :lastpulledat and b.dateCreated = b.dateUpdated)") })
public class BeneficiaryIntervention implements Serializable
{
    private Beneficiary beneficiary;
    private SubService subService;
    private String result;
    private Date date;
    private Integer us_id;
    private Integer activistId;
    private Integer entryPoint;
    private String provider;
    private String remarks;
    private Integer status;
    private Integer createdBy;
    private Date dateCreated;
    private Integer updatedBy;
    private Date dateUpdated;
    private String offlineId;

    public BeneficiaryIntervention() {}

    public BeneficiaryIntervention(Beneficiary beneficiary, SubService subService, String result, Date date,
                                   Integer us_id, Integer activistId, Integer entryPoint, String provider,
                                   String remarks, Integer status, Integer createdBy, Date dateCreated,
                                   Integer updatedBy, Date dateUpdated) {
        super();
        this.beneficiary = beneficiary;
        this.subService = subService;
        this.result = result;
        this.date = date;
        this.us_id = us_id;
        this.activistId = activistId;
        this.entryPoint = entryPoint;
        this.provider = provider;
        this.remarks = remarks;
        this.status = status;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.updatedBy = updatedBy;
        this.dateUpdated = dateUpdated;
    }

    public BeneficiaryIntervention(BeneficiaryInterventionSyncModel model, String timestamp) {
        Long t = Long.valueOf(timestamp);
        Date regDate = new Date(t);
        this.beneficiary = new Beneficiary(model.getBeneficiary_id());
        this.subService = new SubService(model.getSubService_id());
        this.result = model.getResult();
        this.date = model.getDate();
        this.us_id = model.getUs_id();
        this.activistId = model.getActivist_id();
        this.entryPoint = model.getEntryPoint();
        this.provider = model.getProvider();
        this.remarks = model.getRemarks();
        this.status = model.getStatus();
        this.offlineId = model.getBeneficiary_id() + "" + model.getSubService_id();
        this.dateCreated = regDate;
        this.dateUpdated = regDate;
    }

    @JsonIgnore
    @Id
    @ManyToOne
    @MapsId("beneficiaryId")
    @JsonProperty("beneficiary")
    @JsonSerialize(using = BeneficiarySerializer.class)
    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Id
    @ManyToOne
    @MapsId("subServiceId")
    @JoinColumn(name = "sub_service_id")
    @JsonProperty("subService")
    @JsonSerialize(using = SubServiceSerializer.class)
    public SubService getSubService() {
        return subService;
    }

    public void setSubService(SubService subService) {
        this.subService = subService;
    }

    @Column(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "us_id")
    public Integer getUs_id() {
        return us_id;
    }

    public void setUs_id(Integer us_id) {
        this.us_id = us_id;
    }

    @Column(name = "activist_id")
    public Integer getActivistId() {
        return activistId;
    }

    public void setActivistId(Integer activistId) {
        this.activistId = activistId;
    }

    @Column(name = "entry_point")
    public Integer getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(Integer entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Column(name = "provider")
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "created_by")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "date_created")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = "updated_by")
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "date_updated")
    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Column(name = "offline_id", nullable = true, length = 45)
    public String getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(String offlineId) {
        this.offlineId = offlineId;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode beneficiaryIntervention = mapper.createObjectNode();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (offlineId != null) {
            beneficiaryIntervention.put("id", offlineId);
        } else {
            beneficiaryIntervention.put("id", beneficiary.getId() + "" + subService.getId());
        }

        if (dateUpdated == null || dateUpdated.after(dateCreated)) {
            beneficiaryIntervention.put("beneficiary_id", beneficiary.getId());
            beneficiaryIntervention.put("subService_id", subService.getId());
            beneficiaryIntervention.put("result", result);
            beneficiaryIntervention.put("date", dateFormat.format(date));
            beneficiaryIntervention.put("us_id", us_id);
            beneficiaryIntervention.put("activist_id", activistId);
            beneficiaryIntervention.put("entry_point", entryPoint);
            beneficiaryIntervention.put("provider", provider);
            beneficiaryIntervention.put("remarks", remarks);
            beneficiaryIntervention.put("status", status);
            beneficiaryIntervention.put("online_id", beneficiary.getId() + "" + subService.getId()); // flag to control if entity is synchronized with the backend
        } else { // ensure online_id is updated first
            beneficiaryIntervention.put("online_id", beneficiary.getId() + "" + subService.getId());
        }
        return beneficiaryIntervention;
    }

    public void update(BeneficiaryInterventionSyncModel model, String timestamp) {
        Long t = Long.valueOf(timestamp);
        
        this.offlineId = model.getBeneficiary_id() + "" + model.getSubService_id();
        this.dateUpdated = new Date(t);
        this.beneficiary.setId(model.getBeneficiary_id());
        this.subService.setId(model.getSubService_id());
        this.result = model.getResult();
        this.date = model.getDate();
        this.us_id = model.getUs_id();
        this.activistId = model.getActivist_id();
        this.entryPoint = model.getEntryPoint();
        this.provider = model.getProvider();
        this.remarks = model.getRemarks();
        this.status = model.getStatus();
    }
}
