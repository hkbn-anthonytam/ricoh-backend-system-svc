package poc.msa.system.repository;

import org.springframework.data.repository.CrudRepository;

import poc.msa.system.entity.Estate;

public interface EstateRepository extends CrudRepository<Estate, Integer> {
    
}
