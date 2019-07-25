package io.github.assets.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.assets.domain.TransactionApproval} entity.
 */
public class TransactionApprovalDTO implements Serializable {

    private Long id;

    private String description;

    private Long requestedBy;

    
    private String recommendedBy;

    
    private String reviewedBy;

    
    private String firstApprover;

    
    private String secondApprover;

    
    private String thirdApprover;

    
    private String fourthApprover;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getFirstApprover() {
        return firstApprover;
    }

    public void setFirstApprover(String firstApprover) {
        this.firstApprover = firstApprover;
    }

    public String getSecondApprover() {
        return secondApprover;
    }

    public void setSecondApprover(String secondApprover) {
        this.secondApprover = secondApprover;
    }

    public String getThirdApprover() {
        return thirdApprover;
    }

    public void setThirdApprover(String thirdApprover) {
        this.thirdApprover = thirdApprover;
    }

    public String getFourthApprover() {
        return fourthApprover;
    }

    public void setFourthApprover(String fourthApprover) {
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

        TransactionApprovalDTO transactionApprovalDTO = (TransactionApprovalDTO) o;
        if (transactionApprovalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionApprovalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionApprovalDTO{" +
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
