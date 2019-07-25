import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionApproval } from 'app/shared/model/transaction-approval.model';

@Component({
  selector: 'gha-transaction-approval-detail',
  templateUrl: './transaction-approval-detail.component.html'
})
export class TransactionApprovalDetailComponent implements OnInit {
  transactionApproval: ITransactionApproval;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionApproval }) => {
      this.transactionApproval = transactionApproval;
    });
  }

  previousState() {
    window.history.back();
  }
}
