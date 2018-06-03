package unitbv.licenta.hotel.repositories;

import org.springframework.data.repository.CrudRepository;

import unitbv.licenta.hotel.models.Accomodation;

public interface AccomodationRepository extends CrudRepository<Accomodation, Long> {
	
	Iterable<Accomodation> getByUser(long id);
	

}
