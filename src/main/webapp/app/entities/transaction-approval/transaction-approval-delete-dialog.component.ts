import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionApproval } from 'app/shared/model/transaction-approval.model';
import { TransactionApprovalService } from './transaction-approval.service';

@Component({
  selector: 'gha-transaction-approval-delete-dialog',
  templateUrl: './transaction-approval-delete-dialog.component.html'
})
export class TransactionApprovalDeleteDialogComponent {
  transactionApproval: ITransactionApproval;

  constructor(
    protected transactionApprovalService: TransactionApprovalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionApprovalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionApprovalListModification',
        content: 'Deleted an transactionApproval'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-transaction-approval-delete-popup',
  template: ''
})
export class TransactionApprovalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionApproval }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionApprovalDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.transactionApproval = transactionApproval;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-approval', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-approval', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
