import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.test.solution.controller.WalletController;
import ru.test.solution.data.reqeust.TransactionRequest;
import ru.test.solution.enums.OperationType;
import ru.test.solution.exception.GlobalExceptionHandler;
import ru.test.solution.model.Wallet;
import ru.test.solution.service.TransactionService;
import ru.test.solution.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private WalletController walletController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private final UUID walletId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private final BigDecimal amount = new BigDecimal("100.50");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Добавляем обработчик исключений
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void processTransaction_Deposit_ShouldReturnWalletResponse() throws Exception {
        TransactionRequest request = new TransactionRequest(walletId, OperationType.DEPOSIT, amount);
        Wallet wallet = new Wallet(walletId, new BigDecimal("1100.50"));

        when(walletService.processTransaction(eq(walletId), eq(OperationType.DEPOSIT), eq(amount)))
                .thenReturn(wallet);

        when(transactionService.createTransaction(eq(walletId), eq(OperationType.DEPOSIT), eq(amount)))
                .thenReturn(null); // или thenReturn(ожидаемоеЗначение)

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(1100.50));

        verify(walletService).processTransaction(walletId, OperationType.DEPOSIT, amount);
        verify(transactionService).createTransaction(walletId, OperationType.DEPOSIT, amount);
    }

    @Test
    void processTransaction_Withdraw_ShouldReturnWalletResponse() throws Exception {
        TransactionRequest request = new TransactionRequest(walletId, OperationType.WITHDRAW, amount);
        Wallet wallet = new Wallet(walletId, new BigDecimal("900.50"));

        when(walletService.processTransaction(eq(walletId), eq(OperationType.WITHDRAW), eq(amount)))
                .thenReturn(wallet);

        when(transactionService.createTransaction(eq(walletId), eq(OperationType.WITHDRAW), eq(amount)))
                .thenReturn(null);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(900.50));

        verify(walletService).processTransaction(walletId, OperationType.WITHDRAW, amount);
        verify(transactionService).createTransaction(walletId, OperationType.WITHDRAW, amount);
    }

    @Test
    void getWalletBalance_ValidWalletId_ShouldReturnWalletResponse() throws Exception {
        Wallet wallet = new Wallet(walletId, new BigDecimal("1000.00"));

        when(walletService.getWallet(walletId)).thenReturn(wallet);

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(1000.00));

        verify(walletService).getWallet(walletId);
    }
}