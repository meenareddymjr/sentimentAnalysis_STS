package com.sentimentanalysis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sentimentanalysis.model.CommentAnalysedReaction;
import com.sentimentanalysis.model.Comments;
import com.sentimentanalysis.process.SentenceTokenClass;
import com.sentimentanalysis.repository.CommentAnalysedReactionRepository;
import com.sentimentanalysis.repository.CommentRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class CommentsController {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CommentAnalysedReactionRepository commentAnalysedReactionRepository;

	@GetMapping("comments")
	public String getComments() throws InvalidFormatException, IOException{
		List<Comments> comments = this.commentRepository.findAll();
		int lastIndex = comments.size()-1;
		String reaction = SentenceTokenClass.sentenceDetect(comments.get(lastIndex).getCommentText());
		this.commentAnalysedReactionRepository.save(new CommentAnalysedReaction(reaction));
		List<CommentAnalysedReaction> list = this.commentAnalysedReactionRepository.findAll();
		int wrc =1;
		int max=0;
		String finalReaction = null;
		for(int i=0;i<list.size();i++)        
	      {
			for(int j=i+1;j<list.size();j++)
	         {
	            
	         if(list.get(i).getCommentReaction().equals(list.get(j).getCommentReaction()))
	            {
	               wrc=wrc+1;    
	               list.get(j).setCommentReaction("0");
	            }
	         }
	         if(list.get(i).getCommentReaction()!="0")
	         {
	        	 if(max<wrc) {
	        		 max = wrc;
	        		 finalReaction = list.get(i).getCommentReaction();
	        	 }
	        	 else if(max == wrc) {
	        		 finalReaction = "Neutral";
	        	 }
	         }
	         wrc=1;	         
	        }  
		return finalReaction;
	}
	
	@PostMapping(value = "/postcomments")
	public  Comments createComments (HttpServletRequest request) throws IOException {
		 String body = null;
		StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }
	    body = stringBuilder.toString();
	    for(int i=0;i<body.length();i++) {
	    	return this.commentRepository.save(new Comments(body));
	    }
		return null;		
	}
}	

