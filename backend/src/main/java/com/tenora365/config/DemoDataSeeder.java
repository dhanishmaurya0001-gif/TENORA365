package com.tenora365.config;

import com.tenora365.entity.*;
import com.tenora365.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
@Profile("demo")
public class DemoDataSeeder {
    @Bean
    CommandLineRunner seedDemo(SchoolRepository schools, UserRepository users,
            StudentRepository students, TeacherRepository teachers, FeePaymentRepository fees,
            ModuleRecordRepository modules, PasswordEncoder encoder) {
        return args -> {
            if (users.count() > 0) return;
            School first = school(schools, "Pragati Public School - Demo", "PRAGATI", "school@pragati.example");
            School second = school(schools, "Riverbank Academy - Demo", "RIVERBANK", "school@riverbank.example");
            user(users, encoder, null, "superadmin", "Demo Platform Administrator", "SUPER_ADMIN");
            user(users, encoder, first, "admin@demo.tenora365.com", "Pragati Demo Administrator", "SCHOOL_ADMIN");
            user(users, encoder, second, "admin@riverbank.example", "Riverbank Demo Administrator", "SCHOOL_ADMIN");
            String[] names = {"Aarav Sharma", "Aanya Verma", "Vivaan Singh", "Anika Gupta", "Meera Patel"};
            for (int i = 0; i < names.length; i++) {
                Student student = new Student();
                student.setSchool(first); student.setAdmissionNo("PPS26000" + (i + 1));
                student.setFullName(names[i] + " - Demo"); student.setClassName("Class 6"); student.setSection("A");
                student.setRollNo(String.valueOf(i + 1)); student.setParentName("Demo Guardian " + (i + 1));
                student.setParentPhone("0000000000"); student.setDob(LocalDate.of(2015, i + 1, i + 1));
                students.save(student);
                FeePayment fee = new FeePayment(); fee.setSchool(first); fee.setStudentId(student.getId());
                fee.setReceiptNo("DEMO-PPS-" + (i + 1)); fee.setAmount(BigDecimal.valueOf(2500 + i * 100));
                fee.setFeeHead("Tuition - November"); fee.setStatus(i < 3 ? "PAID" : "PENDING");
                fee.setProvider("DEMO_CASH"); fee.setPaidOn(LocalDate.of(2026, 11, i + 1)); fees.save(fee);
            }
            Student isolated = new Student(); isolated.setSchool(second); isolated.setAdmissionNo("RBA260001");
            isolated.setFullName("Ishita Rao - Demo"); isolated.setClassName("Class 7"); isolated.setSection("B");
            isolated.setParentName("Riverbank Demo Guardian"); isolated.setParentPhone("0000000000"); students.save(isolated);
            for (String name : new String[]{"Nisha Sharma", "Rohan Verma"}) {
                Teacher teacher = new Teacher(); teacher.setSchool(first); teacher.setEmployeeNo("DEMO-" + name.substring(0, 3));
                teacher.setFullName(name + " - Demo"); teacher.setSubject("Mathematics"); teacher.setPhone("0000000000");
                teacher.setEmail(name.split(" ")[0].toLowerCase() + "@teacher.example"); teacher.setSalary(BigDecimal.valueOf(35000)); teachers.save(teacher);
            }
            String[][] records = {
                {"ATTENDANCE", "{\"date\":\"2026-11-23\",\"class\":\"Class 6 A\",\"present\":4,\"absent\":1}"},
                {"EXAMS", "{\"name\":\"Half Yearly Assessment - Demo\",\"class\":\"Class 6 A\",\"status\":\"Results prepared\"}"},
                {"TIMETABLE", "{\"day\":\"Monday\",\"period\":1,\"subject\":\"Mathematics\",\"class\":\"Class 6 A\"}"},
                {"LIBRARY", "{\"title\":\"Introduction to Science - Demo\",\"copies\":6,\"available\":4}"},
                {"PAYROLL", "{\"month\":\"November 2026\",\"employee\":\"Nisha Sharma - Demo\",\"status\":\"Draft\"}"},
                {"TRANSPORT", "{\"route\":\"Demo Route A\",\"vehicle\":\"Fictional School Bus\",\"capacity\":30}"},
                {"PARENTS", "{\"name\":\"Demo Guardian\",\"student\":\"Aarav Sharma - Demo\"}"},
                {"STUDENT-PORTAL", "{\"student\":\"Aarav Sharma - Demo\",\"notice\":\"Library reading week\"}"},
                {"NOTIFICATIONS", "{\"title\":\"Demo parent-teacher meeting\",\"delivery\":\"No external message sent\"}"},
                {"DOCUMENTS", "{\"title\":\"Demo student handbook\",\"status\":\"Sample metadata record\"}"}
            };
            for (String[] entry : records) {
                ModuleRecord record = new ModuleRecord(); record.setSchool(first); record.setModule(entry[0]);
                record.setRecordKey("DEMO-" + entry[0]); record.setDataJson(entry[1]); modules.save(record);
            }
        };
    }
    private School school(SchoolRepository repository, String name, String code, String email) {
        School school = new School(); school.setName(name); school.setCode(code); school.setEmail(email);
        return repository.save(school);
    }
    private void user(UserRepository repository, PasswordEncoder encoder, School school,
            String username, String name, String role) {
        User user = new User(); user.setSchool(school); user.setUsername(username); user.setFullName(name);
        user.setRole(role); user.setPasswordHash(encoder.encode("Demo@School2026!")); repository.save(user);
    }
}
