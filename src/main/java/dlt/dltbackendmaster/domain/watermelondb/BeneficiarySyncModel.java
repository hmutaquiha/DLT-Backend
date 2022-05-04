package dlt.dltbackendmaster.domain.watermelondb;

import java.util.Date;

import dlt.dltbackendmaster.domain.DeficiencyType;
import dlt.dltbackendmaster.domain.LivesWith;

public class BeneficiarySyncModel extends BasicLifeCycleSyncModel
{
    private String nui;
    private String surname;
    private String nick_name;
    private Integer organization_id;
    private Date date_of_birth;
    private Character gender;
    private String address;
    private String phone_number;
    private String e_mail;
    private LivesWith lives_with;
    private Boolean is_orphan;
    private Boolean via;
    private Integer partner_id;
    private Boolean is_student;
    private Integer grade;
    private String school_name;
    private Boolean is_deficient;
    private DeficiencyType deficiency_type;
    private String entry_point;
    private Integer neighbourhood_id;
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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Integer getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Integer organization_id) {
        this.organization_id = organization_id;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String email) {
        this.e_mail = email;
    }

    public LivesWith getLives_with() {
        return lives_with;
    }

    public void setLives_with(LivesWith lives_with) {
        this.lives_with = lives_with;
    }

    public Boolean getIs_orphan() {
        return is_orphan;
    }

    public void setIs_orphan(Boolean is_orphan) {
        this.is_orphan = is_orphan;
    }

    public Boolean getVia() {
        return via;
    }

    public void setVia(Boolean via) {
        this.via = via;
    }

    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
    }

    public Boolean getIs_student() {
        return is_student;
    }

    public void setIs_student(Boolean is_student) {
        this.is_student = is_student;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public Boolean getIs_deficient() {
        return is_deficient;
    }

    public void setIs_deficient(Boolean is_deficient) {
        this.is_deficient = is_deficient;
    }

    public DeficiencyType getDeficiency_type() {
        return deficiency_type;
    }

    public void setDeficiency_type(DeficiencyType deficiency_type) {
        this.deficiency_type = deficiency_type;
    }

    public String getEntry_point() {
        return entry_point;
    }

    public void setEntry_point(String entry_point) {
        this.entry_point = entry_point;
    }

    public Integer getNeighbourhood_id() {
        return neighbourhood_id;
    }

    public void setNeighbourhood_id(Integer neighborhood_id) {
        this.neighbourhood_id = neighborhood_id;
    }

    public Integer getUs_id() {
        return us_id;
    }

    public void setUs_id(Integer us_id) {
        this.us_id = us_id;
    }
}
