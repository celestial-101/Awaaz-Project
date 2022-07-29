package com.json.awaaz.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.json.awaaz.model.Skill;
import com.json.awaaz.model.User;
import com.json.awaaz.service.SkillService;


public class SkillController {
	
	@Autowired
	private SkillService skillService;
	
	/*
	 * This method is used to select a choice from console first page.
	 */
	public void outputAtConsole(){
    boolean isContinue = true;

    do {
        paintMenu();

        switch (processInput()) {

            case 1:// add skill
                addSkill();
                break;
            case 2:
                addUsers();
                break;
            case 3:
            	getUser();
                break;
            case 4:
            	printAllSkill();
                break;
            case 5:// exit
                System.out.println("Good bye");
                isContinue = false;
                break;
            default:
                System.out.println("Invalid input. Please try again.");
                break;
        }

    } while (isContinue);
	}
  /*
   * This method is used to print choices on the console.
   */
  public static void paintMenu() {
    System.out.println("=========================");
    System.out.println("1. Add a Skill by reading a JSON file");
    System.out.println("2. Add a User by reading a json file");
    System.out.println("3. Get all Users by skill name");
    System.out.println("4. Print all skill name");
    System.out.println("5. Exit");
  }
 
  /*
   * This method is used to return integer for processing the choice from console
   */
  public static int processInput() {

    Scanner input = new Scanner(System.in);
    return input.nextInt();
  }
  
  /*
   * This method is similar as of addSkill from skill service class.
   */
  public void addSkill() {

    String defaultSkillsFolder = "src/main/resources/skills/";
    String filePath = "";
    System.out.println("Default skills folder -> [" + defaultSkillsFolder + " ]");
    System.out.println("1. Use default folder");
    System.out.println("2. Specify full path");

    Scanner userScanner = new Scanner(System.in);

    if (userScanner.nextInt() == 2) {
        System.out.println("Enter full path including filename & extention:");
        filePath = userScanner.next();
    } else {
        System.out.println("Enter filename with extention ( eq. english.json) :");
        filePath = defaultSkillsFolder + userScanner.next();
    }

    try {
        skillService.addSkill(filePath);
    } catch (IOException | ParseException ex) {
        Logger.getLogger(SkillController.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("There was an error while processing");
    }
  }
  /*
   * This method is similar as of addUsers from skill service class.
   */
  public void addUsers() {
	    String defaultUsersFolder = "src/main/resources/users/";
	    String filePath = "";
	    System.out.println("Default users folder -> [" + defaultUsersFolder + " ]");
	    System.out.println("1. Use default folder");
	    System.out.println("2. Specify full path");

	    Scanner userScanner = new Scanner(System.in);

	    if (userScanner.nextInt() == 2) {
	        System.out.println("Enter full path including filename & extention:");
	        filePath = userScanner.next();
	    } else {
	        System.out.println("Enter filename with extention ( eq. putin.json) :");
	        filePath = defaultUsersFolder + userScanner.next();
	    }

	    try {
	        skillService.addUser(filePath);
	    } catch (IOException | ParseException ex) {
	        Logger.getLogger(SkillController.class.getName()).log(Level.SEVERE, null, ex);
	        System.out.println("There was an error while processing");
	    }
  }
  /*
   * This method is used to take input form console as name and return 
   * corresponding users related .
   */
   public void getUser() {
	   System.out.println("1. Select 1 to type skill name : ");
	   Scanner userScanner = new Scanner(System.in);
	   if (userScanner.nextInt()==1) {
		   System.out.println("Type skill name : ");
	   }
	   System.out.println("Warning : Skill name is case sensitive");
		   String name=userScanner.next();
		   
	   List<User> users=skillService.getUserBySkillName(name);
	   if(!users.isEmpty()) {
	   for(User u:users) {
		   System.out.print("Id : " +u.getId()+" : "+u.getFirstName()+" ");
		   System.out.println(u.getLastName());
		   }
	   }else {
		   System.out.println("No users found ");
	   }
	   }
   
   /*
    * This method is used to print names of all Skills present in skill repo.
    */
   public void printAllSkill() {
       Scanner input = new Scanner(System.in);
       List<Skill> skills=skillService.getAllSkills();
       if(skills.size()==0) {
    	   System.out.println("No skills is present in database .");
       }
       for(Skill s:skills) {
    	   System.out.println(s.getName());
       }
   }
   
}
