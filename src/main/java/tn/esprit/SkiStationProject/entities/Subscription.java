package tn.esprit.SkiStationProject.entities;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import tn.esprit.SkiStationProject.entities.enums.TypeSubscription;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
public class Subscription extends BaseEntity {

	LocalDate startDate;
	LocalDate endDate;
	Float price;
    @Enumerated(EnumType.STRING)
	TypeSubscription typeSub;

}
