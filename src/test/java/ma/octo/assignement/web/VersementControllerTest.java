package ma.octo.assignement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VersementRepository;
import ma.octo.assignement.service.AuditService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class VersementControllerTest {

    private MockMvc mockMvc;
    
    private VersementController controller;

    @Mock
    private VersementRepository repository;

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private AuditService auditService;

    @Spy
    private VersementDto versementDto;

    @Autowired
    public VersementControllerTest(MockMvc mockMvc, VersementController controller) {
        this.mockMvc = mockMvc;
        this.controller = controller;
    }

    @BeforeEach
    public void init(){
        versementDto = new VersementDto();
    }

    //
    @Test
    public void createTransaction_should_throws_CompteNonExistantException_when_toAccount_not_exit() throws Exception {

    ObjectMapper mapper = new ObjectMapper();

    Mockito.when(compteRepository.findById(Mockito.anyLong())).thenReturn(null);

    Assertions.assertThrows(CompteNonExistantException.class,()->{
       controller.createTransaction(versementDto);
    });

    mockMvc.perform(get("/versement/executerVersements"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

}
