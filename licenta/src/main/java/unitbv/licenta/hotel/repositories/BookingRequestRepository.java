package unitbv.licenta.hotel.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import unitbv.licenta.hotel.models.BookingRequestForm;

@Repository
public interface BookingRequestRepository extends CrudRepository<BookingRequestForm, Long>{
	
	Iterable<BookingRequestForm> getByUserId(long id);

}
