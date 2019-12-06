package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid
                                          @ModelAttribute("user") User user, BindingResult result,
                                          Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors())
        {
            return "registration";
        }
        else
        {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "index";
    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        return "index";
    }
    @RequestMapping("/viewAll")
    public String viewAll(Model model){
        model.addAttribute("students", studentRepository.findAll());
        return "viewAll";
    }
    @RequestMapping("/addlink")
    public String addlink(Model model){
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        return "studentcourseform";
    }
    @PostMapping("/processlink")
    public String processlink(@RequestParam("courseId") long cid, @RequestParam("studentId") long sid){
        Course course = courseRepository.findById(cid).get();
        Student student = studentRepository.findById(sid).get();
        Set<Student> students;
        Set<Course> courses;
        if(course.getStudents().size() > 0) {
            students = new HashSet<>(course.getStudents());
        }
        else{
            students = new HashSet<>();
        }
        if(student.getCourses().size() > 0) {
            courses = new HashSet<>(student.getCourses());
        }
        else {
            courses = new HashSet<>();
        }
        students.add(student);
        courses.add(course);
        course.setStudents(students);
        courseRepository.save(course);
        student.setCourses(courses);
        studentRepository.save(student);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable("id") long id, Model model){
        model.addAttribute("student",studentRepository.findById(id).get());
        return "studentform";
    }

    @RequestMapping("/updateCourse/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("course", courseRepository.findById(id).get());
        return "courseform";
    }

    @RequestMapping("/deleteCourse/{id}")
    public String delCourse(@PathVariable("id") long id){
        courseRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/deleteStudent/{id}")
    public String delStudent(@PathVariable("id") long id){
        studentRepository.deleteById(id);
        return "redirect:/";
    }
}
