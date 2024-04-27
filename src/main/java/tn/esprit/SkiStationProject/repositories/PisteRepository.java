package tn.esprit.SkiStationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.SkiStationProject.entities.enums.Color;
import tn.esprit.SkiStationProject.entities.Piste;

import java.util.List;

public interface PisteRepository extends JpaRepository<Piste,Long> {


    public List<Piste> findByColor(Color color);
}
