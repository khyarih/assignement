package ma.octo.assignement.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class VirementRepositoryTest {

  @Autowired
  private VirementRepository virementRepository;

  @Test
  public void findOne() {
    //TODO implement findAllFindOne
  }

  @Test
  public void findAll() {
    //TODO implement findAlTest
  }

  @Test
  public void save() {
    //TODO implement findAlSave
    
    //Save a 
  }

  @Test
  public void delete() {
    //TODO implement findAlDelete
  }
}