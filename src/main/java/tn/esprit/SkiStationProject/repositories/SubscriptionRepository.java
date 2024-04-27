package tn.esprit.SkiStationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.SkiStationProject.entities.enums.TypeSubscription;
import tn.esprit.SkiStationProject.entities.Subscription;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    List<Subscription> findByTypeSub(TypeSubscription typeSubscription);
    List<Subscription> findByStartDateAfterAndEndDateBefore(LocalDate startdate, LocalDate enddate);
    @Query("select s from Subscription s where s.typeSub = :typeS order by s.startDate")
    Set<Subscription> findByTypeSubOrderByStartDateAsc(@Param("typeS") TypeSubscription typeSub);

    List<Subscription> getSubscriptionsByStartDateBetween(LocalDate date1, LocalDate date2);

    @Query("select distinct s from Subscription s where s.endDate <= CURRENT_TIME order by s.endDate")
    List<Subscription> findDistinctOrderByEndDateAsc();


    @Query("select (sum(s.price))/(count(s)) from Subscription s where s.typeSub = ?1")
    Float recurringRevenueByTypeSubEquals(TypeSubscription typeSub);
}
