package ttl.demogradle.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import ttl.json.domain.Student;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Path("/demorest")
public class DemoRestService {

	private static Map<Integer, Student> students;
	
	static {
		students = new ConcurrentHashMap<>();
		LocalDate ld = LocalDate.of(1960, 10, 10);
		Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault())
				.toInstant();
		Date bday = Date.from(instant);
		Student stu = new Student("Jane", Student.Status.FULL_TIME,
				new BigDecimal(1000), bday);
		students.put(0, stu);

		stu = new Student("Chung Lee", Student.Status.FULL_TIME,
				new BigDecimal(1000), bday);
		students.put(1, stu);
		
	}

	@GET
	@Path("/{id}")
	public String getStudentPath(@PathParam("id") int id) {
		return getStudent(id);
	}

	@GET
	public String getStudent(@QueryParam("id") Integer id) {
		LocalDate ld = LocalDate.of(1960, 10, 10);
		Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault())
				.toInstant();
		Date bday = Date.from(instant);
		//Here we are going to do something completely
		//different
		Calendar cal = Calendar.getInstance();
		cal.setTime(bday);
		cal.add(Calendar.MONTH, 1);

		Student student = students.get(id);
		if(student == null) {
			return "Not Found";
		}
		return student.toString();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getStudentXml(@QueryParam("id") int id) {
		Student student = students.get(id);

		XMLOutputFactory f = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = null;
		String result = null;
		try {
			StringWriter sw = new StringWriter();
			writer = f.createXMLStreamWriter(sw);

			writeToXml(student, writer);
			
			result = (sw.toString());
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStudentJson(@QueryParam("id") int id) {
		Student student = students.get(id);
		//Create the Mapper
		ObjectMapper mapper = new ObjectMapper();
		//Configure mapper
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-dd-MM");
		mapper.setDateFormat(outputFormat);

		String stJson = null;
		try {
			stJson = studentToJson(student,  mapper);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return stJson;
	}

	public static void writeToXml(Student student, XMLStreamWriter writer)
			throws Exception {
		writer.writeStartDocument();
		writer.writeCharacters("\n");
		writer.writeStartElement("Student");

		writer.writeStartElement("name");
		writer.writeCharacters(student.getName());
		writer.writeEndElement();

		writer.writeStartElement("status");
		writer.writeCharacters(student.getStatus().toString());
		writer.writeEndElement();

		writer.writeStartElement("grantAmount");
		writer.writeCharacters(student.getGrantAmount().toString());
		writer.writeEndElement();

		writer.writeStartElement("birthday");
		writer.writeCharacters(student.getBirthday().toString());
		writer.writeEndElement();

		writer.writeStartElement("phoneNumber");
		writer.writeCharacters(student.getPhoneNumber().toString());
		writer.writeEndElement();

		writer.writeEndElement(); // student
		writer.writeCharacters("\n");

		writer.writeEndDocument();

	}
	
	public String studentToJson(Student student, ObjectMapper mapper)
			throws JsonParseException, JsonMappingException, IOException {
		
		StringWriter sw = new StringWriter();
		//Convert
		mapper.writeValue(sw, student);
		
		return sw.toString();
	}	
	
	public void junkforGitPlay() {
		
	}
}
