package com.mo.money_organiser.service;

import com.mo.money_organiser.model.Transaction;
import com.mo.money_organiser.model.User;
import com.mo.money_organiser.repository.TransactionRepository;
import com.mo.money_organiser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public Transaction addTransaction(Long userId, Transaction tx) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        tx.setUser(user);
        if (tx.getDate() == null) tx.setDate(LocalDate.now());
        Transaction saved = transactionRepository.save(tx);

        // update user's balance simply
        if ("INCOME".equalsIgnoreCase(tx.getType())) {
            user.setBalance(user.getBalance() + tx.getAmount());
        } else {
            user.setBalance(user.getBalance() - tx.getAmount());
        }
        userRepository.save(user);
        return saved;
    }

    public List<Transaction> listUserTransactions(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
