package poc.msa.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import poc.msa.system.entity.Language;

public interface LanguageRepository extends 
        PagingAndSortingRepository<Language, Integer>, 
        CrudRepository<Language, Integer> {
    
}
