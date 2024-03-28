package com.seulah.appdesign.service;

import com.seulah.appdesign.entity.AgreementForm;
import com.seulah.appdesign.entity.Terms;
import com.seulah.appdesign.repository.AgreementRepo;
import com.seulah.appdesign.repository.TermsRepo;
import com.seulah.appdesign.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseService {

    private final TermsRepo termsRepo;
    private final AgreementRepo agreementRepo;

    public BaseService(TermsRepo termsRepo, AgreementRepo agreementRepo) {
        this.termsRepo = termsRepo;
        this.agreementRepo = agreementRepo;
    }
    public ResponseEntity<?> saveTerms(Terms terms){
        Terms items  = termsRepo.findByTitle(terms.getTitle());
        if(items!=null){
            items.setId(items.getId());
            items.setTitle(terms.getTitle());
            items.setDesc(terms.getDesc());
            termsRepo.save(items);
            return ResponseEntity.badRequest().body(new MessageResponse("Terms & Condition has been Updated",null,false));
        }else {
            termsRepo.save(terms);
            return ResponseEntity.badRequest().body(new MessageResponse("Terms & Condition has been Saved", null, false));
        }
    }

    public ResponseEntity<?> getTerms() {
      List<Terms> terms =  termsRepo.findAll();
      if(terms!=null){
          return ResponseEntity.ok().body(terms.get(0));
      }
      return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> saveAgreement(AgreementForm agreementForm) {
        AgreementForm items  = agreementRepo.findByTitle(agreementForm.getTitle());
        if(items!=null){
            items.setId(items.getId());
            items.setTitle(agreementForm.getTitle());
            items.setDesc(agreementForm.getDesc());
            agreementRepo.save(items);
            return ResponseEntity.badRequest().body(new MessageResponse("Terms & Condition has been Updated",null,false));
        }else {
            agreementRepo.save(agreementForm);
            return ResponseEntity.badRequest().body(new MessageResponse("Terms & Condition has been Saved", null, false));
        }

    }

    public ResponseEntity<?> getAgreement() {
        List<AgreementForm> terms =  agreementRepo.findAll();
        if(terms!=null){
            return ResponseEntity.ok().body(terms.get(0));
        }
        return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
    }
}
