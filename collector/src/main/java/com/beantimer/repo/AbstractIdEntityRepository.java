package com.beantimer.repo;

import com.beantimer.entity.IdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractIdEntityRepository<T extends IdEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
