package unitbv.licenta.hotel.repositories;

import org.springframework.data.repository.CrudRepository;

import unitbv.licenta.hotel.models.AccomodationPrestige;

public interface AccomodationPrestigeRepository extends CrudRepository<AccomodationPrestige, Long>{
	
	Iterable<AccomodationPrestige> getByUserId(long id);

}
