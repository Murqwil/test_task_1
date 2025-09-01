package ru.test.solution.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    private UUID id;

    private BigDecimal balance;

    private Long version;// под вопросом, нужно или нет.
}
