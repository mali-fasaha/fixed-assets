import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepreciationRegime } from 'app/shared/model/depreciation-regime.model';
import { DepreciationRegimeService } from './depreciation-regime.service';

@Component({
  selector: 'gha-depreciation-regime-delete-dialog',
  templateUrl: './depreciation-regime-delete-dialog.component.html'
})
export class DepreciationRegimeDeleteDialogComponent {
  depreciationRegime: IDepreciationRegime;

  constructor(
    protected depreciationRegimeService: DepreciationRegimeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.depreciationRegimeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'depreciationRegimeListModification',
        content: 'Deleted an depreciationRegime'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-depreciation-regime-delete-popup',
  template: ''
})
export class DepreciationRegimeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ depreciationRegime }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DepreciationRegimeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.depreciationRegime = depreciationRegime;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/depreciation-regime', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/depreciation-regime', { outlets: { popup: null } }]);
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
