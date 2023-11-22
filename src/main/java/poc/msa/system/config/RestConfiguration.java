package poc.msa.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import poc.msa.system.entity.District;
import poc.msa.system.entity.DistrictName;
import poc.msa.system.entity.Estate;
import poc.msa.system.entity.EstateName;
import poc.msa.system.entity.Language;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(District.class, 
                DistrictName.class, 
                Estate.class,
                EstateName.class,
                Language.class);
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);                
    }
}