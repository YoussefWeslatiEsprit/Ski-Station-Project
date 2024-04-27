package tn.esprit.SkiStationProject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.repositories.CourseRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CourseServicesImpl implements  ICourseServices{

    private final CourseRepository courseRepository;

    @Override
    public List<Course> retrieveAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course retrieveCourse(Long numCourse) {
        return courseRepository.findById(numCourse)
                .orElseThrow(() -> new IllegalArgumentException("no course found with this id " + numCourse));
    }


}
