package com.sentimentanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commentAnalysedReaction")
public class CommentAnalysedReaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;
	
	@Column(name = "comment_Reaction")
	private String commentReaction;
	
	public CommentAnalysedReaction() {
		super();
	}

	public CommentAnalysedReaction(String commentReaction) {
		super();
		this.commentReaction = commentReaction;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getCommentReaction() {
		return commentReaction;
	}

	public void setCommentReaction(String commentReaction) {
		this.commentReaction = commentReaction;
	}
	
}
