package fc.projectboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fc.projectboard.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
