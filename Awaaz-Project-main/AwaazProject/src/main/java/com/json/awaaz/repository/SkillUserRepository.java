package com.json.awaaz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.json.awaaz.model.Skill;
import com.json.awaaz.model.SkillUser;

public interface SkillUserRepository extends JpaRepository<SkillUser, Long>{
	
	List<SkillUser> getBySkills(Skill skill);
    
	

}
