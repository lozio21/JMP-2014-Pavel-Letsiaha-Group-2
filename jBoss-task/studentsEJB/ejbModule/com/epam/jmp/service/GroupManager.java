package com.epam.jmp.service;

import java.util.List;

import javax.ejb.Local;

import com.epam.jmp.entity.Group;

@Local
public interface GroupManager {
	
	List<Group> getGroupList();

	Group getGroupById(Long id);
}