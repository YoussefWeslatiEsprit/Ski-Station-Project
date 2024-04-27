package tn.esprit.SkiStationProject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tn.esprit.SkiStationProject.entities.*;
import tn.esprit.SkiStationProject.entities.enums.Support;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.repositories.RegistrationRepository;
import tn.esprit.SkiStationProject.repositories.SkierRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationServicesImpl implements  IRegistrationServices{

    private final RegistrationRepository registrationRepository;
    private final SkierRepository skierRepository;
    private final CourseRepository courseRepository;

    private final InstructorRepository instructorRepository;


    @Override
    public Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier) {
        Skier skier = skierRepository.findById(numSkier).orElseThrow(() -> new IllegalArgumentException("no skier found with this id " + numSkier));
        registration.setSkier(skier);
         registrationRepository.save(registration);
         return registration;
    }

    @Override
    public Registration assignRegistrationToCourse(Long numRegistration, Long numCourse) {
        Registration registration = registrationRepository.findById(numRegistration).orElseThrow(() -> new IllegalArgumentException("no registration found with this id " + numRegistration));
        Course course = courseRepository.findById(numCourse).orElseThrow(() -> new IllegalArgumentException("no course found with this id " + numCourse));
        registration.setCourse(course);
        return registrationRepository.save(registration);
    }

    @Transactional
    @Override
    public Registration addRegistrationAndAssignToSkierAndCourse(Registration registration, Long numSkieur, Long numCours) {

        Skier skier = skierRepository.findById(numSkieur).orElse(null);
        Course course = courseRepository.findById(numCours).orElse(null);

        Assert.notNull(skier,"No Skieur found with this id : "+numSkieur);
        Assert.notNull(course,"No Skieur found with this id : "+numSkieur);


        if(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(registration.getNumWeek(), skier.getId(), course.getId()) >=1){
            log.info("Sorry, you're already register to this course of the week :" + registration.getNumWeek());
            return null;
        }

        int ageSkieur = Period.between(skier.getDateOfBirth(), LocalDate.now()).getYears();
        log.info("Age " + ageSkieur);

        switch (course.getTypeCourse()) {
            case INDIVIDUAL:
                log.info("add without tests");
                return assignRegistration(registration, skier, course);

            case COLLECTIVE_CHILDREN:
                if (ageSkieur < 16) {
                    log.info("Ok CHILD !");
                    if (registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek()) < 6) {

                        log.info("Course successfully added !");
                        return assignRegistration(registration, skier, course);
                    } else {
                        log.info("Full Course ! Please choose another week to register !");
                        return null;
                    }
                }
                else{
                    log.info("Sorry, your age doesn't allow you to register for this course ! \n Try to Register to a Collective Adult Course...");
                }
                break;

            default:
                if (ageSkieur >= 16) {
                    log.info("Ok ADULT !");
                    if (registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek()) < 6) {
                        log.info("Course successfully added !");
                        return assignRegistration(registration, skier, course);
                    } else {
                        log.info("Full Course ! Please choose another week to register !");
                        return null;
                    }
                }
                log.info("Sorry, your age doesn't allow you to register for this course ! \n Try to Register to a Collective Child Course...");
        }
        return registration;

    }
    private Registration assignRegistration (Registration registration, Skier skier, Course course){
        registration.setSkier(skier);
        registration.setCourse(course);
        return registrationRepository.save(registration);
    }

    @Override
    public List<Integer> numWeeksCourseOfInstructorBySupport(Long numInstructor, Support support) {

        Map<Course, List<Integer>> map = instructorRepository.findById(numInstructor)
                .orElseThrow(() -> new IllegalArgumentException("No Instructor Found with this id " + numInstructor))
                .getCourses().stream()
                .filter(course -> course.getSupport().equals(support))
                .map(Course::getRegistrations)
                .flatMap(Collection::stream)
               // .map(Registration::getNumWeek)
                .collect(Collectors.groupingBy(
                        Registration::getCourse,
                        Collectors.mapping(Registration::getNumWeek, Collectors.toList())
                ));

        return registrationRepository.numWeeksCourseOfInstructorBySupport(numInstructor, support);
    }

}
