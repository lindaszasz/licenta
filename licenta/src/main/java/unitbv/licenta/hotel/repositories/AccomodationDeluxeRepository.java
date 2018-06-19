package unitbv.licenta.hotel.repositories;

import org.springframework.data.repository.CrudRepository;

import unitbv.licenta.hotel.models.AccomodationDeluxe;

public interface AccomodationDeluxeRepository extends CrudRepository<AccomodationDeluxe, Long>{
	
	Iterable<AccomodationDeluxe> getByUserId(long id);

}
