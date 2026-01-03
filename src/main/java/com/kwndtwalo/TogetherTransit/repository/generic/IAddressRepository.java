package com.kwndtwalo.TogetherTransit.repository.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {

}
