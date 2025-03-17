DROP PROCEDURE refresh_mv_now;

DELIMITER $$

CREATE PROCEDURE refresh_mv_now (
    IN end_date VARCHAR(10),
    OUT rc INT
)
BEGIN

    TRUNCATE TABLE agyw_prev_mview;

    SET @@SESSION.sql_mode='NO_ZERO_DATE,NO_ZERO_IN_DATE';

    INSERT INTO agyw_prev_mview
    select a.*,
      if (a.flag_vulnerable = 1, 1, if (current_age between 15 and 17,general_vulnerabilities>1 or specific_vulnerabilities>0,general_vulnerabilities>0 or specific_vulnerabilities>0)) vulnerable,
      sab.level
   from
   (
      select a.*,
         if(registration_age between 9 and 14,1,if (registration_age between 15 and 19,2,if (registration_age between 20 and 24,3,if (registration_age between 25 and 29,4,5)))) registration_age_band,
         if(current_age between 9 and 14,1,if (current_age between 15 and 19,2,if (current_age between 20 and 24,3,if (current_age between 25 and 29,4,5)))) current_age_band,
         max(intervention_date) service_date,
         if (current_age between 9 and 17,vblt_house_sustainer+!vblt_is_student+vblt_is_deficient+vblt_married_before+coalesce(vblt_idp,0),
            if (current_age between 18 and 24,vblt_is_deficient+coalesce(vblt_idp,0),0)) general_vulnerabilities,
         if (current_age between 9 and 17,(current_age<15 and vblt_sexually_active)+vblt_pregnant_or_has_children+vblt_multiple_partners+vblt_sexual_exploitation_trafficking_victim+vblt_gbv_victim+vblt_alcohol_drugs_use+vblt_sti_history,
            if (current_age between 18 and 24,vblt_multiple_partners+vblt_sexual_exploitation_trafficking_victim+coalesce(vblt_sex_worker,0)+vblt_gbv_victim+vblt_alcohol_drugs_use+vblt_sti_history,0)) specific_vulnerabilities
      from
      (
         select @counter:=@counter+1 id, 
            p.id as province_id,
            d.id as district_id,
            n.id as neighborhood_id,
            b.id as beneficiary_id,
            bus.id as beneficiary_us_id,
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
            b.vblt_sexual_exploitation_trafficking_victim,
            b.vblt_sexually_active,
            b.vblt_pregnant_or_has_children,
            b.vblt_multiple_partners,
            b.vblt_vbg_victim vblt_gbv_victim,
            b.vblt_vbg_type vblt_gbv_type,
            b.vblt_sex_worker,
            b.vblt_alcohol_drugs_use,
            b.vblt_sti_history,
            b.clinical_interventions,
            b.community_interventions,
            b.completion_status,
            b.vulnerable flag_vulnerable,
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
         left join us bus on b.us_id = bus.id
         where b.status=1
      ) a
       where intervention_date <= now()
      group by beneficiary_id, sub_service_id
   ) a
   left join services_agebands sab on sab.service_id = a.service_id and sab.age_band = a.current_age_band;

    SET rc = 0;
END;
$$

DELIMITER ;