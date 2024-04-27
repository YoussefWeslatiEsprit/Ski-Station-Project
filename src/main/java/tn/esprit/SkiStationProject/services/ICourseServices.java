package tn.esprit.SkiStationProject.services;

import tn.esprit.SkiStationProject.entities.Course;

import java.util.List;

public interface ICourseServices {

    List<Course> retrieveAllCourses();

    Course  addCourse(Course  course);

    Course updateCourse(Course course);

    Course retrieveCourse(Long numCourse);


}
