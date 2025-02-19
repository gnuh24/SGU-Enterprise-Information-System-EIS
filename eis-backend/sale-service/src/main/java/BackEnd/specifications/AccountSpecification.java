package BackEnd.specifications;

import BackEnd.entities.Account;
import BackEnd.dto.request.account.AccountFilterForm;
import com.mysql.cj.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

@Data
public class AccountSpecification implements Specification<Account> {

    @NonNull
    private String field;

    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(  @NotNull Root<Account> root,
                                                    @NotNull CriteriaQuery<?> query,
                                                    @NotNull CriteriaBuilder criteriaBuilder) {

        if (field.equalsIgnoreCase("role")) {
            return criteriaBuilder.equal(root.get("role"), value);
        }

        if (field.equalsIgnoreCase("status")) {
            return criteriaBuilder.equal(root.get("status"), value);
        }

        if (field.equalsIgnoreCase("from")) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createAt").as(Date.class), (Date) value);
        }

        if (field.equalsIgnoreCase("to")) {
            return criteriaBuilder.lessThanOrEqualTo(root.get("createAt").as(Date.class), (Date) value);
        }

        if (field.equalsIgnoreCase("email")) {
            return criteriaBuilder.like(root.get("email"), "%" + value + "%");
        }

        return null;
    }

    public static Specification<Account> buildWhere(AccountFilterForm form) {
        Specification<Account> where = null;
        String search = form.getSearch();

        if (!StringUtils.isEmptyOrWhitespaceOnly(search)) {
            search = search.trim();
            search = search.toLowerCase();

            AccountSpecification email = new AccountSpecification("email",  search);
            where = Specification.where(email);
        }

        if (form != null) {

            if (form.getRole() != null) {
                // Convert the role to string (if needed) or directly use it for comparison
                String role = form.getRole().toUpperCase();

                AccountSpecification roleSpec = new AccountSpecification("role", role);

                // Apply the role specification
                if (where != null) {
                    where = where.and(roleSpec);
                } else {
                    where = Specification.where(roleSpec);
                }
            }

            if (form.getStatus() != null) {
                // Convert the status to string (if needed) and ensure it's uppercase for comparison
                String status = form.getStatus().toUpperCase();
                AccountSpecification statusSpec = new AccountSpecification("status", status);

                // Apply the status specification
                if (where != null) {
                    where = where.and(statusSpec);
                } else {
                    where = Specification.where(statusSpec);
                }
            }


//            if (form.getFrom() != null) {
//                AccountSpecification fromSpec = new AccountSpecification("from", form.getFrom());
//                if (where != null) {
//                    where = where.and(fromSpec);
//                } else {
//                    where = Specification.where(fromSpec);
//                }
//            }
//
//            if (form.getTo() != null) {
//                AccountSpecification toSpec = new AccountSpecification("to", form.getTo());
//                if (where != null) {
//                    where = where.and(toSpec);
//                } else {
//                    where = Specification.where(toSpec);
//                }
//            }

        }

        return where;
    }
}
