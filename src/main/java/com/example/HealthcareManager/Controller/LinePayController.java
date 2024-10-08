package com.example.HealthcareManager.Controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HealthcareManager.ConsumerCheck;


@RestController
@RequestMapping("/api")
public class LinePayController {

    @Autowired
    private ConsumerCheck service;

    // 保存支付請求
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveCheckoutPaymentRequest(@RequestBody Map<String, Object> requestBody) {
        service.saveCheckoutPaymentRequest(requestBody);

        // 創建返回的 Map
        Map<String, Object> response = new HashMap<>();
        response.put("message", "訂單保存成功");
        response.put("orderId", requestBody.get("orderId"));  // 可以返回剛保存的訂單ID

        return ResponseEntity.ok(response);
    }

    // 取得指定 ID 的支付詳細訊息
    @GetMapping("/details/{orderId}")
    public ResponseEntity<?> getDetails(@PathVariable String orderId) {
        System.out.println("Received orderId: " + orderId);
        
        Map<String, Object> response = service.getCheckoutPaymentDetails(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 發送支付請求到 Line Pay API
    @PostMapping("/payment")
    public ResponseEntity<?> processPaymentRequest(@RequestBody Map<String, Object> requestBody) {
        // 將傳入的 request body 傳遞給 service 進行處理
        Map<String, Object> response = service.sendPaymentRequest(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // // 發起支付請求
    // @PostMapping("/request")
    // public String requestPayment(@RequestParam int amount, @RequestParam String currency, @RequestParam String orderId) {
    //     // 調用 LinePayService 發送支付請求
    //     return linePayService.requestPayment(amount, currency, orderId);
    // }
    

    // // 處理支付成功的回調
    // @GetMapping("/confirm")
    // public String confirmPayment(@RequestParam String transactionId, @RequestParam int amount, @RequestParam String currency) {
    //     // 調用 LinePayService 確認支付狀態
    //     return linePayService.confirmPayment(transactionId, amount, currency);
    // }

    // // 處理支付取消的回調
    // @GetMapping("/cancel")
    // public String cancelPayment() {
    //     return "支付已失敗";
    // }
}