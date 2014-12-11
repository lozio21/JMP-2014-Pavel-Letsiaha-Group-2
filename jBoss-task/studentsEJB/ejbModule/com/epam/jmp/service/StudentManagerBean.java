package com.epam.jmp.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.epam.jmp.entity.Group;
import com.epam.jmp.entity.Student;

@Stateless
public class StudentManagerBean implements StudentManager {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentList() {
		Query query = em.createQuery("from Student");
		List<Student> students = query.getResultList();
		return students;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentsByGroup(Group group) {
		Query query = em.createQuery("from Student where studentGroup = ?")
				.setParameter(1, group);
		List<Student> students = query.getResultList();
		return students;
	}

	@Override
	public Student getStudentById(Long id) {
		return em.find(Student.class, id);
	}
}