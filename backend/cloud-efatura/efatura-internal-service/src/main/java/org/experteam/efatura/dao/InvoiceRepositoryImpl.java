package org.experteam.efatura.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.experteam.efatura.domain.BaseInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public InvoiceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<BaseInvoice> query(SearchQuery searchQuery) {
        final Query query = new Query();
        final List<Criteria> andCriteria = new ArrayList<>();
        final List<Criteria> orCriteria = new ArrayList<>();
        if (StringUtils.isNotEmpty(searchQuery.getInvoiceNumberLike())) {
            orCriteria.add(Criteria.where("invoiceNumber").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    searchQuery.getInvoiceNumberLike(), MongoRegexCreator.MatchMode.CONTAINING
            ), "i"));
        }
        if (StringUtils.isNotEmpty(searchQuery.getCustomerPartyNameLike())) {
            orCriteria.add(Criteria.where("accountingCustomerParty.partyName").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    searchQuery.getCustomerPartyNameLike(), MongoRegexCreator.MatchMode.CONTAINING
            ), "i"));
        }
        if (StringUtils.isNotEmpty(searchQuery.getSupplierPartyNameLike())) {
            orCriteria.add(Criteria.where("accountingSupplierParty.partyName").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    searchQuery.getSupplierPartyNameLike(), MongoRegexCreator.MatchMode.CONTAINING
            ), "i"));
        }
        if (StringUtils.isNotEmpty(searchQuery.getDirection())) {
            andCriteria.add(Criteria.where("direction").is(searchQuery.getDirection()));
        }

        if (!orCriteria.isEmpty()) {
            Criteria orAllCriteria = new Criteria().orOperator(orCriteria.toArray(new Criteria[orCriteria.size()]));
            andCriteria.add(orAllCriteria);
        }

        if (!andCriteria.isEmpty()) {
            Criteria andAllCriteria = new Criteria().andOperator(andCriteria.toArray(new Criteria[andCriteria.size()]));
            query.addCriteria(andAllCriteria);

            log.info("query: " + query.toString());
        }

        return mongoTemplate.find(query, BaseInvoice.class);
    }
}
