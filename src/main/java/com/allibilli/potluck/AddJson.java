package com.allibilli.potluck;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AddJson {
	
	JSONArray persons=null;
	JSONObject  potLuck = null;
	FileReader reader = null;
	List<String> emails = new ArrayList<String>();
	List<String> foodItemsList = new ArrayList<String>();
	
	public JSONObject parseFile(String filePath) throws Exception {
		reader = new FileReader(filePath);
		JSONParser jsonParser = new JSONParser();
		return (JSONObject) jsonParser.parse(reader);
		
	}
	
	public void read(String filePath) throws Exception {
		potLuck = parseFile(filePath);
		persons = (JSONArray)potLuck.get("children");
	}

	public void printPersons(String filePath) throws Exception {
		
		read(filePath);
		
		for (Object object : persons.toArray()) {
			JSONObject person = (JSONObject) object;
			
			String tname = (String) person.get("name");
			System.out.println("The name is: " + tname);

			// get a number from the JSON object
			String temail = (String) person.get("email");
			System.out.println("The email is: " + temail);
			emails.add(temail);
			// get an array from the JSON object
			JSONArray foodItems = (JSONArray) person.get("children");
			for (Object obj : foodItems.toArray()) {
				JSONObject item = (JSONObject) obj;
				System.out.println("Item of the food: " + item.get("name"));
				foodItemsList.add((String)item.get("name"));
			}
		}
	}
	
	
	public void add(String name, String email, String[] items, String filePath, String appURL) throws Exception {
		
		printPersons(filePath);
		
		try {
		
				JSONObject newGuy = new JSONObject();
				newGuy.put("name", name);
				newGuy.put("email", email);
				emails.add(email);
				JSONArray foodItems = new JSONArray();
				
				for(String itemName : items)
				{
					JSONObject item = new JSONObject();
					item.put("name", itemName);
					item.put("size", "17010");
					foodItems.add(item);
				}

				
				newGuy.put("children", foodItems);
				System.out.println(newGuy.toJSONString());
				persons.add(newGuy);
				reader.close();
			
				sendEmails(appURL);
				
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} finally{
		
		}

		try {

			FileWriter file = new FileWriter(filePath);
			System.out.println(potLuck.toJSONString());
			file.write(potLuck.toJSONString());
			file.flush();
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendEmails(String appURL){
		final String username = "alertsoalerts@gmail.com";
		final String password = "gopikrishna";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("haigopi@gmail.com"));
			for(String email: emails) {
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(email));	
			}
			
			message.setSubject("Potluck 2018 - Updated News");
			StringBuilder foodNames = new StringBuilder();
			for(String foodName: foodItemsList) {
				foodNames.append(" -- ").append(foodName).append("<br>");
			}
			
			message.setContent("Following items were chosen to bring to potluck"
					+ "<br>" + foodNames.toString() + "<BR> <a href=\""+appURL+"\">Visit Here to know more details</a>", "text/html; charset=utf-8");
			
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
