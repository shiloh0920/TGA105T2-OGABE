package com.ogabe.forum.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import com.ogabe.user.entity.UserVO;

import java.util.Date;

@Entity
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reply_id")
    private Integer replyid;

    @Column(name="reply_context",nullable = false)
    private String replycontext;


    @Column(name="reply_datetime",nullable = false,columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",insertable = false)
    private Date replydatetime;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="articleid")
    private  ArticleEntity articleEntity;
    
    
    @ManyToOne
    @JoinColumn(name="userid")
    private UserVO userVo;


    public  ReplyEntity(){

    };
    
    
    public ReplyEntity(String replycontext) {
        this.replycontext = replycontext;
    }

    public Integer getReplyid() {
        return replyid;
    }

    public void setReplyid(Integer replyid) {
        this.replyid = replyid;
    }

    public String getReplycontext() {
        return replycontext;
    }

    public void setReplycontext(String replycontext) {
        this.replycontext = replycontext;
    }

    public Date getReplydatetime() {
        return replydatetime;
    }

    public void setReplydatetime(Date replydatetime) {
        this.replydatetime = replydatetime;
    }

    public ArticleEntity getArticleEntity() {
        return articleEntity;
    }

    public void setArticleEntity(ArticleEntity articleEntity) {
        this.articleEntity = articleEntity;
    }
	public UserVO getUserVo() {
		return userVo;
	}
	public void setUserVo(UserVO userVo) {
		this.userVo = userVo;
	}

   
}

