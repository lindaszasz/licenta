package unitbv.licenta.hotel.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import unitbv.licenta.hotel.models.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

}
