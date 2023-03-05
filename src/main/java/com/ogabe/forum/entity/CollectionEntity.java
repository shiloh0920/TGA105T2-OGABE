package com.ogabe.forum.entity;

import java.util.Date;

import com.ogabe.user.entity.UserVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CollectionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer collectionid;
	
	@Column(name="collection_datetime" ,nullable = false,columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",insertable = false)
	private Date collectiondate;
	
	
	@ManyToOne
	@JoinColumn(name="articleid")
	private ArticleEntity articleEntity;
	
	
	@ManyToOne
	@JoinColumn(name="userid")
	private UserVO uservo;


	public Integer getCollectionid() {
		return collectionid;
	}


	public void setCollectionid(Integer collectionid) {
		this.collectionid = collectionid;
	}


	public Date getCollectiondate() {
		return collectiondate;
	}


	public void setCollectiondate(Date collectiondate) {
		this.collectiondate = collectiondate;
	}


	public ArticleEntity getArticleEntity() {
		return articleEntity;
	}


	public void setArticleEntity(ArticleEntity articleEntity) {
		this.articleEntity = articleEntity;
	}


	public UserVO getUservo() {
		return uservo;
	}


	public void setUservo(UserVO uservo) {
		this.uservo = uservo;
	}
	
	
}
