package practice.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import practice.mvc.dbservice.StudentService;
import practice.mvc.domain.Student;

@Controller
@RequestMapping("/main")
public class MVCController {

	private List<Student> studentList;

	protected static Logger logger = Logger.getLogger("controller");
	protected static boolean CREATE_DEFAULT_STUDENTS = true;

	@Resource(name = "studentService")
	private StudentService studentService;

	public MVCController() {
		
	
	
	}

	/**
	 * Handles and retrieves all students and show it in a JSP page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public String getStudents(Model model) {

		logger.debug("Received request to show all students");
		
		// Retrieve all students by delegating the call to studentService
		List<Student> students = studentService.getAll();

		// Attach persons to the Model
		model.addAttribute("students", students);

		// This will resolve to /WEB-INF/jsp/studentlistpage.jsp
		return "welcome";
	}

	/**
	 * Retrieves the add page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/students/add", method = RequestMethod.GET)
	public String getAdd(Model model) {
		logger.debug("Received request to show add page");

		// Create new Person and add to model
		// This is the formBackingOBject
		model.addAttribute("studentAttribute", new Student());

		// This will resolve to /WEB-INF/jsp/addpage.jsp
		return "addstudent";
	}

	/**
	 * Adds a new person by delegating the processing to studentService.
	 * Displays a confirmation JSP page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/students/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("studentAttribute") Student student) {
		logger.debug("Received request to add new person");

		// The "personAttribute" model has been passed to the controller from the JSP
		// We use the name "personAttribute" because the JSP uses that name

		// Call studentService to do the actual adding
		studentService.add(student);

		// This will resolve to /WEB-INF/jsp/addedpage.jsp
		return "addedstudent";
	}

	/**
	 * Deletes an existing person by delegating the processing to
	 * studentService. Displays a confirmation JSP page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/students/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "id", required = true) Integer id, Model model) {

		logger.debug("Received request to delete existing student");

		// Call studentService to do the actual deleting
		studentService.delete(id);

		// Add id reference to Model
		model.addAttribute("id", id);

		// This will resolve to /WEB-INF/jsp/deletedpage.jsp
		return "deletedStudent";
	}

	/**
	 * Retrieves the edit page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/students/edit", method = RequestMethod.GET)
	public String getEdit(@RequestParam(value = "id", required = true) Integer id, Model model) {
		logger.debug("Received request to show edit page");

		// Retrieve existing Person and add to model
		// This is the formBackingOBject
		model.addAttribute("studentAttribute", studentService.get(id));

		// This will resolve to /WEB-INF/jsp/editpage.jsp
		return "editstudent";
	}

	/**
	 * Edits an existing person by delegating the processing to studentService.
	 * Displays a confirmation JSP page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/students/edit", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("studentAttribute") Student student, @RequestParam(value = "id", required = true) Integer id, Model model) {
		logger.debug("Received request to update student");

		// The "personAttribute" model has been passed to the controller from the JSP
		// We use the name "personAttribute" because the JSP uses that name

		// We manually assign the id because we disabled it in the JSP page
		// When a field is disabled it will not be included in the ModelAttribute
		student.setId(id);

		// Delegate to studentService for editing
		studentService.edit(student);

		// Add id reference to Model
		model.addAttribute("id", id);

		// This will resolve to /WEB-INF/jsp/editedpage.jsp
		return "editedstudent";
	}

}