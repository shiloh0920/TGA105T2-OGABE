package com.ogabe.forum.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "article_type")
public class ArticleTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_type_id")
    private  Integer articletypeid;

    @Column(name = "article_type_detail")
    private  String articletypedetail;


    public Integer getArticletypeid() {
        return articletypeid;
    }

    public void setArticletypeid(Integer articletypeid) {
        this.articletypeid = articletypeid;
    }

    public String getArticletypedetail() {
        return articletypedetail;
    }

    public void setArticletyprdetail(String articletyprdetail) {
        this.articletypedetail = articletyprdetail;
    }

}

