package com.sentimentanalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sentimentanalysis.model.Comments;
import com.sentimentanalysis.repository.CommentRepository;

@SpringBootApplication
public class SentimentanalysisApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SentimentanalysisApplication.class, args);
	}

//	@Autowired
//	private CommentRepository commentRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
//	public void run(String... args) throws Exception {
//		this.commentRepository.save(new Comments("Meena is a good girl"));
//		this.commentRepository.save(new Comments("Meena is a bad girl"));
//		this.commentRepository.save(new Comments("Meena is not a good girl"));
//		
//	}

}
