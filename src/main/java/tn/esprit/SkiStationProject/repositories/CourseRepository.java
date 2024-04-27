package tn.esprit.SkiStationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.enums.Support;
import tn.esprit.SkiStationProject.entities.enums.TypeCourse;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {

    @Query(value = "SELECT * " +
            "FROM course c " +
            "ORDER BY (SELECT COUNT * FROM registration i WHERE i.course_id = c.id)",nativeQuery = true)
    List<Course> getAllOrderByInscriptionsNumberSQL();

    @Query(value = "SELECT c FROM Course c ORDER BY c.registrations.size")
    List<Course> getAllOrderByInscriptionsNumberJPQL();


    @Query(value= "SELECT c FROM Course c WHERE c.typeCourse=?1 AND c.support=?2 " )
    List<Course> getcourbysupportskiettypecourcollectif_enfant(Support s , TypeCourse t);
}
