package ehb.demo.repository;

import ehb.demo.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findByUserEmailOrderByReservatieDatumDesc(String email);

}