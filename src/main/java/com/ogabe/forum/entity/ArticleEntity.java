package com.ogabe.forum.entity;

import com.ogabe.user.entity.UserVO;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleid;
    
    @ManyToOne
    @JoinColumn(name="userid")
    private UserVO userVo;


	@Column(name ="article_title")
    private String articletitle;

    @Column(name ="article_context")
    private String articlecontext;
    
    @Column(name ="post_datetime", nullable = false,columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",insertable = false)
    private java.util.Date postdatetime;


    @Column(name = "update_datetime",nullable = false,columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",insertable = false)
    private java.util.Date updatetime;

    @ManyToOne
    @JoinColumn(name="articletypeid")
    private  ArticleTypeEntity articleTypeEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "articleEntity")
    private List<ReplyEntity> replyEnitySet;
    
    @OneToMany(mappedBy = "articleEntity")
    private List<CollectionEntity> collectionEntity;
    
    @Lob
    private byte[] articleimg;

    public List<CollectionEntity> getCollectionEntity() {
		return collectionEntity;
	}

	public void setCollectionEntity(List<CollectionEntity> collectionEntity) {
		this.collectionEntity = collectionEntity;
	}

	public byte[] getArticleimg() {
		return articleimg;
	}

	public void setArticleimg(byte[] articleimg) {
		this.articleimg = articleimg;
	}

	public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle;
    }

    public String getArticlecontext() {
        return articlecontext;
    }

    public void setArticlecontext(String articlecontext) {
        this.articlecontext = articlecontext;
    }

    public Date getPostdatetime() {
        return postdatetime;
    }

    public void setPostdatetime(Date postdatetime) {
        this.postdatetime = postdatetime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public ArticleTypeEntity getArticleTypeEntity() {
        return articleTypeEntity;
    }

    public void setArticleTypeEntity(ArticleTypeEntity articleTypeEntity) {
        this.articleTypeEntity = articleTypeEntity;
    }

    public List<ReplyEntity> getReplyEnitySet() {
        return replyEnitySet;
    }

    public void setReplyEnitySet(List<ReplyEntity> replyEnitySet) {
        this.replyEnitySet = replyEnitySet;
    }
    
    public UserVO getUserVo() {
  		return userVo;
  	}

  	public void setUserVo(UserVO userVo) {
  		this.userVo = userVo;
  	}

	public ArticleEntity(Integer articleid, UserVO userVo, String articletitle, String articlecontext,
			Date postdatetime, Date updatetime, ArticleTypeEntity articleTypeEntity, List<ReplyEntity> replyEnitySet) {
		super();
		this.articleid = articleid;
		this.userVo = userVo;
		this.articletitle = articletitle;
		this.articlecontext = articlecontext;
		this.postdatetime = postdatetime;
		this.updatetime = updatetime;
		this.articleTypeEntity = articleTypeEntity;
		this.replyEnitySet = replyEnitySet;
	}

	public ArticleEntity() {
		super();
	}


    
    

    //    public List<ReplyEnity> getReplyEnityList() {
//        return replyEnityList;
//    }

//    public void setReplyEnityList(List<ReplyEnity> replyEnityList) {
//        this.replyEnityList = replyEnityList;
//    }



//    @OneToMany(mappedBy = "articleEntity", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
//    private List<ReplyEnity> replyEnityList=new ArrayList<>();

//    public void addReply(ReplyEnity replyEnity){
//        replyEnity.setArticleEntity(this);
//        replyEnityList.add(replyEnity);
//    }



//    @Override
//    public String toString() {
//        return "ArticleEntity{" +
//                "replyEnitySet=" + replyEnitySet +
//                '}';
//    }
}

