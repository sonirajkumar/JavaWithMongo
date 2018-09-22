package testdb;

import java.util.Scanner;
import org.bson.Document;

import com.mongodb.BasicDBObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MenuDrivenMongo {
public static void main(String[] args) {
	System.out.println("Enter database name:");
	
	Scanner sc= new Scanner(System.in);
	String dbname=sc.next();
	
	MongoClient mn= new MongoClient("localhost",27017);
	
	System.out.println("Connected");
	System.out.println("Collections present in Entered Database are:");
	MongoDatabase database = mn.getDatabase(dbname); 
	MongoIterable<String> str=database.listCollectionNames();
	System.out.println("Collection present in "+dbname+" are :");
	for (String s : str) {
	System.out.println("* " +s);
	}
	System.out.println("please Enter a collection Name");
	String collname= sc.next();
	
	 MongoCollection<Document> coll= database.getCollection(collname);
	 System.out.println(collname+ " selected successfully !");
	 int option;
	 do { 
	 System.out.println("Select operation :\n1. Insert Document \n2. Update Document \n3. Delete Document \n4. Show All Documents of Collection \n5.exit");
	 option=sc.nextInt();
	 
	
	 if(option==1) {
		 char st;
		 Document doc =new Document();
		 do {
		 System.out.println("Enter Key followed by value :");
		 String key=sc.next();
		 String value=sc.next();
		 doc.append(key,value);
		 
		 System.out.println("Want to enter more? Y/N");
		 st=sc.next().charAt(0);
		 } while( st=='Y'||st=='y');
		 coll.insertOne(doc);
	 }
	 else if(option==2) {
		 System.out.println("Enter Key followed by value to identify document :");
		 String key=sc.next();
		 String value=sc.next();
		 System.out.println("enter new key and value");
		 BasicDBObject query=new BasicDBObject(key,value);
		 BasicDBObject update=new BasicDBObject();
		 update.put("$set", new BasicDBObject(sc.next(),sc.next()));
		 coll.updateOne(query, update);
		 System.out.println("Update");
	 }
	 else if(option==3) {
		 System.out.println("Enter Key followed by value to Delete document :");
		 String key=sc.next();
		 String value=sc.next();
		 BasicDBObject query=new BasicDBObject(key,value);
		 coll.deleteOne(query);
		 System.out.println("Deleted !");
	 }
	 else if(option==4) {
		 MongoIterable<Document> doc=coll.find();
		 for(Document d: doc) {
			 System.out.println(d);
		 }
	 }
	 else if(option==5)
	 { break;}
	}while(option>=1||option<=3);
}
}