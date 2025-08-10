package com.mo.money_organiser.controller;

import com.mo.money_organiser.dto.TransactionDTO;
import com.mo.money_organiser.model.Transaction;
import com.mo.money_organiser.model.User;
import com.mo.money_organiser.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> add(@AuthenticationPrincipal User current, @RequestBody TransactionDTO dto) {
        if (current == null) return ResponseEntity.status(401).build();
        Transaction t = new Transaction();
        t.setType(dto.getType());
        t.setAmount(dto.getAmount());
        t.setCategory(dto.getCategory());
        t.setDate(dto.getDate());
        Transaction saved = transactionService.addTransaction(current.getId(), t);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<?> list(@AuthenticationPrincipal User current) {
        if (current == null) return ResponseEntity.status(401).build();
        List<Transaction> txs = transactionService.listUserTransactions(current.getId());
        return ResponseEntity.ok(txs);
    }
}