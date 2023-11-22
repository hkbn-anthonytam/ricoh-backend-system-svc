package poc.msa.system.repository;

import org.springframework.data.repository.CrudRepository;

import poc.msa.system.entity.DistrictName;

public interface DistrictNameRepository extends CrudRepository<DistrictName, Integer> {
    
}
