package poc.msa.system.repository;

import org.springframework.data.repository.CrudRepository;

import poc.msa.system.entity.EstateName;

public interface EstateNameRepository extends CrudRepository<EstateName, Integer> {
    
}
