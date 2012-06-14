package practice.mvc.dbservice;

import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import practice.mvc.domain.Student;

@Service("studentService")
@Transactional
public class StudentService {
	protected static Logger logger = Logger.getLogger("service");

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Retrieves all students
	 * 
	 * @return a list of students
	 */
	public List<Student> getAll() {
		logger.debug("Retrieving all students");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Student");

		// Retrieve all
		return query.list();
	}

	/**
	 * Retrieves a single student
	 */
	public Student get(Integer id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing person first
		Student student = (Student) session.get(Student.class, id);

		return student;
	}

	/**
	 * Adds a new person
	 */
	public void add(Student student) {
		logger.debug("Adding new person");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Save
		session.save(student);
	}

	/**
	 * Deletes an existing person
	 * 
	 * @param id
	 *            the id of the existing person
	 */
	public void delete(Integer id) {
		logger.debug("Deleting existing student");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing person first
		Student student = (Student) session.get(Student.class, id);

		// Delete 
		session.delete(student);
	}

	/**
	 * Edits an existing person
	 */
	public void edit(Student student) {
		logger.debug("Editing existing student");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing person via id
		Student existingStudent = (Student) session.get(Student.class, student.getId());

		// Assign updated values to this student
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setPresent(student.getPresent());
		
		// Save updates
		session.save(existingStudent);
	}
}