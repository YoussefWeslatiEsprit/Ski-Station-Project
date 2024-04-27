package tn.esprit.SkiStationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.SkiStationProject.entities.*;
import tn.esprit.SkiStationProject.entities.enums.Color;
import tn.esprit.SkiStationProject.entities.enums.Support;
import tn.esprit.SkiStationProject.entities.enums.TypeSubscription;
import tn.esprit.SkiStationProject.entities.enums.TypeCourse;

import java.util.List;

public interface SkierRepository extends JpaRepository<Skier,Long> {

    List<Skier> findBySubscription_TypeSub(TypeSubscription typeSubscription); //Keyword

    Skier findBySubscription(Subscription subscription);

    @Query("select s from Skier s where s.subscription.typeSub = ?1 and s.city=?2") //JPQL
    List<Skier> findBySubscription_TypeSubAndCityJPQL(TypeSubscription typeSubscription, String city); //Keyword

    @Query("select s from Skier s where s.subscription.typeSub = :typeSubscription and s.city= :city") //JPQL
    List<Skier> findBySubscription_TypeSubAndCityJPQL2(@Param("typeSubscription") TypeSubscription typeSubscription, @Param("city") String city); //Key word


     @Query("select s from Skier s " +
             "join Registration i on i.skier=s " +
             "join Instructor ins on i.course member of  ins.courses where ins.firstName = :firstName and i.course.support = :support"
             ) //JPQL
     List<Skier> findSkierbyInstructorFirstNameAndSupportTypeJPQL(@Param("support") Support support, @Param("firstName") String firstName);






}
