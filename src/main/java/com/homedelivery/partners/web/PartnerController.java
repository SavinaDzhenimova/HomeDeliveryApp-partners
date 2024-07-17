package com.homedelivery.partners.web;

import com.homedelivery.partners.model.dto.AddPartnerDTO;
import com.homedelivery.partners.model.dto.PartnerDTO;
import com.homedelivery.partners.service.interfaces.PartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerController.class);
    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public ResponseEntity<List<PartnerDTO>> getAllPartners() {
        return ResponseEntity.ok(
                this.partnerService.getAllPartners()
        );
    }

    @PostMapping
    public ResponseEntity<PartnerDTO> addPartner(@RequestBody AddPartnerDTO addPartnerDTO) {

        LOGGER.info("Going to add a partner {}", addPartnerDTO);

        PartnerDTO partnerDTO = this.partnerService.addPartner(addPartnerDTO);
        return ResponseEntity.
                created(ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(partnerDTO.getId())
                                .toUri()
                ).body(partnerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PartnerDTO> deleteById(@PathVariable("id") Long id) {

        this.partnerService.deletePartner(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
