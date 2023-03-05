package com.ogabe.forum.Service;

import com.ogabe.forum.entity.ArticleTypeEntity;
import com.ogabe.forum.Repository.ArticleRepository;
import com.ogabe.forum.Repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleTypeService {

    @Autowired
    ArticleTypeRepository articleTypeRepository;
    public ArticleTypeEntity find(Integer articletypeid){
        return articleTypeRepository.findById(articletypeid).get();
    }

    public List<ArticleTypeEntity> findAll(){
        return  articleTypeRepository.findAll();
    }
}


