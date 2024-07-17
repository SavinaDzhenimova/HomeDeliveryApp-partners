package com.homedelivery.partners.service.interfaces;

import com.homedelivery.partners.model.dto.AddPartnerDTO;
import com.homedelivery.partners.model.dto.PartnerDTO;

import java.util.List;

public interface PartnerService {

    List<PartnerDTO> getAllPartners();

    PartnerDTO addPartner(AddPartnerDTO addPartnerDTO);

    void deletePartner(Long id);

}