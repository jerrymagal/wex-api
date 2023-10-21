package br.com.wex.controller;

import br.com.wex.controller.request.PurchaseTransactionRequest;
import br.com.wex.controller.response.ConvertedTransaction;
import br.com.wex.controller.response.PurchaseTransactionResponse;
import br.com.wex.service.PurchaseTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Controller", description = "Endpoints related to transactions")
public class TransactionController {

    private final PurchaseTransactionService service;

    @PostMapping
    @Operation(summary = "Save a new transaction")
    public ResponseEntity<PurchaseTransactionResponse> save(
            @Parameter(description = "Transaction details") @RequestBody @Valid PurchaseTransactionRequest transaction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(transaction));
    }

    @GetMapping("/{uuid}/convert")
    @Operation(summary = "Retrieve a transaction with currency conversion")
    public ConvertedTransaction getWithConversion( @Parameter(description = "UUID of the transaction") @PathVariable UUID uuid,
                                                   @Parameter(description = "Target currency for conversion") @RequestParam String currency) {
        return service.getWithConversion(uuid, currency);
    }
}
