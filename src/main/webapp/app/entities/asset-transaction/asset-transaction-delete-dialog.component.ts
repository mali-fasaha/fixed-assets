import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssetTransaction } from 'app/shared/model/asset-transaction.model';
import { AssetTransactionService } from './asset-transaction.service';

@Component({
  selector: 'gha-asset-transaction-delete-dialog',
  templateUrl: './asset-transaction-delete-dialog.component.html'
})
export class AssetTransactionDeleteDialogComponent {
  assetTransaction: IAssetTransaction;

  constructor(
    protected assetTransactionService: AssetTransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.assetTransactionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'assetTransactionListModification',
        content: 'Deleted an assetTransaction'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-asset-transaction-delete-popup',
  template: ''
})
export class AssetTransactionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ assetTransaction }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AssetTransactionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.assetTransaction = assetTransaction;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/asset-transaction', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/asset-transaction', { outlets: { popup: null } }]);
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
