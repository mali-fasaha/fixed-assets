export interface ITransactionApproval {
  id?: number;
  description?: string;
  requestedBy?: number;
  recommendedBy?: string;
  reviewedBy?: string;
  firstApprover?: string;
  secondApprover?: string;
  thirdApprover?: string;
  fourthApprover?: string;
}

export class TransactionApproval implements ITransactionApproval {
  constructor(
    public id?: number,
    public description?: string,
    public requestedBy?: number,
    public recommendedBy?: string,
    public reviewedBy?: string,
    public firstApprover?: string,
    public secondApprover?: string,
    public thirdApprover?: string,
    public fourthApprover?: string
  ) {}
}
