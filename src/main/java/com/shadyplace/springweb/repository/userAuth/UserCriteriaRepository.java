package com.shadyplace.springweb.repository.userAuth;

import com.shadyplace.springweb.forms.SearchForm;
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
public class UserCriteriaRepository {
    @Autowired
    private EntityManager entityManager;

    public Page<User> getUserPageBySearchForm(SearchForm searchForm, Pageable pageable) {
        // Criteria root
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);

        // Predicate list
        List<Predicate> predicates = new ArrayList<Predicate>();

        // Predicate conditions
        if (!searchForm.getSearchBar().isEmpty()){
            Predicate p1 = cb.like(userRoot.get("firstname"), "%"+searchForm.getSearchBar()+"%");
            Predicate p2 = cb.like(userRoot.get("lastname"), "%"+searchForm.getSearchBar()+"%");
            Predicate p3 = cb.like(userRoot.get("email"), "%"+searchForm.getSearchBar()+"%");

            predicates.add(cb.or(p1,p2,p3));
        }

        cq.where(predicates.toArray(new Predicate[] {}));

        cq.orderBy(
                cb.asc(userRoot.get("lastname")),
                cb.asc(userRoot.get("firstname"))
        );
        TypedQuery<User> query = entityManager.createQuery(cq);

        List<User> userList = query.getResultList();
        int start = (int) pageable.getOffset();
        int end = start + pageable.getPageSize();

        if (end > userList.size()) {
            end = userList.size();
        }

        List<User> sublist = userList.subList(start, end);

        Page<User> userPage = new PageImpl<>(sublist, pageable, userList.size());

        return userPage;
    }
}
