package com.epam.jmp.service;

import java.util.List;

import javax.ejb.Local;

import com.epam.jmp.entity.Group;
import com.epam.jmp.entity.Student;

@Local
public interface StudentManager {
	
	List<Student> getStudentList();

	List<Student> getStudentsByGroup(Group group);

	Student getStudentById(Long id);
}