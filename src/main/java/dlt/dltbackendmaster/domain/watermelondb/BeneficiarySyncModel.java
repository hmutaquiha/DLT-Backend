package dlt.dltbackendmaster.domain.watermelondb;

import java.util.Date;

import dlt.dltbackendmaster.domain.Beneficiary;
import dlt.dltbackendmaster.domain.DeficiencyType;
import dlt.dltbackendmaster.domain.LivesWith;

public class BeneficiarySyncModel extends BasicLifeCycleSyncModel
{
    private String nui;
    private String surname;
    private String nickName;
    private Date dateOfBirth;
    private Character gender;
    private String address;
    private String phoneNumber;
    private String email;
    private LivesWith livesWith;
    private Boolean isOrphan;
    private Boolean via;
    private Beneficiary partner;
    private Boolean isStudent;
    private Integer grade;
    private String schoolName;
    private Boolean isDeficient;
    private DeficiencyType deficiencyType;
    private String entryPoint;
    private Integer neighborhood_id;
    private Integer us_id;

    public BeneficiarySyncModel() {}

    public String getNui() {
        return nui;
    }

    public void setNui(String nui) {
        this.nui = nui;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LivesWith getLivesWith() {
        return livesWith;
    }

    public void setLivesWith(LivesWith livesWith) {
        this.livesWith = livesWith;
    }

    public Boolean getIsOrphan() {
        return isOrphan;
    }

    public void setIsOrphan(Boolean isOrphan) {
        this.isOrphan = isOrphan;
    }

    public Boolean getVia() {
        return via;
    }

    public void setVia(Boolean via) {
        this.via = via;
    }

    public Beneficiary getPartner() {
        return partner;
    }

    public void setPartner(Beneficiary partner) {
        this.partner = partner;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Boolean getIsDeficient() {
        return isDeficient;
    }

    public void setIsDeficient(Boolean isDeficient) {
        this.isDeficient = isDeficient;
    }

    public DeficiencyType getDeficiencyType() {
        return deficiencyType;
    }

    public void setDeficiencyType(DeficiencyType deficiencyType) {
        this.deficiencyType = deficiencyType;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public Integer getNeighborhood_id() {
        return neighborhood_id;
    }

    public void setNeighborhood_id(Integer neighborhood_id) {
        this.neighborhood_id = neighborhood_id;
    }

    public Integer getUs_id() {
        return us_id;
    }

    public void setUs_id(Integer us_id) {
        this.us_id = us_id;
    }
}
