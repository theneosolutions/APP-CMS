package com.seulah.appdesign.apicontroller.nafith.controller;

import com.seulah.appdesign.apicontroller.nafith.dto.SanadGroupDto;
import com.seulah.appdesign.apicontroller.nafith.service.NafithService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Nafith", description = "Nafith API v1")
@RestController
@RequestMapping("/api/nafith")
public class NafithController {

    @Autowired
    private NafithService nafithService;
    String trackingId, timestamp,signature;
    @PostMapping("/sanad-group")
    public ResponseEntity<String> setSanadGroup(@RequestBody SanadGroupDto sanadGroupDto) {
        nafithService.setSanadGroup(trackingId, timestamp, signature,sanadGroupDto);
        return ResponseEntity.ok("Sanad Group created successfully");
    }

    @GetMapping("/sanad-group/status")
    public ResponseEntity<?> sanadStatus() {
        return ResponseEntity.ok(nafithService.sanadStatus(trackingId, timestamp, signature));
    }

    @GetMapping("/sanad-group/by-debtor/{debtorNin}")
    public ResponseEntity<?> sanadGroupListByDebtorId(@PathVariable String debtorNin) {
        String sanadGroupList = nafithService.sanadGroupListByDebtorId(debtorNin);
        return ResponseEntity.ok(sanadGroupList);
    }

    @GetMapping("/sanad-group/{sanadGroupId}")
    public ResponseEntity<?> sanadGroupDetails(@PathVariable String sanadGroupId) {
        String details = nafithService.sanadGroupDetails(sanadGroupId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/sanad-group/with-credit-check/{sanadGroupId}")
    public ResponseEntity<?> sanadGroupDetailsWithCreditCheck(@PathVariable String sanadGroupId) {
        String details = nafithService.sanadGroupDetailsWithCreditCheck(sanadGroupId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/sanad-group/with-reference-id/{referenceId}")
    public ResponseEntity<?> sanadGroupDetailsWithReferenceId(@PathVariable String referenceId) {
        String details = nafithService.sanadGroupDetailsWithReferenceId(referenceId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/sanad-group/download/{sanadGroupId}")
    public ResponseEntity<byte[]> downloadPdfSanadGroup(@PathVariable String sanadGroupId) {
        byte[] pdfData = nafithService.downloadPdfSanadGroup(sanadGroupId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfData);
    }

    @GetMapping("/sanad-group/{sanadGroupId}/sanad/{sanadId}")
    public ResponseEntity<?> sanadGroupDetailsByIdAndSanadId(
            @PathVariable String sanadGroupId,
            @PathVariable String sanadId) {
        String details = nafithService.sanadGroupDetailsByIdAndSanadId(sanadGroupId, sanadId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/sanad/by-reference-id/{referenceId}")
    public ResponseEntity<?> sanadDetailsByReferenceId(@PathVariable String referenceId) {
        String details = nafithService.sanadDetailsByReferenceId(referenceId);
        return ResponseEntity.ok(details);
    }


    @PostMapping("/sanad-group/resend-pending-sms/{sanadGroupId}")
    public ResponseEntity<String> resendPendingSms(@PathVariable String sanadGroupId) {
        nafithService.resendPendingSms(sanadGroupId);
        return ResponseEntity.ok("Pending SMS resent successfully");
    }

    @PatchMapping("/sanad-group/{sanadGroupId}")
    public ResponseEntity<String> updateSanadGroupById(
            @PathVariable String sanadGroupId,
            @RequestBody SanadGroupDto updatedSanadGroupDto) {
        nafithService.updateSanadGroupById(sanadGroupId, updatedSanadGroupDto);
        return ResponseEntity.ok("Sanad Group updated successfully");
    }
}
