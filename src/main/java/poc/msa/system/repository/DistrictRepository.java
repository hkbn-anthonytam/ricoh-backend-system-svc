package poc.msa.system.repository;

import org.springframework.data.repository.CrudRepository;

import poc.msa.system.entity.District;

public interface DistrictRepository extends CrudRepository<District, Integer> {
    
}
