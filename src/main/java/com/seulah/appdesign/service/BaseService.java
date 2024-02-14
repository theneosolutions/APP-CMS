package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.Terms;
import com.seulah.appdesign.repository.TermsRepo;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseService {

    private final TermsRepo termsRepo;

    public BaseService(TermsRepo termsRepo) {
        this.termsRepo = termsRepo;
    }
    public ResponseEntity<?> saveTerms(Terms terms){
        Terms items  = termsRepo.findByTitle(terms.getTitle());
        if(items!=null){
            items.setId(items.getId());
            items.setTitle(terms.getTitle());
            items.setDesc(terms.getDesc());
            termsRepo.save(items);
            return ResponseEntity.badRequest().body(new MessageResponse("Terms & Condition has been Updated",null,false));
        }
        termsRepo.save(terms);
        return ResponseEntity.badRequest().body(new MessageResponse("Terms & Condition has been Saved",null,true));
    }

    public ResponseEntity<?> getTerms() {
      List<Terms> terms =  termsRepo.findAll();
      if(terms!=null){
          return ResponseEntity.ok().body(terms.get(0));
      }
      return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
    }
}
