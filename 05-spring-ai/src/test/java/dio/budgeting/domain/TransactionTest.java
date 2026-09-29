package dio.budgeting.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTest {
    @Test
    void shouldCreateTransaction_whenInputIsValid() {
        var transaction = new Transaction("  Compra no mercado  ", 1000, Category.GROCERIES);

        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getDescription()).isEqualTo("Compra no mercado");
        assertThat(transaction.getAmount()).isEqualTo(1000);
        assertThat(transaction.getCategory()).isEqualTo(Category.GROCERIES);
    }

    @Test
    void shouldRejectTransaction_whenIdIsNull() {
        assertThatThrownBy(() -> new Transaction(null, "Compra no mercado", 1000, Category.GROCERIES))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessage("A identificação da transação é obrigatória");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldRejectTransaction_whenDescriptionIsBlank(String description) {
        assertThatThrownBy(() -> new Transaction(description, 1000, Category.GROCERIES))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessage("A descrição da transação é obrigatória");
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1, -1000})
    void shouldRejectTransaction_whenAmountIsNotPositive(long amount) {
        assertThatThrownBy(() -> new Transaction("Compra no mercado", amount, Category.GROCERIES))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessage("O valor da transação deve ser maior que zero");
    }

    @Test
    void shouldRejectTransaction_whenCategoryIsNull() {
        assertThatThrownBy(() -> new Transaction("Compra no mercado", 1000, null))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessage("A categoria da transação é obrigatória");
    }

    @Test
    void shouldApplyValidation_whenTransactionIsRehydrated() {
        assertThatThrownBy(() -> new Transaction(new TransactionId(), " ", 1000, Category.GROCERIES))
                .isInstanceOf(InvalidTransactionException.class);
    }
}
