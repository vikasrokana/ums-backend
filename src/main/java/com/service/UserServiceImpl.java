package com.service;

import com.Utility.AppUtils;
import com.model.Faculties;
import com.model.Student;
import com.model.User;
import com.payload.request.UserRequest;
import com.repository.FacultiesRepository;
import com.repository.StudentRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultiesRepository facultiesRepository;
    @Override
    public User registerUser(UserRequest userRequest) {
        User user = new User();

        if (null != userRequest.getFirstName()) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (null != userRequest.getLastName()) {
            user.setLastName(userRequest.getLastName());
        }
        if (null != userRequest.getEmail()) {
            user.setEmail(userRequest.getEmail());
        }
        if (null != userRequest.getUserName()) {
            user.setUserName(userRequest.getUserName());
        }
        if (null != userRequest.getProfilePic()) {
            user.setProfilePic(userRequest.getProfilePic());
        }
        if (null != userRequest.getPassword()) {
            user.setPassword(userRequest.getPassword());
        }
        if (null != userRequest.getRole()) {
            user.setRole(userRequest.getRole());
        }
        user.setCreatedOn(AppUtils.getCurrentIstTime());

        User user1 = userRepository.save(user);

        if ("student".equalsIgnoreCase(userRequest.getRole())) {
            Student student = new Student();
            student.setUserId(user1.getId());  // Assuming there's an ID field
            student.setStudentName(user1.getFirstName()+" " + user1.getLastName());
            student.setEnrollmentNumber(AppUtils.generateEnrollmentNumber(userRequest.getCourseCode()));
            student.setEmail(user1.getEmail());
            student.setCourseId(userRequest.getCourseId());
            studentRepository.save(student);
        } else if ("faculty".equalsIgnoreCase(userRequest.getRole())) {
            Faculties faculty = new Faculties();
            faculty.setUserId(user1.getId());
            faculty.setFacultyName(user1.getFirstName()+" " + user1.getLastName());
            faculty.setEmail(user1.getEmail());
            facultiesRepository.save(faculty);
        }
        return  user1;
    }
}
