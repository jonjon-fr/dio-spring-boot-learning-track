package dio.budgeting.application;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.InvalidTransactionException;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class PersistTransactionUseCaseTest {
    @Test
    void shouldNotCallRepository_whenTransactionIsInvalid() {
        var repository = mock(TransactionRepository.class);
        var useCase = new PersistTransactionUseCase(repository);
        var input = new PersistTransactionInput(" ", 1000, Category.GROCERIES);

        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(InvalidTransactionException.class);

        verifyNoInteractions(repository);
    }
}
