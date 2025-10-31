package com.example.controller;

import com.example.model.Budget;
import com.example.model.Expense;
import com.example.model.NotificationRequest;
import com.example.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expenseRequest) {
        String budgetServiceUrl = "http://localhost:9090/budgets/" + expenseRequest.getBudgetId();
        Budget budget = restTemplate.getForObject(budgetServiceUrl, Budget.class);
        if (budget != null) {
            // 2. Compare expense amount to budget amount
            if (expenseRequest.getAmount() > budget.getAmount()) {
                // 3. Notify Notification Service
                String notificationServiceUrl = "http://localhost:9091/notifications";
                NotificationRequest notification = new NotificationRequest(
                        budget.getCategory(),
                        budget.getAmount(),
                        expenseRequest.getDescription(),
                        expenseRequest.getAmount()
                );
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<NotificationRequest> entity = new HttpEntity<>(notification, headers);

                restTemplate.postForObject(notificationServiceUrl, entity, NotificationRequest.class);
            }
        }
        // 4. Save Expense as usual
        Expense savedExpense = expenseRepository.save(expenseRequest);
        return ResponseEntity.created(URI.create("/expenses/" + savedExpense.getId())).body(savedExpense);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
