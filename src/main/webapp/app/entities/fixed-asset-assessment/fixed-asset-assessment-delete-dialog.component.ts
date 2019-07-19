import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFixedAssetAssessment } from 'app/shared/model/fixed-asset-assessment.model';
import { FixedAssetAssessmentService } from './fixed-asset-assessment.service';

@Component({
  selector: 'gha-fixed-asset-assessment-delete-dialog',
  templateUrl: './fixed-asset-assessment-delete-dialog.component.html'
})
export class FixedAssetAssessmentDeleteDialogComponent {
  fixedAssetAssessment: IFixedAssetAssessment;

  constructor(
    protected fixedAssetAssessmentService: FixedAssetAssessmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fixedAssetAssessmentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fixedAssetAssessmentListModification',
        content: 'Deleted an fixedAssetAssessment'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-fixed-asset-assessment-delete-popup',
  template: ''
})
export class FixedAssetAssessmentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fixedAssetAssessment }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FixedAssetAssessmentDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.fixedAssetAssessment = fixedAssetAssessment;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fixed-asset-assessment', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fixed-asset-assessment', { outlets: { popup: null } }]);
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
