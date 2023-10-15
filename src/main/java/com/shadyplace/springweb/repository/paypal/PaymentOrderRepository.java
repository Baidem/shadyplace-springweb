package com.shadyplace.springweb.repository.paypal;

import com.shadyplace.springweb.models.paypal.PaymentOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepository extends CrudRepository<PaymentOrder, String> {
}
