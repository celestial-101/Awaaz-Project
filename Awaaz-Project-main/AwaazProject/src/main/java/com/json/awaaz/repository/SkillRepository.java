package com.json.awaaz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.json.awaaz.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    
	Optional<Skill> findByName(String name);
	
	

}
