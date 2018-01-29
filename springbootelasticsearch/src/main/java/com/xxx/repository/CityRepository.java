package com.xxx.repository;

import com.xxx.models.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luomouren
 * @date 17/05/2017
 */
@Repository
public interface CityRepository extends ElasticsearchRepository<City,Long> {


}
