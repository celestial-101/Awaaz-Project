package com.json.awaaz.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.json.awaaz.model.Skill;
import com.json.awaaz.model.SkillUser;
import com.json.awaaz.model.User;
import com.json.awaaz.repository.SkillRepository;
import com.json.awaaz.repository.SkillUserRepository;
import com.json.awaaz.repository.UserRepository;

@Service
public class SkillService {
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SkillUserRepository skillUserRepository;
	/*
	 * This is a method with void return type
	 * Used String Path as parameter.
	 * Function is to save a skill object in skill Repo.
	 * No same name of skill is stored.
	 */
    public void addSkill(String path) throws IOException, ParseException {
    	
    	Skill skill=new Skill();
    	JSONParser jsonParser=new JSONParser();
    	FileReader file=new FileReader(path);
        Object obj= jsonParser.parse(file); 
        JSONObject skillObj=(JSONObject)obj;
        skill.setLevel(Long.parseLong(String.valueOf(skillObj.get("level"))));
    	skill.setName(String.valueOf(skillObj.get("name")));
    	Optional<Skill> sk=findByName(String.valueOf(skillObj.get("name")));
    	if(sk.isPresent()) {
    		System.out.println("Skill "+ skill.getName()+ " is already in the system. Ignoring ");
    	}else {
    		if(!(skill.getLevel()>=1 && skill.getLevel()<=3)) {
	    		 System.out.println("Skill "
	     		 		+ skill.getName()+" Level is " +skill.getLevel()+
	     		 		" , it should be between 1 and 3. Ignoring");
	    	 }else {
	    		 System.out.println("Skill saved successfully .");
	    		 skillRepository.save(skill);
	    		 }
    	}
        
    }
    
    /*
     * This method is used to give skill object on the basis of name
     */
    public Optional<Skill> findByName(String name) {
    return	skillRepository.findByName(name);
    }
    
    /*
     * This method is used to skillUser object by skill name.
     */
    public List<SkillUser> getBySkill(String name){
    	Optional<Skill> skill=findByName(name);
    	return skillUserRepository.getBySkills(skill.get());
    }
    public Optional<User> getByUserId(long id) {
    	return userRepository.findById(id);
    }
    /*
     * This method is used to find list of users by skill name.
     */
    public List<User> getUserBySkillName(String name) {
    	List<SkillUser> sku=getBySkill(name);
    	List<User> u=new ArrayList<>();
    	for(SkillUser s:sku) {
    		Optional<User> user=getByUserId(s.getUsers().getId());
    		u.add(user.get());
    	}
    	return u;
    }
    
    /*
	 * This is a method with void return type
	 * Used String Path as parameter.
	 * Function is to save a User object in User Repo.
	 * No same name of skill is stored.
	 */
    @Transactional
    public void addUser(String path) throws IOException, ParseException {
        User user=new User();
    	JSONParser jsonParser=new JSONParser();
    	FileReader file=new FileReader(path);
        Object obj= jsonParser.parse(file); 
        JSONObject jobj=(JSONObject)obj;
        user.setFirstName(String.valueOf(jobj.get("first_name")));
        user.setLastName(String.valueOf(jobj.get("last_name")));
        user.setAge(String.valueOf(jobj.get("age")));
        userRepository.save(user);
        System.out.println(user.getFirstName() + " is saved successfuly .");
        JSONArray arr=(JSONArray) jobj.get("skills");
        if(arr.size()==0) {
        	System.out.println("Unable to locate skill id . Ignoring");
        }else {
        for(int i=0;i<arr.size();i++) {
        	Skill skill=new Skill();
        	SkillUser skillUser=new SkillUser();
        	JSONObject skillObj=(JSONObject) arr.get(i); 
        	skill.setName(String.valueOf(skillObj.get("name")));
        	skill.setLevel(Long.parseLong(String.valueOf(skillObj.get("level"))));
        	Optional<Skill> sk=findByName(String.valueOf(skillObj.get("name")));
        	if(sk.isPresent()) {
        		skillUser.setSkills(sk.get());
        		skillUser.setUsers(user);
        		skillUserRepository.save(skillUser);
        		System.out.println("Skill "+ skill.getName()+ " is already in the system. Ignoring ");
        	}else {
        		if(!(skill.getLevel()>=1 && skill.getLevel()<=3)) {
   	    		 System.out.println("Skill "
   	     		 		+ skill.getName()+" Level is " +skill.getLevel()+
   	     		 		" , it should be between 1 and 3. Ignoring");
   	    	 }else {
   	    		 System.out.println("Skill " + skill.getName()+ " saved successfully .");
   	    		 skillUser.setUsers(user);
   	    		 skillUser.setSkills(skill);
   	    		 skillRepository.save(skill);
   	    		 skillUserRepository.save(skillUser);
   	    		 
   	    		 }
        	  }
           }
        }
    }
  
    /*
     * This method is used to print names of all skill object .
     */
    public List<Skill> getAllSkills(){
    	return skillRepository.findAll();
    }
}
