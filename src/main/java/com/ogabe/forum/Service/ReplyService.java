package com.ogabe.forum.Service;

import com.ogabe.forum.entity.ReplyEntity;
import com.ogabe.forum.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    @Autowired
    ReplyRepository replyRepository;


    public ReplyEntity add(ReplyEntity replyEnity){
       return replyRepository.save(replyEnity);

    }
    public List<ReplyEntity> findAll(){

        return  replyRepository.findAll();
    }

    public ReplyEntity findOne(Integer id){

        return  replyRepository.findById(id).get();
    }
    
    public List<ReplyEntity> find(Integer articleid){
    	return replyRepository.findByArticleEntity(articleid);
    }
    
    public void deleteReply(Integer id) {
    	
    	replyRepository.deleteById(id);
    }
}

