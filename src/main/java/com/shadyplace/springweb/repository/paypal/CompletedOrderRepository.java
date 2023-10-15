package com.shadyplace.springweb.repository.paypal;

import com.shadyplace.springweb.models.paypal.CompletedOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedOrderRepository extends CrudRepository<CompletedOrder, String> {
}
