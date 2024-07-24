package com.homedelivery.partners.web;

import com.homedelivery.partners.model.dto.AddPartnerDTO;
import com.homedelivery.partners.model.dto.PartnerDTO;
import com.homedelivery.partners.service.interfaces.PartnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class PartnerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerService mockPartnerService;

    private PartnerDTO partnerDTO;
    private AddPartnerDTO addPartnerDTO;

    @BeforeEach
    void setUp() {
        partnerDTO = new PartnerDTO();
        partnerDTO.setId(1L);
        partnerDTO.setName("Test Partner");

        addPartnerDTO = new AddPartnerDTO();
        addPartnerDTO.setName("Test Partner");
    }

    @Test
    void testGetAllPartners() throws Exception {
        when(mockPartnerService.getAllPartners()).thenReturn(List.of(partnerDTO));

        mockMvc.perform(get("/partners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(partnerDTO.getId()))
                .andExpect(jsonPath("$[0].name").value(partnerDTO.getName()));

        verify(mockPartnerService, times(1)).getAllPartners();
    }

    @Test
    void testAddPartner() throws Exception {
        when(mockPartnerService.addPartner(any(AddPartnerDTO.class))).thenReturn(partnerDTO);

        mockMvc.perform(post("/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Partner\"}")
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        UriComponentsBuilder
                                .fromUriString("http://localhost/partners/1").build().toUri().toString()))
                .andExpect(jsonPath("$.id").value(partnerDTO.getId()))
                .andExpect(jsonPath("$.name").value(partnerDTO.getName()));

        verify(mockPartnerService, times(1)).addPartner(any(AddPartnerDTO.class));
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(mockPartnerService).deletePartner(1L);

        mockMvc.perform(delete("/partners/1"))
                .andExpect(status().isNoContent());

        verify(mockPartnerService, times(1)).deletePartner(1L);
    }

}