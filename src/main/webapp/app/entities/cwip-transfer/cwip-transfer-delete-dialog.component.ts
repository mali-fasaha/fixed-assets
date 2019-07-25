import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICwipTransfer } from 'app/shared/model/cwip-transfer.model';
import { CwipTransferService } from './cwip-transfer.service';

@Component({
  selector: 'gha-cwip-transfer-delete-dialog',
  templateUrl: './cwip-transfer-delete-dialog.component.html'
})
export class CwipTransferDeleteDialogComponent {
  cwipTransfer: ICwipTransfer;

  constructor(
    protected cwipTransferService: CwipTransferService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cwipTransferService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cwipTransferListModification',
        content: 'Deleted an cwipTransfer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-cwip-transfer-delete-popup',
  template: ''
})
export class CwipTransferDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cwipTransfer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CwipTransferDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cwipTransfer = cwipTransfer;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cwip-transfer', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cwip-transfer', { outlets: { popup: null } }]);
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
