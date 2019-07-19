import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFixedAssetItem } from 'app/shared/model/fixed-asset-item.model';
import { FixedAssetItemService } from './fixed-asset-item.service';

@Component({
  selector: 'gha-fixed-asset-item-delete-dialog',
  templateUrl: './fixed-asset-item-delete-dialog.component.html'
})
export class FixedAssetItemDeleteDialogComponent {
  fixedAssetItem: IFixedAssetItem;

  constructor(
    protected fixedAssetItemService: FixedAssetItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fixedAssetItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fixedAssetItemListModification',
        content: 'Deleted an fixedAssetItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-fixed-asset-item-delete-popup',
  template: ''
})
export class FixedAssetItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fixedAssetItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FixedAssetItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fixedAssetItem = fixedAssetItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fixed-asset-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fixed-asset-item', { outlets: { popup: null } }]);
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
