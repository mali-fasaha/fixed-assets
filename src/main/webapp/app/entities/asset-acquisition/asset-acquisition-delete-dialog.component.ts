import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';
import { AssetAcquisitionService } from './asset-acquisition.service';

@Component({
  selector: 'gha-asset-acquisition-delete-dialog',
  templateUrl: './asset-acquisition-delete-dialog.component.html'
})
export class AssetAcquisitionDeleteDialogComponent {
  assetAcquisition: IAssetAcquisition;

  constructor(
    protected assetAcquisitionService: AssetAcquisitionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.assetAcquisitionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'assetAcquisitionListModification',
        content: 'Deleted an assetAcquisition'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-asset-acquisition-delete-popup',
  template: ''
})
export class AssetAcquisitionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ assetAcquisition }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AssetAcquisitionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.assetAcquisition = assetAcquisition;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/asset-acquisition', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/asset-acquisition', { outlets: { popup: null } }]);
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
