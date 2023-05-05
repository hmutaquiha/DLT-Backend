package dlt.dltbackendmaster.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.District;
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Neighborhood;
import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.Profiles;
import dlt.dltbackendmaster.domain.Province;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.Services;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.UsersBeneficiariesCustomSync;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.serializers.SyncSerializer;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.SequenceGenerator;
import dlt.dltbackendmaster.service.UsersBeneficiariesCustomSyncService;

@RestController
@RequestMapping("/custom/sync")
public class CustomSyncController {

	private final DAOService service;
	@Autowired
	private UsersBeneficiariesCustomSyncService usersBeneficiariesCustomSyncService;

	@Autowired
	public CustomSyncController(DAOService service) {
		this.service = service;
		new SequenceGenerator(service);
	}

	@SuppressWarnings({ "rawtypes" })
	@GetMapping(path = "/beneficiaries", produces = "application/json")
	public ResponseEntity get(@RequestParam(name = "lastPulledAt", required = false) @Nullable String lastPulledAt,
			@RequestParam(name = "nui") String nui, @RequestParam(name = "userId") Integer userId)
			throws ParseException {

		List<Users> usersCreated = new ArrayList<Users>();
		List<Users> usersUpdated = new ArrayList<Users>();
		List<Integer> listDeleted;

		List<Locality> localityCreated = new ArrayList<Locality>();
		List<Locality> localityUpdated = new ArrayList<Locality>();

		List<Province> provincesCreated = new ArrayList<Province>();
		List<District> districtsCreated = new ArrayList<District>();

		List<Partners> partnersCreated = new ArrayList<Partners>();
		List<Partners> partnersUpdated = new ArrayList<Partners>();

		List<Profiles> profilesCreated = new ArrayList<Profiles>();
		List<Profiles> profilesUpdated = new ArrayList<Profiles>();

		List<Us> usCreated = new ArrayList<Us>();
		List<Us> usUpdated = new ArrayList<Us>();

		List<Beneficiaries> beneficiariesCreated = new ArrayList<Beneficiaries>();
		List<Beneficiaries> beneficiariesUpdated = new ArrayList<Beneficiaries>();

		List<BeneficiariesInterventions> beneficiariesInterventionsCreated = new ArrayList<BeneficiariesInterventions>();
		List<BeneficiariesInterventions> beneficiariesInterventionsUpdated = new ArrayList<BeneficiariesInterventions>();

		List<Neighborhood> neighborhoodsCreated = new ArrayList<>();
		List<Neighborhood> neighborhoodUpdated = new ArrayList<>();

		List<Services> servicesCreated = new ArrayList<>();
		List<Services> servicesUpdated = new ArrayList<>();

		List<SubServices> subServicesCreated = new ArrayList<>();
		List<SubServices> subServicesUpdated = new ArrayList<>();

		List<References> referencesCreated = new ArrayList<References>();
		List<References> referencesUpdated = new ArrayList<References>();

		List<ReferencesServices> referenceServicesCreated = new ArrayList<ReferencesServices>();
		List<ReferencesServices> referenceServicesUpdated = new ArrayList<ReferencesServices>();

		usersUpdated = new ArrayList<Users>();
		listDeleted = new ArrayList<Integer>();

		// Beneficiary
		List<Users> users = service.GetAllEntityByNamedQuery("Users.findById",userId);
		List <Integer> localitiesIds = new ArrayList<>();
		Set<Locality> localities = users.get(0).getLocalities();
		localities.forEach(locality->{
			localitiesIds.add(locality.getId());
			});
		
		beneficiariesCreated = service.GetAllEntityByNamedQuery("Beneficiary.getBeneficiariesByNui",nui, localitiesIds);

		if (beneficiariesCreated.size() > 0) {

			beneficiariesInterventionsCreated = service.GetAllEntityByNamedQuery(
					"BeneficiaryIntervention.findByBeneficiaryId", beneficiariesCreated.get(0).getId());

			referencesCreated = service.GetAllEntityByNamedQuery("References.findByBeneficiaryId",
					beneficiariesCreated.get(0).getId());

			for (References ref : referencesCreated) {
				List<ReferencesServices> refServicesByRef = new ArrayList<ReferencesServices>();
				refServicesByRef = service.GetAllEntityByNamedQuery("ReferencesServices.findByReference", ref.getId());
				referenceServicesCreated.addAll(refServicesByRef);
			}

			neighborhoodsCreated = service.GetAllEntityByNamedQuery("Neighborhood.findById",
					beneficiariesCreated.get(0).getNeighborhood().getId());

			partnersCreated = service.GetAllEntityByNamedQuery("Partners.findById",
					beneficiariesCreated.get(0).getPartners().getId());

			districtsCreated = service.GetAllEntityByNamedQuery("District.findById",
					beneficiariesCreated.get(0).getDistrict().getId());
			
			provincesCreated = service.GetAllEntityByNamedQuery("Province.findById",
					beneficiariesCreated.get(0).getDistrict().getProvince().getId());
			
			localityCreated = service.GetAllEntityByNamedQuery("Locality.findById",
					beneficiariesCreated.get(0).getLocality().getId());
		}

		try {
			SyncObject<Users> usersSO = new SyncObject<Users>(usersCreated, usersUpdated, listDeleted);
			SyncObject<Province> provinceSO = new SyncObject<Province>(provincesCreated, new ArrayList<Province>(),
					listDeleted);
			SyncObject<District> districtSO = new SyncObject<District>(districtsCreated, new ArrayList<District>(),
					listDeleted);
			SyncObject<Locality> localitySO = new SyncObject<Locality>(localityCreated, localityUpdated, listDeleted);
			SyncObject<Partners> partnersSO = new SyncObject<Partners>(partnersCreated, partnersUpdated, listDeleted);
			SyncObject<Profiles> profilesSO = new SyncObject<Profiles>(profilesCreated, profilesUpdated, listDeleted);
			SyncObject<Us> usSO = new SyncObject<Us>(usCreated, usUpdated, listDeleted);
			SyncObject<Beneficiaries> beneficiarySO = new SyncObject<Beneficiaries>(beneficiariesCreated,
					beneficiariesUpdated, listDeleted);
			SyncObject<BeneficiariesInterventions> beneficiaryInterventionSO = new SyncObject<BeneficiariesInterventions>(
					beneficiariesInterventionsCreated, beneficiariesInterventionsUpdated, listDeleted);
			SyncObject<Neighborhood> neighborhoodSO = new SyncObject<Neighborhood>(neighborhoodsCreated,
					neighborhoodUpdated, listDeleted);
			SyncObject<Services> serviceSO = new SyncObject<Services>(servicesCreated, servicesUpdated, listDeleted);
			SyncObject<SubServices> subServiceSO = new SyncObject<SubServices>(subServicesCreated, subServicesUpdated,
					listDeleted);
			SyncObject<References> referencesSO = new SyncObject<References>(referencesCreated, referencesUpdated,
					listDeleted);
			SyncObject<ReferencesServices> referencesServicesSO = new SyncObject<ReferencesServices>(
					referenceServicesCreated, referenceServicesUpdated, listDeleted);

			String object = SyncSerializer.createSyncObject(usersSO, provinceSO, districtSO, localitySO, profilesSO,
					partnersSO, usSO, beneficiarySO, beneficiaryInterventionSO, neighborhoodSO, serviceSO, subServiceSO,
					referencesSO, referencesServicesSO, lastPulledAt);
			// System.out.println("PULLING " + object);
			
			if(beneficiariesCreated.size()>0) {
				createBeneficiariesAndUsersAssociation(beneficiariesCreated.get(0), users.get(0));
			}

			return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return new ResponseEntity<>("Parameter not present", HttpStatus.BAD_REQUEST);
		}
	}

	private void createBeneficiariesAndUsersAssociation(Beneficiaries beneficiary, Users user) {
		
			List<UsersBeneficiariesCustomSync> userBeneficiariesSync = usersBeneficiariesCustomSyncService
					.findByUserIdAndBeneficiaryId(user.getId(), beneficiary.getId());
			if(userBeneficiariesSync.isEmpty()) {
				UsersBeneficiariesCustomSync userBeneficiary = new UsersBeneficiariesCustomSync();
				userBeneficiary.setUser(user);
				userBeneficiary.setBeneficiary(beneficiary);
				userBeneficiary.setSyncDate(new Date());
				service.Save(userBeneficiary);
			}
	}

	@SuppressWarnings({ "rawtypes" })
	@GetMapping(path = "/userBeneficiariesSync", produces = "application/json")
	public ResponseEntity getUserBeneficiaries(@RequestParam(name = "lastPulledAt", required = false) @Nullable String lastPulledAt,@RequestParam(name = "userId") Integer userId) throws ParseException {

		List<UsersBeneficiariesCustomSync> userBeneficiariesSync = usersBeneficiariesCustomSyncService
				.getUserBeneficiariesSync(userId);

		List<Users> usersCreatedCustomized = new ArrayList<Users>();
		List<Users> usersUpdatedCustomized = new ArrayList<Users>();
		List<Integer> listDeletedCustomized;

		List<Locality> localityCreatedCustomized = new ArrayList<Locality>();
		List<Locality> localityUpdatedCustomized = new ArrayList<Locality>();

		List<Province> provincesCreatedCustomized = new ArrayList<Province>();
		List<District> districtsCreatedCustomized = new ArrayList<District>();

		List<Partners> partnersCreatedCustomized = new ArrayList<Partners>();
		List<Partners> partnersUpdatedCustomized = new ArrayList<Partners>();

		List<Profiles> profilesCreatedCustomized = new ArrayList<Profiles>();
		List<Profiles> profilesUpdatedCustomized = new ArrayList<Profiles>();

		List<Us> usCreatedCustomized = new ArrayList<Us>();
		List<Us> usUpdatedCustomized = new ArrayList<Us>();

		List<Beneficiaries> beneficiariesCreatedCustomized = new ArrayList<Beneficiaries>();
		List<Beneficiaries> beneficiariesUpdatedCustomized = new ArrayList<Beneficiaries>();

		List<BeneficiariesInterventions> beneficiariesInterventionsCreatedCustomized = new ArrayList<BeneficiariesInterventions>();
		List<BeneficiariesInterventions> beneficiariesInterventionsUpdatedCustomized = new ArrayList<BeneficiariesInterventions>();

		List<Neighborhood> neighborhoodsCreatedCustomized = new ArrayList<>();
		List<Neighborhood> neighborhoodUpdatedCustomized = new ArrayList<>();

		List<Services> servicesCreatedCustomized = new ArrayList<>();
		List<Services> servicesUpdatedCustomized = new ArrayList<>();

		List<SubServices> subServicesCreatedCustomized = new ArrayList<>();
		List<SubServices> subServicesUpdatedCustomized = new ArrayList<>();

		List<References> referencesCreatedCustomized = new ArrayList<References>();
		List<References> referencesUpdatedCustomized = new ArrayList<References>();

		List<ReferencesServices> referenceServicesCreatedCustomized = new ArrayList<ReferencesServices>();
		List<ReferencesServices> referenceServicesUpdatedCustomized = new ArrayList<ReferencesServices>();

		usersUpdatedCustomized = new ArrayList<Users>();
		listDeletedCustomized = new ArrayList<Integer>();

		beneficiariesCreatedCustomized = userBeneficiariesSync.stream()
			    .map(UsersBeneficiariesCustomSync::getBeneficiary)
			    .collect(Collectors.toList());
		
		List<Integer> beneficiariesIds = beneficiariesCreatedCustomized.stream().map(Beneficiaries::getId).collect(Collectors.toList());
		List<Integer> neighborhoodsIds = beneficiariesCreatedCustomized.stream()
			    .map(beneficiary -> beneficiary.getNeighborhood().getId())
			    .collect(Collectors.toList());
		List<Integer> partnersIds = beneficiariesCreatedCustomized.stream()
			    .map(beneficiary -> beneficiary.getPartners().getId())
			    .collect(Collectors.toList());
		List<Integer> districtsIds = beneficiariesCreatedCustomized.stream()
			    .map(beneficiary -> beneficiary.getDistrict().getId())
			    .collect(Collectors.toList());
		List<Integer> provincesIds = beneficiariesCreatedCustomized.stream()
			    .map(beneficiary -> beneficiary.getDistrict().getProvince().getId())
			    .collect(Collectors.toList());
		List<Integer> localitiesIds = beneficiariesCreatedCustomized.stream()
			    .map(beneficiary -> beneficiary.getLocality().getId())
			    .collect(Collectors.toList());

		
		if (beneficiariesCreatedCustomized.size() > 0) {

			beneficiariesInterventionsCreatedCustomized = service.GetAllEntityByNamedQuery(
					"BeneficiaryIntervention.findByBeneficiariesIds", beneficiariesIds);

			referencesCreatedCustomized = service.GetAllEntityByNamedQuery("References.findByBeneficiariesIds",
					beneficiariesIds);

			for (References ref : referencesCreatedCustomized) {
				List<ReferencesServices> refServicesByRef = new ArrayList<ReferencesServices>();
				refServicesByRef = service.GetAllEntityByNamedQuery("ReferencesServices.findByReference", ref.getId());
				referenceServicesCreatedCustomized.addAll(refServicesByRef);
			}

			neighborhoodsCreatedCustomized = service.GetAllEntityByNamedQuery("Neighborhood.findByIds",
					neighborhoodsIds);

			partnersCreatedCustomized = service.GetAllEntityByNamedQuery("Partners.findByIds",
					partnersIds);

			districtsCreatedCustomized = service.GetAllEntityByNamedQuery("District.findByIds",
					districtsIds);

			provincesCreatedCustomized = service.GetAllEntityByNamedQuery("Province.findByIds",
					provincesIds);

			localityCreatedCustomized = service.GetAllEntityByNamedQuery("Locality.findByIds",
					localitiesIds);
		}

		try {
			SyncObject<Users> usersSO = new SyncObject<Users>(usersCreatedCustomized, usersUpdatedCustomized, listDeletedCustomized);
			SyncObject<Province> provinceSO = new SyncObject<Province>(provincesCreatedCustomized, new ArrayList<Province>(),
					listDeletedCustomized);
			SyncObject<District> districtSO = new SyncObject<District>(districtsCreatedCustomized, new ArrayList<District>(),
					listDeletedCustomized);
			SyncObject<Locality> localitySO = new SyncObject<Locality>(localityCreatedCustomized, localityUpdatedCustomized, listDeletedCustomized);
			SyncObject<Partners> partnersSO = new SyncObject<Partners>(partnersCreatedCustomized, partnersUpdatedCustomized, listDeletedCustomized);
			SyncObject<Profiles> profilesSO = new SyncObject<Profiles>(profilesCreatedCustomized, profilesUpdatedCustomized, listDeletedCustomized);
			SyncObject<Us> usSO = new SyncObject<Us>(usCreatedCustomized, usUpdatedCustomized, listDeletedCustomized);
			SyncObject<Beneficiaries> beneficiarySO = new SyncObject<Beneficiaries>(beneficiariesCreatedCustomized,
					beneficiariesUpdatedCustomized, listDeletedCustomized);
			SyncObject<BeneficiariesInterventions> beneficiaryInterventionSO = new SyncObject<BeneficiariesInterventions>(
					beneficiariesInterventionsCreatedCustomized, beneficiariesInterventionsUpdatedCustomized, listDeletedCustomized);
			SyncObject<Neighborhood> neighborhoodSO = new SyncObject<Neighborhood>(neighborhoodsCreatedCustomized,
					neighborhoodUpdatedCustomized, listDeletedCustomized);
			SyncObject<Services> serviceSO = new SyncObject<Services>(servicesCreatedCustomized, servicesUpdatedCustomized, listDeletedCustomized);
			SyncObject<SubServices> subServiceSO = new SyncObject<SubServices>(subServicesCreatedCustomized, subServicesUpdatedCustomized,
					listDeletedCustomized);
			SyncObject<References> referencesSO = new SyncObject<References>(referencesCreatedCustomized, referencesUpdatedCustomized,
					listDeletedCustomized);
			SyncObject<ReferencesServices> referencesServicesSO = new SyncObject<ReferencesServices>(
					referenceServicesCreatedCustomized, referenceServicesUpdatedCustomized, listDeletedCustomized);

			String object = SyncSerializer.createSyncObject(usersSO, provinceSO, districtSO, localitySO, profilesSO,
					partnersSO, usSO, beneficiarySO, beneficiaryInterventionSO, neighborhoodSO, serviceSO, subServiceSO,
					referencesSO, referencesServicesSO, lastPulledAt);
			// System.out.println("PULLING " + object);

			return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return new ResponseEntity<>("Parameter not present", HttpStatus.BAD_REQUEST);
		}
	}

}
