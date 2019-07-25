package io.github.assets.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TransactionApproval.
 */
@Entity
@Table(name = "transaction_approval")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transactionapproval")
public class TransactionApproval implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "requested_by")
    private Long requestedBy;

    
    @Column(name = "recommended_by", unique = true)
    private String recommendedBy;

    
    @Column(name = "reviewed_by", unique = true)
    private String reviewedBy;

    
    @Column(name = "first_approver", unique = true)
    private String firstApprover;

    
    @Column(name = "second_approver", unique = true)
    private String secondApprover;

    
    @Column(name = "third_approver", unique = true)
    private String thirdApprover;

    
    @Column(name = "fourth_approver", unique = true)
    private String fourthApprover;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public TransactionApproval description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRequestedBy() {
        return requestedBy;
    }

    public TransactionApproval requestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public TransactionApproval recommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
        return this;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public TransactionApproval reviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
        return this;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getFirstApprover() {
        return firstApprover;
    }

    public TransactionApproval firstApprover(String firstApprover) {
        this.firstApprover = firstApprover;
        return this;
    }

    public void setFirstApprover(String firstApprover) {
        this.firstApprover = firstApprover;
    }

    public String getSecondApprover() {
        return secondApprover;
    }

    public TransactionApproval secondApprover(String secondApprover) {
        this.secondApprover = secondApprover;
        return this;
    }

    public void setSecondApprover(String secondApprover) {
        this.secondApprover = secondApprover;
    }

    public String getThirdApprover() {
        return thirdApprover;
    }

    public TransactionApproval thirdApprover(String thirdApprover) {
        this.thirdApprover = thirdApprover;
        return this;
    }

    public void setThirdApprover(String thirdApprover) {
        this.thirdApprover = thirdApprover;
    }

    public String getFourthApprover() {
        return fourthApprover;
    }

    public TransactionApproval fourthApprover(String fourthApprover) {
        this.fourthApprover = fourthApprover;
        return this;
    }

    public void setFourthApprover(String fourthApprover) {
        this.fourthApprover = fourthApprover;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionApproval)) {
            return false;
        }
        return id != null && id.equals(((TransactionApproval) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionApproval{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", requestedBy=" + getRequestedBy() +
            ", recommendedBy='" + getRecommendedBy() + "'" +
            ", reviewedBy='" + getReviewedBy() + "'" +
            ", firstApprover='" + getFirstApprover() + "'" +
            ", secondApprover='" + getSecondApprover() + "'" +
            ", thirdApprover='" + getThirdApprover() + "'" +
            ", fourthApprover='" + getFourthApprover() + "'" +
            "}";
    }
}
