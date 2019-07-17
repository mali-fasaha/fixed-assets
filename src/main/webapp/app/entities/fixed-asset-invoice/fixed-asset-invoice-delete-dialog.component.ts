import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';
import { FixedAssetInvoiceService } from './fixed-asset-invoice.service';

@Component({
  selector: 'gha-fixed-asset-invoice-delete-dialog',
  templateUrl: './fixed-asset-invoice-delete-dialog.component.html'
})
export class FixedAssetInvoiceDeleteDialogComponent {
  fixedAssetInvoice: IFixedAssetInvoice;

  constructor(
    protected fixedAssetInvoiceService: FixedAssetInvoiceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fixedAssetInvoiceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fixedAssetInvoiceListModification',
        content: 'Deleted an fixedAssetInvoice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-fixed-asset-invoice-delete-popup',
  template: ''
})
export class FixedAssetInvoiceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fixedAssetInvoice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FixedAssetInvoiceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fixedAssetInvoice = fixedAssetInvoice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fixed-asset-invoice', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fixed-asset-invoice', { outlets: { popup: null } }]);
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
