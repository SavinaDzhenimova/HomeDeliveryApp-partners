package com.homedelivery.partners.service;

import com.homedelivery.partners.model.dto.AddPartnerDTO;
import com.homedelivery.partners.model.dto.PartnerDTO;
import com.homedelivery.partners.model.entity.Partner;
import com.homedelivery.partners.repository.PartnerRepository;
import com.homedelivery.partners.service.interfaces.PartnerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final ModelMapper modelMapper;

    public PartnerServiceImpl(PartnerRepository partnerRepository, ModelMapper modelMapper) {
        this.partnerRepository = partnerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PartnerDTO> getAllPartners() {
        return this.partnerRepository.findAll().stream()
                .map(partner -> this.modelMapper.map(partner, PartnerDTO.class))
                .toList();
    }

    @Override
    public PartnerDTO addPartner(AddPartnerDTO addPartnerDTO) {

        Partner partner = this.modelMapper.map(addPartnerDTO, Partner.class);
        this.partnerRepository.saveAndFlush(partner);

        return this.modelMapper.map(partner, PartnerDTO.class);
    }

    @Override
    public void deletePartner(Long id) {
        this.partnerRepository.deleteById(id);
    }

}