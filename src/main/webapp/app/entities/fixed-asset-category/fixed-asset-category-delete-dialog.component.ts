import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFixedAssetCategory } from 'app/shared/model/fixed-asset-category.model';
import { FixedAssetCategoryService } from './fixed-asset-category.service';

@Component({
  selector: 'gha-fixed-asset-category-delete-dialog',
  templateUrl: './fixed-asset-category-delete-dialog.component.html'
})
export class FixedAssetCategoryDeleteDialogComponent {
  fixedAssetCategory: IFixedAssetCategory;

  constructor(
    protected fixedAssetCategoryService: FixedAssetCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fixedAssetCategoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fixedAssetCategoryListModification',
        content: 'Deleted an fixedAssetCategory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-fixed-asset-category-delete-popup',
  template: ''
})
export class FixedAssetCategoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fixedAssetCategory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FixedAssetCategoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fixedAssetCategory = fixedAssetCategory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fixed-asset-category', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fixed-asset-category', { outlets: { popup: null } }]);
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
