package com.epam.jmp.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.epam.jmp.entity.Group;

@Stateless
public class GroupManagerBean implements GroupManager {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getGroupList() {
		List<Group> groups = em.createQuery("from Group").getResultList();
		return groups;
	}

	@Override
	public Group getGroupById(Long id) {
		return em.find(Group.class, id);
	}
}