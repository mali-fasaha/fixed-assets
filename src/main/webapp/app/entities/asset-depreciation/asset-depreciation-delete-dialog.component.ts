import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssetDepreciation } from 'app/shared/model/asset-depreciation.model';
import { AssetDepreciationService } from './asset-depreciation.service';

@Component({
  selector: 'gha-asset-depreciation-delete-dialog',
  templateUrl: './asset-depreciation-delete-dialog.component.html'
})
export class AssetDepreciationDeleteDialogComponent {
  assetDepreciation: IAssetDepreciation;

  constructor(
    protected assetDepreciationService: AssetDepreciationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.assetDepreciationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'assetDepreciationListModification',
        content: 'Deleted an assetDepreciation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-asset-depreciation-delete-popup',
  template: ''
})
export class AssetDepreciationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ assetDepreciation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AssetDepreciationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.assetDepreciation = assetDepreciation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/asset-depreciation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/asset-depreciation', { outlets: { popup: null } }]);
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
