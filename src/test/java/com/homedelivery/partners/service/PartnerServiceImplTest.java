package com.homedelivery.partners.service;

import com.homedelivery.partners.model.dto.AddPartnerDTO;
import com.homedelivery.partners.model.dto.PartnerDTO;
import com.homedelivery.partners.model.entity.Partner;
import com.homedelivery.partners.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartnerServiceImplTest {

    @Mock
    private PartnerRepository mockPartnerRepository;

    @Mock
    private ModelMapper mockModelMapper;

    private PartnerServiceImpl partnerServiceToTest;

    @BeforeEach
    void setUp() {
        this.partnerServiceToTest = new PartnerServiceImpl(mockPartnerRepository, mockModelMapper);
    }

    @Test
    void testGetAllPartners() {
        Partner partner = new Partner();
        partner.setName("Partner 1");
        partner.setEmail("test_partner@gmail.com");
        partner.setSite("www.test_partner.com");
        PartnerDTO partnerDTO = new PartnerDTO();

        when(mockPartnerRepository.findAll()).thenReturn(List.of(partner));
        when(mockModelMapper.map(partner, PartnerDTO.class)).thenReturn(partnerDTO);

        List<PartnerDTO> result = partnerServiceToTest.getAllPartners();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(partnerDTO.getName(), result.get(0).getName());
        assertEquals(partnerDTO.getEmail(), result.get(0).getEmail());
        assertEquals(partnerDTO.getSite(), result.get(0).getSite());

        verify(mockPartnerRepository, times(1)).findAll();
        verify(mockModelMapper, times(1)).map(partner, PartnerDTO.class);
    }

    @Test
    void testAddPartner() {
        AddPartnerDTO addPartnerDTO = new AddPartnerDTO();
        Partner partner = new Partner();
        PartnerDTO partnerDTO = new PartnerDTO();

        when(mockModelMapper.map(addPartnerDTO, Partner.class)).thenReturn(partner);
        when(mockPartnerRepository.saveAndFlush(partner)).thenReturn(partner);
        when(mockModelMapper.map(partner, PartnerDTO.class)).thenReturn(partnerDTO);

        PartnerDTO result = partnerServiceToTest.addPartner(addPartnerDTO);

        assertNotNull(result);
        assertSame(partnerDTO, result);

        verify(mockModelMapper, times(1)).map(addPartnerDTO, Partner.class);
        verify(mockPartnerRepository, times(1)).saveAndFlush(partner);
        verify(mockModelMapper, times(1)).map(partner, PartnerDTO.class);
    }

    @Test
    void testDeletePartner() {
        Long partnerId = 1L;

        partnerServiceToTest.deletePartner(partnerId);

        verify(mockPartnerRepository, times(1)).deleteById(partnerId);
    }

}