package ua.com.andromeda.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ua.com.andromeda.shop.entity.Country;
import ua.com.andromeda.shop.entity.Product;
import ua.com.andromeda.shop.entity.ProductCategory;
import ua.com.andromeda.shop.entity.State;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] unsupportedActions = {HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PUT};

        disableHttpMethods(config, unsupportedActions, Product.class);
        disableHttpMethods(config, unsupportedActions, ProductCategory.class);
        disableHttpMethods(config, unsupportedActions, State.class);
        disableHttpMethods(config, unsupportedActions, Country.class);

        exposeIds(config);
    }

    private void disableHttpMethods(RepositoryRestConfiguration config, HttpMethod[] unsupportedActions, Class<?> clazz) {
        config.getExposureConfiguration()
                .forDomainType(clazz)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));

    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities();
        List<Class<?>> entityClasses = new ArrayList<>();
        for (EntityType<?> entityType: entityTypes){
            entityClasses.add(entityType.getJavaType());
        }
        config.exposeIdsFor(entityClasses.toArray(new Class[0]));
    }
}
