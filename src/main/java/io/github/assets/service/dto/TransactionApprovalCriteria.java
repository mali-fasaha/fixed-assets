package io.github.assets.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.TransactionApproval} entity. This class is used
 * in {@link io.github.assets.web.rest.TransactionApprovalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transaction-approvals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionApprovalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LongFilter requestedBy;

    private StringFilter recommendedBy;

    private StringFilter reviewedBy;

    private StringFilter firstApprover;

    private StringFilter secondApprover;

    private StringFilter thirdApprover;

    private StringFilter fourthApprover;

    public TransactionApprovalCriteria(){
    }

    public TransactionApprovalCriteria(TransactionApprovalCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.requestedBy = other.requestedBy == null ? null : other.requestedBy.copy();
        this.recommendedBy = other.recommendedBy == null ? null : other.recommendedBy.copy();
        this.reviewedBy = other.reviewedBy == null ? null : other.reviewedBy.copy();
        this.firstApprover = other.firstApprover == null ? null : other.firstApprover.copy();
        this.secondApprover = other.secondApprover == null ? null : other.secondApprover.copy();
        this.thirdApprover = other.thirdApprover == null ? null : other.thirdApprover.copy();
        this.fourthApprover = other.fourthApprover == null ? null : other.fourthApprover.copy();
    }

    @Override
    public TransactionApprovalCriteria copy() {
        return new TransactionApprovalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(LongFilter requestedBy) {
        this.requestedBy = requestedBy;
    }

    public StringFilter getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(StringFilter recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public StringFilter getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(StringFilter reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public StringFilter getFirstApprover() {
        return firstApprover;
    }

    public void setFirstApprover(StringFilter firstApprover) {
        this.firstApprover = firstApprover;
    }

    public StringFilter getSecondApprover() {
        return secondApprover;
    }

    public void setSecondApprover(StringFilter secondApprover) {
        this.secondApprover = secondApprover;
    }

    public StringFilter getThirdApprover() {
        return thirdApprover;
    }

    public void setThirdApprover(StringFilter thirdApprover) {
        this.thirdApprover = thirdApprover;
    }

    public StringFilter getFourthApprover() {
        return fourthApprover;
    }

    public void setFourthApprover(StringFilter fourthApprover) {
        this.fourthApprover = fourthApprover;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransactionApprovalCriteria that = (TransactionApprovalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(requestedBy, that.requestedBy) &&
            Objects.equals(recommendedBy, that.recommendedBy) &&
            Objects.equals(reviewedBy, that.reviewedBy) &&
            Objects.equals(firstApprover, that.firstApprover) &&
            Objects.equals(secondApprover, that.secondApprover) &&
            Objects.equals(thirdApprover, that.thirdApprover) &&
            Objects.equals(fourthApprover, that.fourthApprover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        requestedBy,
        recommendedBy,
        reviewedBy,
        firstApprover,
        secondApprover,
        thirdApprover,
        fourthApprover
        );
    }

    @Override
    public String toString() {
        return "TransactionApprovalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (requestedBy != null ? "requestedBy=" + requestedBy + ", " : "") +
                (recommendedBy != null ? "recommendedBy=" + recommendedBy + ", " : "") +
                (reviewedBy != null ? "reviewedBy=" + reviewedBy + ", " : "") +
                (firstApprover != null ? "firstApprover=" + firstApprover + ", " : "") +
                (secondApprover != null ? "secondApprover=" + secondApprover + ", " : "") +
                (thirdApprover != null ? "thirdApprover=" + thirdApprover + ", " : "") +
                (fourthApprover != null ? "fourthApprover=" + fourthApprover + ", " : "") +
            "}";
    }

}
