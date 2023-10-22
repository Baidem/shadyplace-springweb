package com.shadyplace.springweb.repository.bookingResa;

import com.shadyplace.springweb.forms.SearchForm;
import com.shadyplace.springweb.models.bookingResa.Command;
import com.shadyplace.springweb.models.enums.CommandStatus;
import com.shadyplace.springweb.models.userAuth.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CommandCriteriaRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<Command> getCommandPageByUserAndSearchForm(User user, SearchForm searchForm, Pageable pageable) {
        // Criteria root
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Command> cq = cb.createQuery(Command.class);
        Root<Command> commandRoot = cq.from(Command.class);

        // Predicate list
        List<Predicate> predicates = new ArrayList<Predicate>();

        // Predicate conditions
        if (!searchForm.getSearchBar().isEmpty()){
            Predicate p1 = cb.like(commandRoot.get("comment"), "%"+searchForm.getSearchBar()+"%");
            Predicate p2 = cb.equal(commandRoot.get("user"), user);

            predicates.add(cb.and(p1,p2));
        }

        cq.where(predicates.toArray(new Predicate[] {}));

        cq.orderBy(cb.desc(commandRoot.get("createdAt")));

        TypedQuery<Command> query = entityManager.createQuery(cq);

        List<Command> commandList = query.getResultList();
        int start = (int) pageable.getOffset();
        int end = start + pageable.getPageSize();

        if (end > commandList.size()) {
            end = commandList.size();
        }

        List<Command> sublist = commandList.subList(start, end);

        Page<Command> commandPage = new PageImpl<>(sublist, pageable, commandList.size());

        return commandPage;
    }

    public Page<Command> getCommandPageByUserAndCommandStatus(User user, CommandStatus commandStatus, Pageable pageable) {
        // Criteria root
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Command> cq = cb.createQuery(Command.class);
        Root<Command> commandRoot = cq.from(Command.class);

        // Predicate list
        List<Predicate> predicates = new ArrayList<Predicate>();

        // Predicate conditions
            Predicate p1 = cb.equal(commandRoot.get("status"), commandStatus);
            Predicate p2 = cb.equal(commandRoot.get("user"), user);

            predicates.add(cb.and(p1,p2));

        cq.where(predicates.toArray(new Predicate[] {}));

        cq.orderBy(cb.desc(commandRoot.get("createdAt")));

        TypedQuery<Command> query = entityManager.createQuery(cq);

        List<Command> commandList = query.getResultList();
        int start = (int) pageable.getOffset();
        int end = start + pageable.getPageSize();

        if (end > commandList.size()) {
            end = commandList.size();
        }

        List<Command> sublist = commandList.subList(start, end);

        Page<Command> commandPage = new PageImpl<>(sublist, pageable, commandList.size());

        return commandPage;
    }


}
