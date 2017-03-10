package com.kaningo.scrum.portlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import com.kaningo.scrum.model.Client;
import com.kaningo.scrum.model.Project;
import com.kaningo.scrum.model.impl.ClientImpl;
import com.kaningo.scrum.model.impl.ProjectImpl;
import com.kaningo.scrum.service.ClientLocalServiceUtil;
import com.kaningo.scrum.service.ProjectLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

public class Client_CURD extends MVCPortlet {
		public void addclient(ActionRequest actionRequest, ActionResponse actionResponse)
				throws IOException, PortletException, PortalException, SystemException {
			
			  //To Add New Client 
			
			  Client client = new ClientImpl();
			
			  String clientName= ParamUtil.getString(actionRequest, "ClientName"); 
			  String clientDiscription= ParamUtil.getString(actionRequest, "ClientDiscription");
			  Date clientCreatedOn= ParamUtil.getDate(actionRequest, "ClientCreatedOn",null, new Date());
			  long clientOrgId= ParamUtil.getLong(actionRequest,"ClientOrgId");
			 
			  client.setClientName(clientName);
			  client.setClientDiscription(clientDiscription);
			  client.setClientCreatedOn(clientCreatedOn);
			  client.setClientOrgId(clientOrgId);

			  Client clientRet = ClientLocalServiceUtil.addClient(client);
			  
			  //To Add List Of Projects To Client
			  
			  String projectslist[]= ParamUtil.getStringValues(actionRequest, "ProjectNames");
			  addProjects(clientRet.getClientId(),projectslist, actionRequest);
			  ClientLocalServiceUtil.findByClientname(clientName);
			
			  /*
			  Client clientobj=ClientLocalServiceUtil.get(); 
			  long ClientId=clientobj.getClientId();
			  
			  Client clientObj = ClientLocalServiceUtil.getby(); 
			  Project pclientObj= ProjectLocalServiceUtil.getby();
			  
			  client.set(); project.set();
			*/  
    }
       public void editClient(ActionRequest actionRequest, ActionResponse actionResponse)
				throws IOException, PortletException, PortalException, SystemException {
    	   
    	      //To Edit Project details in client
			
		      String clientName =  ParamUtil.getString(actionRequest, "ClientName");
			  Client clientDetails = ClientLocalServiceUtil.findByClientname(clientName);
			  String projectslist[]= ParamUtil.getStringValues(actionRequest, "ProjectNames");
			  ClientLocalServiceUtil.findByClientname(clientName);
			  addProjects(clientDetails.getClientId(),projectslist, actionRequest); 
			  long projectId= ParamUtil.getLong(actionRequest, "ProjectId");
			  
			  //Delete Previous entries and add new list
			  
			  ProjectLocalServiceUtil.deleteProject(projectId);
			  
    }
  
	  public void addProjects(long clientId, String projectList[],ActionRequest actionRequest) throws PortalException{
		  
		     //To Add Projects
		
		     Project project = new ProjectImpl();
		
		     String projectName= ParamUtil.getString(actionRequest, "ProjectName");
		     String projectDiscription= ParamUtil.getString(actionRequest,"ProjectDiscription"); 
		     Date projectCreatedOn=ParamUtil.getDate(actionRequest, "ProjectCreatedOn", null, new Date()); 
		     long proejctClientId= ParamUtil.getLong(actionRequest,"ProjectClientId"); 
		     String proejctCreatedBy=ParamUtil.getString(actionRequest, "ProjectCreatedBy");
		  
		 
		     long ClientId = clientId;
		     String projectslist[]= projectList;
		     String clientName = ClientLocalServiceUtil.getClient(clientId).getClientName();
		     
		     for(String s:projectslist){

			 project.setProjectName(s);
			 project.setProjectDiscription(projectDiscription);
			 project.setProjectCreatedOn(new Date());
			 project.setProjectClientId(ClientId);
			 project.setProjectCreatedBy(clientName);

			 ProjectLocalServiceUtil.addProject(project);
 
		}
    }
	  public void getClientInfo(ActionRequest actionRequest, ActionResponse actionResponse)
				throws IOException, PortletException, PortalException, SystemException {
			
		    //need to pass client name to the request scope or as a init parameter to the action url created in jsp
			
		    //To Get The Projects From Client To Project DropDown
		  
			String clientName =  ParamUtil.getString(actionRequest, "ClientName");
			Client clientDetails = ClientLocalServiceUtil.findByClientname(clientName);
			
			List<Project> projectList = ProjectLocalServiceUtil.findByClientId(clientDetails.getClientId());
			 
		}
}


