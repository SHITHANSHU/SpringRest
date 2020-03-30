package com.hitachi.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.demo.model.Student;
import com.hitachi.demo.repository.StudentRepository;
import com.hitachi.demo.controller.ResourceNotFoundException;



@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

		@Autowired
		private StudentRepository studentRepository;
		
		@GetMapping("getall")
		public List<Student> getAllStudent()
		{
			return this.studentRepository.findAll();
		}
		
		@GetMapping("/get/{id}")
		public ResponseEntity<Student> getstudnetById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException
		{
			Student stud=studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student not found with id : "+id));
			return ResponseEntity.ok().body(stud);
			
		}
		
		@PostMapping("insertStudent")
		public Student createStudent(@RequestBody Student student)
		{
			return this.studentRepository.save(student);
		}
		
		@PutMapping("update/{id}")
		public ResponseEntity<Student> updateStudent(@PathVariable("id") Integer id, @RequestBody Student studnetdetail) throws ResourceNotFoundException
		{
			Student Student= studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Data to be updated not found"));
			Student.setId(studnetdetail.getId());
			Student.setClas(studnetdetail.getClas());
			Student.setMarks(studnetdetail.getMarks());
			Student.setName(studnetdetail.getName());
			
			return ResponseEntity.ok(this.studentRepository.save(Student));
			
		}
		
		@DeleteMapping("del/{id}")
		public Map<String, Boolean> deleteStudent(@PathVariable("id") Integer id) throws ResourceNotFoundException
		{
			Student Student= studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data to be deleted not found id : "+id));
			
			this.studentRepository.delete(Student);
			Map<String,Boolean> resp=new HashMap<String, Boolean>();
			resp.put("deleted", Boolean.TRUE);

			return resp;
		}
		
}

