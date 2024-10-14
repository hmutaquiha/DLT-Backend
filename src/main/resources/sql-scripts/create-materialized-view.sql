DROP TABLE agyw_prev_mview;

CREATE TABLE agyw_prev_mview
(
   id int NOT NULL AUTO_INCREMENT,
   province_id int,
   district_id int DEFAULT 0 NOT NULL,
   neighborhood_id int DEFAULT 0,
   beneficiary_id int DEFAULT 0 NOT NULL,
   gender smallint,
   nui varchar(61),
   organization_id int,
   entry_point int NOT NULL,
   date_of_birth varchar(25),
   created_by int,
   date_created varchar(25),
   updated_by int,
   date_updated varchar(25),
   enrollment_date varchar(25),
   registration_age int,
   current_age int,
   vblt_lives_with varchar(150),
   vblt_is_orphan int,
   vblt_house_sustainer int,
   vblt_is_student int,
   vblt_is_deficient int,
   vblt_deficiency_type varchar(250),
   vblt_married_before int,
   vblt_pregnant_before int,
   vblt_children int,
   vblt_pregnant_or_breastfeeding int,
   vblt_is_employed int,
   vblt_idp int,
   vblt_tested_hiv int,
   vblt_sexual_exploitation int,
   vblt_is_migrant int,
   vblt_trafficking_victim int,
   vblt_sexually_active int,
   vblt_pregnant_or_has_children int,
   vblt_multiple_partners int,
   vblt_gbv_victim int,
   vblt_sex_worker int,
   vblt_alcohol_drugs_use int,
   vblt_sti_history int,
   completion_status int,
   service_type int,
   service_id int,
   sub_service_id int,
   service_entry_point int,
   us_id int,
   intervention_date varchar(25),
   intervention_date_created varchar(25),
   intervention_date_updated varchar(25),
   provider varchar(250),
   remarks varchar(250),
   intervention_status int,
   registration_age_band int,
   current_age_band int,
   service_date varchar(25),
   general_vulnerabilities int DEFAULT 0,
   specific_vulnerabilities int DEFAULT 0,
   vulnerable int,
   level int,
   PRIMARY KEY (`id`)
);

SET @@SESSION.sql_mode='NO_ZERO_DATE,NO_ZERO_IN_DATE';

set @counter=0;

INSERT INTO agyw_prev_mview
select a.*,
   if (current_age between 15 and 17,general_vulnerabilities>1 or specific_vulnerabilities>0,general_vulnerabilities>0 or specific_vulnerabilities>0) vulnerable,
   sab.level
from
(
   select a.*,
      if(registration_age between 9 and 14,1,if (registration_age between 15 and 19,2,if (registration_age between 20 and 24,3,if (registration_age between 25 and 29,4,5)))) registration_age_band,
      if(current_age between 9 and 14,1,if (current_age between 15 and 19,2,if (current_age between 20 and 24,3,if (current_age between 25 and 29,4,5)))) current_age_band,
      max(intervention_date) service_date,
      if (current_age between 9 and 17,vblt_house_sustainer+!vblt_is_student+vblt_is_deficient+vblt_pregnant_or_has_children+vblt_idp,
         if (current_age between 18 and 24,vblt_is_deficient+vblt_idp,0)) general_vulnerabilities,
      if (current_age between 9 and 17,current_age<15 and vblt_sexually_active+vblt_pregnant_or_breastfeeding+vblt_multiple_partners+vblt_sexual_exploitation+vblt_gbv_victim+vblt_alcohol_drugs_use+vblt_sti_history,
         if (current_age between 18 and 24,vblt_multiple_partners+vblt_sexual_exploitation+vblt_sex_worker+vblt_gbv_victim+vblt_alcohol_drugs_use+vblt_sti_history,0)) specific_vulnerabilities
   from
   (
      select @counter:=@counter+1 id, 
         p.id as province_id,
         d.id as district_id,
         n.id as neighborhood_id,
         b.id as beneficiary_id,
         b.gender,
         concat(d.code,'/',b.nui) nui,
         b.organization_id,
         b.entry_point,
         b.date_of_birth,
         b.created_by,
         b.date_created,
         b.updated_by,
         b.date_updated,
         b.enrollment_date,
         floor(datediff(b.date_created,b.date_of_birth)/365) registration_age,
         floor(datediff(now(),b.date_of_birth)/365) current_age,
         b.vblt_lives_with,
         b.vblt_is_orphan,
         b.vblt_house_sustainer,
         b.vblt_is_student,
         b.vblt_is_deficient,
         b.vblt_deficiency_type,
         b.vblt_married_before,
         b.vblt_pregnant_before,
         b.vblt_children,
         b.vblt_pregnant_or_breastfeeding,
         if(b.vblt_is_employed='Não Trabalha',0,if(b.vblt_is_employed='Empregada Doméstica',1,if(b.vblt_is_employed='Babá (Cuida das Crianças)',2,if(b.vblt_is_employed='Outros',3,b.vblt_is_employed)))) vblt_is_employed,
         b.vblt_idp,
         b.vblt_tested_hiv,
         b.vblt_sexual_exploitation,
         b.vblt_is_migrant,
         b.vblt_trafficking_victim,
         b.vblt_sexually_active,
         b.vblt_pregnant_or_has_children,
         b.vblt_multiple_partners,
         b.vblt_vbg_victim vblt_gbv_victim,
         b.vblt_sex_worker,
         b.vblt_alcohol_drugs_use,
         b.vblt_sti_history,
         b.completion_status,
         s.service_type,
         s.id service_id,
         ss.id sub_service_id,
         bi.entry_point service_entry_point,
         us.id us_id,
         bi.date intervention_date,
         bi.date_created intervention_date_created,
         bi.date_updated intervention_date_updated,
         bi.provider,
         bi.remarks,
         bi.status intervention_status
      from beneficiaries b
      inner join district d on b.district_id = d.id
      inner join province p on d.province_id = p.id
      left join neighborhood n on b.neighbourhood_id = n.id
      left join users u on b.created_by = u.id
      left join partners pa on u.partner_id = pa.id
      left join beneficiaries_interventions bi on bi.beneficiary_id = b.id
      left join sub_services ss on bi.sub_service_id = ss.id
      left join services s on ss.service_id = s.id
      left join us on bi.us_id = us.id
      where b.status=1
   ) a
    where intervention_date <= now()
   group by beneficiary_id, sub_service_id
) a
left join services_agebands sab on sab.service_id = a.service_id and sab.age_band = a.current_age_band;