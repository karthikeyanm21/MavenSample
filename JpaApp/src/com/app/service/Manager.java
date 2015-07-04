package com.app.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Criteria;

import com.app.entity.Employee;
@Path("/get")
public class Manager {
	
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "JPA" );
	      
	      EntityManager entitymanager = emfactory.createEntityManager( );
	      @POST
	      @Path("save")
	      public Response saveEmployee(@FormParam("Eid")int id,@FormParam("Name")String name,@FormParam("salary")
	      double sal,@FormParam("Design")String des)throws SQLException{
	        	  
	    	  entitymanager.getTransaction( ).begin( );
	     try{
	    	 

	      Employee employee = new Employee( ); 
	      employee.setEid( id);
	      employee.setEname( name );
	      employee.setSalary( sal );
	      employee.setDeg( des );
	      entitymanager.persist( employee );
	      String str="successfully saved";
	      entitymanager.getTransaction( ).commit( );
	      return Response.status(200).entity(str).build();
	    	  }catch(NullPointerException e){
	    		  String str="missing fields";
	    		  return Response.status(400).entity(str).build(); }
	    	  /*catch(SQLExceptio se){
	    		  return Response.status(400).entity("Duplicate entries").build();
	    	  }*/
	    		  
	    		  finally{
	    	  
	    		  entitymanager.close( );
	    	      System.out.println("done");
	    	      emfactory.close( );
	    	  }
	      }


	      //saving with ckecking of NULL values
	      @POST
	      @Path("/saverec")
	      public Response saveRecord(@FormParam("Eid")int id,@FormParam("Name")String name,@FormParam("salary")
	      double sal,@FormParam("Design")String des){
	    	  entitymanager.getTransaction( ).begin( );
	    	  String str="successfully saved";
	    	  
	    	  if(id!=0&&(name.isEmpty()==false)&&sal!=0&&des.isEmpty()==false){
	    		  Employee employee = new Employee( ); 
	    	      employee.setEid( id);
	    	      employee.setEname( name );
	    	      employee.setSalary( sal );
	    	      employee.setDeg( des );
	    	      entitymanager.persist( employee );
	    	      entitymanager.getTransaction( ).commit( );
	    	      entitymanager.close( );
	    	      System.out.println("done");
	    	      emfactory.close( );
	    	      return Response.status(200).entity(str).build();
	    	        		  
	    	  }
	    	  else 	  
	    	  
	    		  return Response.status(400).entity("Missing fields").build(); 
	    	  }
	    	  
	      //updating a row in a table
	      @POST
	      @Path("/update")
	      public Response updateEmployee(@FormParam("Eid")int id,@FormParam("Name")String name,@FormParam("salary")
	      double sal,@FormParam("Design")String des){
	    	  
	    	  entitymanager.getTransaction( ).begin( );
	    	  try{
	    		  Employee emp = entitymanager.find( Employee.class, id );
	    		  emp.setEname(name);
	    		  emp.setSalary(sal);
	    		  emp.setDeg(des);
	    		  entitymanager.getTransaction( ).commit( );
	    		  String str="updated Successfuly";
	    		  return Response.status(200).entity(str).build(); 
	    		 }catch(Exception E){
	    		  String str="missing fields";
	    		  return Response.status(400).entity(str).build();
	    		  
	    	  }
	    	  finally{
	    		  entitymanager.close( );
	    	      System.out.println("done");
	    	      emfactory.close( );
	    	  }
	      }
	      @SuppressWarnings("unchecked")
	      @POST
	      @Path("/find")
	      @Produces(MediaType.APPLICATION_JSON)
	      public List<Employee> getEmployeeDetail(@FormParam("Name")int name){
	    	  //entitymanager.getTransaction( ).begin( );
	    	 // Employee employee = entitymanager.find( Employee.class, id );
	    	  Query query=entitymanager.createQuery("select e.eid,e.ename,e.salary,e.deg from Employee l WHERE l.ename=?1");
	    	  query.setParameter(1, name);
			  return (List<Employee>)query.getResultList();
			 
	    	    	  
	      }
	      @DELETE
	      @Path("/{Id}")
		  @Produces(MediaType.TEXT_PLAIN)
		  public Response deleteData(@PathParam("Id") int Id){
	    	  String str;
			  Employee emp=entitymanager.find(Employee.class, Id);
			  if(emp!=null){
				  entitymanager.getTransaction().begin();
			  	  entitymanager.remove(emp);
			  	  entitymanager.getTransaction().commit();
			  	  str="Deleted Successfully";
			  return Response.status(200).entity(str).build();
			  }else{
				  str="Doesn't exist in Db";
				  return Response.status(400).entity(str).build();
			  }
	    	  
	      }
	      /*@SuppressWarnings("unchecked")
		  @GET
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Employee> getUserList(){
			  Query query= entitymanager.createQuery("select e.eid,e.ename,e.salary,e.deg from Employee e");
			  return (List<Employee>)query.getResultList();
		  }
	      @POST
	      @Path("/updateAccount")
	      public Response updateDetails(@FormParam("name") String name,@FormParam("id") int id) {

	      entitymanager.getTransaction().begin();
	      if(name!=null&&id!=0)
	      {
	      Query updateQuery=entitymanager.createQuery("update Employee  e set e.ename=:name where e.eid=:id");
	      updateQuery.setParameter("name",name);
	      updateQuery.setParameter("id",id);
	      updateQuery.executeUpdate();	
	      entitymanager.getTransaction().commit();
	      return Response.status(200).entity("Updated Successfully").build();
	      }
	      else
	      return Response.status(200).entity("Updation Fails").build();


	      }
*/	      @GET
	      @Produces(MediaType.APPLICATION_JSON)
	      public List getEmployee(){
	 entitymanager.getTransaction();
	 CriteriaBuilder cb=entitymanager.getCriteriaBuilder();
	 CriteriaQuery<Object> criteriaQuery = cb.createQuery();
	 Root<Employee> from = criteriaQuery.from(Employee.class);
	 System.out.println("Select all records");
	   CriteriaQuery<Object> select = criteriaQuery.select(from);
	   TypedQuery<Object> typedQuery = entitymanager.createQuery(select);
	   List<Object> resultlist = typedQuery.getResultList();  
	   return resultlist;
	      }
}
