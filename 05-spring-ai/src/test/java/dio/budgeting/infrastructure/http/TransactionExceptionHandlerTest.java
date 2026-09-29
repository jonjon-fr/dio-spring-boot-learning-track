package dio.budgeting.infrastructure.http;

import dio.budgeting.domain.InvalidTransactionException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionExceptionHandlerTest {
    @Test
    void shouldReturnBadRequest_whenTransactionIsInvalid() {
        var handler = new TransactionExceptionHandler();

        var response = handler.handleInvalidTransaction(
                new InvalidTransactionException("A descrição da transação é obrigatória"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("INVALID_TRANSACTION");
        assertThat(response.getBody().message())
                .isEqualTo("A descrição da transação é obrigatória");
    }
}
