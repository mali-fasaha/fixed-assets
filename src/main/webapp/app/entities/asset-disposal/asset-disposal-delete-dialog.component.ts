import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssetDisposal } from 'app/shared/model/asset-disposal.model';
import { AssetDisposalService } from './asset-disposal.service';

@Component({
  selector: 'gha-asset-disposal-delete-dialog',
  templateUrl: './asset-disposal-delete-dialog.component.html'
})
export class AssetDisposalDeleteDialogComponent {
  assetDisposal: IAssetDisposal;

  constructor(
    protected assetDisposalService: AssetDisposalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.assetDisposalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'assetDisposalListModification',
        content: 'Deleted an assetDisposal'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-asset-disposal-delete-popup',
  template: ''
})
export class AssetDisposalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ assetDisposal }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AssetDisposalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.assetDisposal = assetDisposal;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/asset-disposal', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/asset-disposal', { outlets: { popup: null } }]);
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
