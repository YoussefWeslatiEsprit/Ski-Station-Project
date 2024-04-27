package tn.esprit.SkiStationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.SkiStationProject.entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor,Long> {


}
