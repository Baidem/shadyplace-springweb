package com.shadyplace.springweb.services.paypal;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.shadyplace.springweb.models.paypal.CompletedOrder;
import com.shadyplace.springweb.models.paypal.PaymentOrder;
import com.shadyplace.springweb.repository.paypal.CompletedOrderRepository;
import com.shadyplace.springweb.repository.paypal.PaymentOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaypalService {

    @Autowired
    private PayPalHttpClient payPalHttpClient;

    @Autowired
    private CompletedOrderRepository completedOrderRepository;

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    public PaymentOrder createPayment(BigDecimal montant, long commandId) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        AmountWithBreakdown amountBreakdown = new AmountWithBreakdown().currencyCode("EUR").value(montant.toString());

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().amountWithBreakdown(amountBreakdown);
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));

        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("http://localhost:8083/paypal/capture/" + commandId)
                .cancelUrl("http://localhost:8083/paypal/cancel/" + commandId);
        orderRequest.applicationContext(applicationContext);

        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);
        try {
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
            Order order = orderHttpResponse.result();
            String redirectUrl = order.links().stream()
                    .filter(link -> "approve".equals(link.rel()))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new)
                    .href();
            PaymentOrder paymentOrder = new PaymentOrder("success",  order.id(), redirectUrl);
            this.paymentOrderRepository.save(paymentOrder);
            return paymentOrder;
        } catch (IOException e) {
            return new PaymentOrder("error");
        }
    }
    public CompletedOrder capturePayment(String token){
        CompletedOrder completeOrder;
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                completeOrder  =  new CompletedOrder("success", token);
            } else {
                completeOrder = new CompletedOrder("error");
            }
        } catch (IOException e) {
            completeOrder = new CompletedOrder("error");
        }

        this.completedOrderRepository.save(completeOrder);

        return completeOrder;
    }




}
